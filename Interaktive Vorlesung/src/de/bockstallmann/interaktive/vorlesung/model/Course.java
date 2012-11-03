package de.bockstallmann.interaktive.vorlesung.model;

public class Course {

	private int id;
	private String t, d;
	private boolean pw;

	public Course(final int _id, final String title, final String description, final boolean hasPassword) {
		id = _id;
		t = title;
		d = description;
		pw = hasPassword;
	}

	public int getID(){
		return id;
	}
	public String getTitle() {
		return t;
	}
	public String getDescription() {
		return d;
	}
	public boolean hasPassword(){
		return pw;
	}
}
