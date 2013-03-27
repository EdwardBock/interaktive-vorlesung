package de.bockstallmann.interaktive.vorlesung.support;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class QuestionSaveJSONHandler extends Handler{

	private CollectionObservable qal;
	
	public QuestionSaveJSONHandler(CollectionObservable questions){
		qal= questions;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler Questions","läuft");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			qal.activeQuestionSaved();
		}
	};
}
