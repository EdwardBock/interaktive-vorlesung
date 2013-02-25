package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.activities.CourseDetailsDetails;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CoursesJSONHandler extends Handler {
	
	private CoursesArrayAdapter adapter;


	public CoursesJSONHandler(CoursesArrayAdapter arrayAdapter){
		adapter = arrayAdapter;
	}

	public void handleMessage(Message msg) {
		if(msg.arg1 == Constants.MSG_SUCCESS){
			adapter.addCourses((JSONArray)msg.obj);
			Log.d("handler", "drinne");
		}
	};
	
}