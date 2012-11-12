package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.support.Constants;
import android.os.Handler;
import android.os.Message;

public class CoursesJSONHandler extends Handler {
	
	private CoursesArrayAdapter adapter;

	public CoursesJSONHandler(CoursesArrayAdapter arrayAdapter){
		adapter = arrayAdapter;
	}
	public void handleMessage(Message msg) {
		if(msg.arg1 == Constants.MSG_SUCCESS){
			adapter.addCourses((JSONArray)msg.obj);
		}
	};
	
}
