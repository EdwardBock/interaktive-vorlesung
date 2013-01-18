package de.bockstallmann.interaktive.vorlesung.model;

public class Question {
	private int id;
	private String q, a1, a2, a3, a4;

	public Question(int id, String question, String answer1, String answer2, String answer3, String answer4) {
		this.id = id;
		this.q = question;
		this.a1 = answer1;
		this.a2 = answer2;
		this.a3 = answer3;
		this.a4 = answer4;
	}

	public String getQuestion() {
		return q;
	}

	public void setQuestion(String q) {
		this.q = q;
	}

	public String getAnswer1() {
		return a1;
	}

	public void setAnswer1(String a1) {
		this.a1 = a1;
	}

	public String getAnswer2() {
		return a2;
	}

	public void setAnswer2(String a2) {
		this.a2 = a2;
	}

	public String getAnswer3() {
		return a3;
	}

	public void setAnswer3(String a3) {
		this.a3 = a3;
	}

	public String getAnswer4() {
		return a4;
	}

	public void setAnswer4(String a4) {
		this.a4 = a4;
	}
	
	
}
