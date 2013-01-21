package de.bockstallmann.interaktive.vorlesung.support;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.activities.SessionStart;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class QuestionJSONHandler extends Handler{

	private SessionStart sessionStart;
	private int add;
	
	public QuestionJSONHandler(SessionStart sStart, int ADD){
		sessionStart = sStart;
		add = ADD;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler Question","in FUnktion");
		
		if(msg.arg1 == Constants.MSG_SUCCESS && add == 1){
			sessionStart.addQuestions((JSONArray) msg.obj);
		}
	};
}
