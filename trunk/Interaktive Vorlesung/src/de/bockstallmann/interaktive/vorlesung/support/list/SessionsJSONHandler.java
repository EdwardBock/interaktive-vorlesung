package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.activities.CourseDetailsDetails;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SessionsJSONHandler extends Handler {
	
	private CourseDetailsFactory coursedetails;

	public SessionsJSONHandler(CourseDetailsFactory CourseDetails){
		coursedetails = CourseDetails;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			coursedetails.addSessions((JSONArray) msg.obj);
		}
	};
}
