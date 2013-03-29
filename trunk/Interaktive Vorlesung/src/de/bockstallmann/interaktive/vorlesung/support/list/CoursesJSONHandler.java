package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import de.bockstallmann.interaktive.vorlesung.support.Constants;

public class CoursesJSONHandler extends Handler {
	
	private CoursesArrayAdapter adapter;

	public CoursesJSONHandler(CoursesArrayAdapter arrayAdapter){
		adapter = arrayAdapter;
	}

	@Override
	public void handleMessage(Message msg) {
		if(msg.arg1 == Constants.MSG_SUCCESS){
			adapter.addCourses((JSONArray)msg.obj);
			Log.d("JSONHandler", "Geladen");
		}
	};
	
}
