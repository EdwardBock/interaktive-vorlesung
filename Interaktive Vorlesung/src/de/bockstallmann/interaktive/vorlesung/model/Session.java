package de.bockstallmann.interaktive.vorlesung.model;

public class Session {

	private String room;
	private String title;

	private String begin;
	private String end;
	private String src;
	private int state;
	private int id;
	private boolean arch;
	
	
	public Session(int ID,String Room, String Title, String Begin, String End, int state, boolean archived){
		this.id = ID;
		this.room = Room;
		this.title = Title;
		this.begin = Begin.substring(0, 16);
		this.end = End.substring(11, 16); 
		this.arch = archived;
		this.state = state;
	}
	
	public int getID(){
		return id;
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
	
	public boolean isArchived(){
		return this.arch;
	}
	public boolean isActive(){
		if(state == 1){
			return true; 
		} 
		return false;
	}

}
