package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import de.bockstallmann.interaktive.vorlesung.interfaces.ArchiveObserverInterface;
import de.bockstallmann.interaktive.vorlesung.model.ArchiveQuestion;
import de.bockstallmann.interaktive.vorlesung.model.Collection;
import de.bockstallmann.interaktive.vorlesung.model.SessionArchive;

public class ArchiveObservable {
	public static final String CMD_LIST = "list", CMD_EMPTY = "empty", CMD_CHANGED = "changed";
	private List<ArchiveObserverInterface> archive_observers;
	private SessionArchive session_archive;
	public ArchiveObservable() {
		archive_observers = new ArrayList<ArchiveObserverInterface>();
	}
	public void addObserver(ArchiveObserverInterface ao){
		archive_observers.add(ao);
	}
	public void setCollections(JSONArray serverDaten) {
		session_archive = new SessionArchive();
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				session_archive.setCollection(serverDaten.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(session_archive.getCollections().size() > 0 && session_archive.getActiveCollection().countQuestions() > 0){
			notifyAll(CMD_LIST);
		} else {
			notifyAll(CMD_EMPTY);
		}
	}
	public Collection getActiveCollection(){
		return session_archive.getActiveCollection();
	}
	public ArchiveQuestion getActiveQuestion(){
		return session_archive.getActiveQuestion();
	}
	public SessionArchive getSessionArchive(){
		return this.session_archive;
	}
	public void nextQuestion(){
		Log.d("ArchiveObservable", "NEXT");
		session_archive.nextQuestion();
		notifyAll(CMD_CHANGED);
	}
	public void prevQuestion(){
		Log.d("ArchiveObservable", "NEXT");
		session_archive.prevQuestion();
		notifyAll(CMD_CHANGED);
	}
	public int countQuestions(){
		return session_archive.countQuestions();
	}
	public int getActiveQuestionCountPosition(){
		return session_archive.getActiveQuestionCountPosition();
	}
	private void notifyAll(String cmd){
		for (ArchiveObserverInterface observer : archive_observers) {
			observer.update(cmd, this);
		}
	}
	public int countCollections() {
		return session_archive.getCollections().size();
	}
}
