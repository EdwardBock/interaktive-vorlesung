package de.bockstallmann.interaktive.vorlesung.model;

public class Course {

	private String t;
	private String d;

	public Course(final String title, final String description) {
		t = title;
		d = description;
	}

	public String getTitle() {
		return t;
	}
	public String getDescription() {
		return d;
	}
}
