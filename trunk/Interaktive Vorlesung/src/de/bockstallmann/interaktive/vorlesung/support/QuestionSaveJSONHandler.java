package de.bockstallmann.interaktive.vorlesung.support;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class QuestionSaveJSONHandler extends Handler{

	private CollectionObservable qal;
	
	public QuestionSaveJSONHandler(CollectionObservable questions){
		qal= questions;
	}
	@Override
	public void handleMessage(Message msg) {
		Log.d("Handler Questions","läuft");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			JSONArray json = (JSONArray) msg.obj;
			try {
				Log.d("QuestionSaveHandler", json.getJSONObject(0).toString());
				if(json.getJSONObject(0).has("_id")){
					qal.activeQuestionSaved();
					return;
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			qal.closeCollection();
		}
	};
}
