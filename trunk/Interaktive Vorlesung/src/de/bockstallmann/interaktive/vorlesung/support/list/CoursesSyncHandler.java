package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import de.bockstallmann.interaktive.vorlesung.support.Constants;

public class CoursesSyncHandler extends Handler {
	
	private CoursesArrayAdapter adapter;

	public CoursesSyncHandler(CoursesArrayAdapter arrayAdapter){
		adapter = arrayAdapter;
	}

	@Override
	public void handleMessage(Message msg) {
		if(msg.arg1 == Constants.MSG_SUCCESS){
			// msg.obj enthält zeitstempel und course ids. 
			// nicht aktuelle werden aktualisiert
			adapter.addCourses((JSONArray)msg.obj);
			Log.d("JSONHandler", "Geladen");
		}
	};
	
}
