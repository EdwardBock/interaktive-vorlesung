package de.bockstallmann.interaktive.vorlesung.model;

public class Course {

	private int id;
	private String t,r, d;
	private boolean pw;

	public Course(final int _id, final String title, final String reader, final boolean hasPassword) {
		id = _id;
		t = title;
		r = reader;
		pw = hasPassword;
	}

	public int getID(){
		return id;
	}
	public String getTitle() {
		return t;
	}
	public String getReader(){
		return r;
	}
	public String getDescription() {
		return d;
	}
	public boolean hasPassword(){
		return pw;
	}
}
