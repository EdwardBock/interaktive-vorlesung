package de.bockstallmann.interaktive.vorlesung.model;

public class Session {

	private String room;
	private String title;

	private String begin;
	private String end;
	private String src;
	
	
	public Session(String Room, String Title, String Begin, String End){
		this.room = Room;
		this.title = Title;
		this.begin = Begin;
		this.end = End;
	}
	
	
	public String getRoom() {
		return room;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBegin() {
		return begin;
	}
	
	public void setBegin(String begin) {
		this.begin = begin;
	}
	
	public String getEnd() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end = end;
	}
	

}
