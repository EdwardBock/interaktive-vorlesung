package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Messenger;
import de.bockstallmann.interaktive.vorlesung.interfaces.CollectionObserverInterface;
import de.bockstallmann.interaktive.vorlesung.model.Question;

public class CollectionObservable {

	private List<Question> questions;
	private JSONObject temp_json;
	private List<CollectionObserverInterface> collection_observers;
	private Question activeQuestion;
	private String chosenAnswer;
	private SQLDataHandler dataHander;
	//private JSONObject jsonCollection;
	public static final String A = "a", B = "b", C = "c", D = "d", NONE = "none";
	public static final String CMD_LIST = "list", CMD_ANSWER = "answer";

	public CollectionObservable(SQLDataHandler dh) {
		questions = new ArrayList<Question>();
		collection_observers = new ArrayList<CollectionObserverInterface>();
		chosenAnswer = NONE;
		dataHander = dh;
	}

	public void addObserver(CollectionObserverInterface co){
		collection_observers.add(co);
	}

	public void addQuestions(JSONArray serverdaten) {
		questions.clear();
		try {
			//jsonCollection = (JSONObject) serverdaten.get(0);
			//collection_id = jsonCollection.getInt("_id");
			//Log.d("CollectionObservable", jsonCollection.toString());
			JSONArray jsonQuestions = (JSONArray) serverdaten.get(1);
			for (int i = 0; i < jsonQuestions.length(); i++){
				temp_json = jsonQuestions.getJSONObject(i);
				if( dataHander.hasQuestion(Integer.parseInt(temp_json.getString("_id"))) ) continue;
				questions.add(new Question(
					Integer.parseInt(temp_json.getString("_id")),
					temp_json.getString("question"),
					temp_json.getString("a"),
					temp_json.getString("b"),
					temp_json.getString("c"),
					temp_json.getString("d"),
					temp_json.getString("correct").charAt(0)));
			}
		}catch (Exception e) {
			//Log.d("QuestionsObservable",e.toString());
			e.toString();
		}
		notifyListChanged();
	}
	public Question getQuestion(){
		if(activeQuestion == null && size() > 0) activeQuestion = questions.get(0);
		return activeQuestion;
	}
	public int size() {
		return questions.size();
	}
	public boolean preventAndNext() {
		if(this.size() < 2) return false;
		questions.remove(activeQuestion);
		questions.add(activeQuestion);
		activeQuestion = null;
		notifyListChanged();
		return true;
	}

	public String getAnswer() {
		return chosenAnswer;
	}

	public void setAnswer(final String answer){
		if(chosenAnswer == answer){
			chosenAnswer = NONE;
		} else{
			chosenAnswer = answer;
		}
		notifyAnswerChanged();
	}

	public void saveAnswerAndNext() {
		new JSONLoader(new Messenger(new QuestionSaveJSONHandler(this))).countAnswer(activeQuestion.getId(), chosenAnswer);
	}

	public void activeQuestionSaved() {
		dataHander.addQuestion(activeQuestion);
		questions.remove(activeQuestion);
		chosenAnswer = NONE;
		activeQuestion = null;
		notifyListChanged();
	}

	private void notifyListChanged(){
		notifyAll(CMD_LIST);
	}
	private void notifyAnswerChanged(){
		notifyAll(CMD_ANSWER);
	}
	private void notifyAll(final String cmd){
		for (CollectionObserverInterface co : collection_observers) {
			co.update(cmd, this);
		}
	}
}
