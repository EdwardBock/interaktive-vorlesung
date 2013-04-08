package de.bockstallmann.interaktive.vorlesung.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionArchive {
	private ArrayList<Collection> collections;
	private int active_collection, active_question;
	private int count_overall_questions;

	public SessionArchive() {
		collections = new ArrayList<Collection>();
		active_collection = 0;
		active_question = 0;
		count_overall_questions = -1;
	}

	public void setCollection(JSONObject jsonObject) {
		ArrayList<ArchiveQuestion> questions = new ArrayList<ArchiveQuestion>();
		try {
			JSONArray questionsJSON = jsonObject.getJSONArray("questions");
			for(int i = 0; i < questionsJSON.length(); i++){
				JSONObject temp_json = questionsJSON.getJSONObject(i);
				questions.add(new ArchiveQuestion(
						Integer.parseInt(temp_json.getString("_id")),
						temp_json.getString("question"),
						temp_json.getString("a"),
						temp_json.getString("b"),
						temp_json.getString("c"),
						temp_json.getString("d"),
						temp_json.getString("correct").charAt(0),
						temp_json.getInt("count_a"),
						temp_json.getInt("count_b"),
						temp_json.getInt("count_c"),
						temp_json.getInt("count_d")));
			}
			
			collections.add(new Collection(
								jsonObject.getInt("_id"), 
								jsonObject.getString("title"), 
								jsonObject.getInt("active"), questions));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		active_collection = 0;
		active_question = 0;
	}
	public ArrayList<Collection> getCollections() {
		return collections;
	}

	public Collection getActiveCollection() {
		return collections.get(active_collection);
	}

	public ArchiveQuestion getActiveQuestion() {
		return collections.get(active_collection).getQuestion(active_question);
	}
	public void nextCollection() {
		active_collection++;
		if(!(active_collection < collections.size())){
			active_collection = 0;
		}
	}
	public void nextQuestion() {
		active_question++;
		if(!(active_question < collections.get(active_collection).countQuestions())){
			active_question = 0;
			nextCollection();
		}		
	}
	public void prevCollection(){
		active_collection--;
		if(active_collection < 0){
			active_collection = (collections.size()-1);
		}
	}
	public void prevQuestion() {
		active_question--;
		if(active_question < 0){
			prevCollection();
			active_question = getActiveCollection().countQuestions()-1;
		}
	}
	
	public int getActiveQuestionCountPosition(){
		int count = 0;
		for(int i = 0; i <= active_collection; i++){
			if(i == active_collection){
				count += active_question+1;
			} else {
				count += collections.get(i).countQuestions();
			}
		}
		return count;
	}
	
	public int countQuestions() {
		if(count_overall_questions  >= 0){
			return count_overall_questions;
		}
		count_overall_questions = 0;
		for (Collection collection : collections) {
			count_overall_questions += collection.countQuestions();
		}
		return count_overall_questions;
	}
}
