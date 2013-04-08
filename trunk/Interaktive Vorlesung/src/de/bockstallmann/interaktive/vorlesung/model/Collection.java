package de.bockstallmann.interaktive.vorlesung.model;

import java.util.ArrayList;

public class Collection {
	
	private int id;
	private String title;
	private int active;
	private ArrayList<ArchiveQuestion> questions;

	public Collection(int id, String title, int active, ArrayList<ArchiveQuestion> questions) {
		this.id = id;
		this.title = title;
		this.active = active;
		this.questions = questions;
	}
	public ArchiveQuestion getQuestion(int index){
		return questions.get(index);
	}
	public int countQuestions(){
		return questions.size();
	}
	public String getTitle(){
		return this.title;
	}
}
