package de.bockstallmann.interaktive.vorlesung.model;

public class Course {

	private String t, d;
	private boolean pw;

	public Course(final String title, final String description, final boolean hasPassword) {
		t = title;
		d = description;
		pw = hasPassword;
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
