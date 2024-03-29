package de.bockstallmann.interaktive.vorlesung.model;

import java.sql.Date;
import java.util.ArrayList;

public class Course {

	private int id;
	private String t,r, d, s, pw, j;
	private ArrayList<Session> sess;
	private Date changed;

	public Course(final int _id, final String title, final String reader, final String semester, final String jahr, final String password) {
		id = _id;
		t = title;
		r = reader;
		s = semester;
		pw = password;
		j = jahr;
	}
	public Course(final int _id, final String title, final String description, final String reader, final String semester,final String jahr,  final String password) {
		this(_id, title, reader, semester,jahr, password);
		d = description;
	}
	public Course(final int _id, final String title, final String reader, final String semester,final String jahr){
		id = _id;
		t = title;
		r = reader;
		s = semester;
		j = jahr;
	}

	public String getYear(){
		return j;
	}
	
	public int getID(){
		return id;
	}
	public String getTitle() {
		return t;
	}
	public String getDescription(){
		return d;
	}
	public String getReader(){
		return r;
	}
	public String getSemester() {
		return s;
	}
	public boolean hasPassword(){
		if(pw == null || pw.equals("")) return false;
		return true;
	}
	public String getPassword(){
		return pw;
	}
	public void setSessions(ArrayList<Session> sessions){
		sess = sessions;
	}
	public Date getChanged(){
		return changed;
	}
}
