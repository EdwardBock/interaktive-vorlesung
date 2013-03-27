package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import de.bockstallmann.interaktive.vorlesung.interfaces.CollectionObserverInterface;
import de.bockstallmann.interaktive.vorlesung.model.Question;

public class CollectionObservable {

	private List<Question> questions;
	private JSONObject jsonCollection;
	private JSONObject temp_json;
	private List<CollectionObserverInterface> collection_observers;
	private Question activeQuestion;
	private String chosenAnswer;
	public static final String A = "a", B = "b", C = "c", D = "d", NONE = "none";
	public static final String CMD_LIST = "list", CMD_ANSWER = "answer";

	public CollectionObservable() {
		questions = new ArrayList<Question>();
		collection_observers = new ArrayList<CollectionObserverInterface>();
		chosenAnswer = NONE;
	}

	public void addObserver(CollectionObserverInterface co){
		collection_observers.add(co);
	}

	public void addQuestions(JSONArray serverdaten) {
		questions.clear();
		Log.d("CollectionObservable","füge daten hinzu:"+serverdaten.toString());
		try {
			//jsonCollection = (JSONObject) serverdaten.get(0);
			JSONArray jsonQuestions = (JSONArray) serverdaten.get(1);
			for (int i = 0; i < jsonQuestions.length(); i++){
				Log.d("CollectionObservable",jsonQuestions.get(i).toString());
				temp_json = jsonQuestions.getJSONObject(i);
				questions.add(new Question(
					Integer.parseInt(temp_json.getString("_id")),
					temp_json.getString("question"),
					temp_json.getString("a"),
					temp_json.getString("b"),
					temp_json.getString("c"),
					temp_json.getString("d")));
			}
		}catch (Exception e) {
			Log.d("QuestionsObservable",e.toString());
		}
		notifyListChanged();
	}
	public Question getQuestion(){
		if(activeQuestion == null) activeQuestion = questions.get(0);
		return activeQuestion;
	}
	public int size() {
		return questions.size();
	}
	public boolean preventAndNext() {
		if(this.size() < 2) return false;
		questions.remove(0);
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
