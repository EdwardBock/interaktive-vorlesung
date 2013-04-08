package de.bockstallmann.interaktive.vorlesung.model;

public class ArchiveQuestion extends Question {
	private int count1, count2, count3, count4;
	public ArchiveQuestion(int id, String question, String answer1,
			String answer2, String answer3, String answer4, char correctAnswer, int count1, int count2, int count3, int count4) {
		super(id, question, answer1, answer2, answer3, answer4, correctAnswer);
		this.count1 = count1;
		this.count2 = count2;
		this.count3 = count3;
		this.count4 = count4;
	}
	public int getCount1(){
		return this.count1;
	}
	public int getCount2(){
		return this.count2;
	}
	public int getCount3(){
		return this.count3;
	}
	public int getCount4(){
		return this.count4;
	}

}
