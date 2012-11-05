package de.bockstallmann.interaktive.vorlesung.model;

public class Session {

	private String room;
	private String info;

	private String begin;
	private String end;
	private String src;
	
	
	public Session(String r,String i,String b,String e,String s){
		this.room = r;
		this.info = i;
		this.begin = b;
		this.end = e;
		this.src = s;
	}
	
	
	public String getRoom() {
		return room;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
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
	
	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
}
