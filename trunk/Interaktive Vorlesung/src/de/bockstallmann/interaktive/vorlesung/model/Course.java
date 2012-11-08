package de.bockstallmann.interaktive.vorlesung.model;

public class Course {

	private int id;
	private String t,r, d, s;
	private boolean pw;

	public Course(final int _id, final String title, final String reader, final String semester,  final boolean hasPassword) {
		id = _id;
		t = title;
		r = reader;
		s = semester;
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
	public String getSemester() {
		return s;
	}
	public boolean hasPassword(){
		return pw;
	}
}
