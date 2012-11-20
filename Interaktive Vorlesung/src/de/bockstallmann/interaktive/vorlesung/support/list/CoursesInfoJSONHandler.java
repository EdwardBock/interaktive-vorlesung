package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.activities.CourseDetails_details;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CoursesInfoJSONHandler extends Handler {
	
	private CourseDetails_details coursedetails;

	public CoursesInfoJSONHandler(CourseDetails_details CourseDetails){
		coursedetails = CourseDetails;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			coursedetails.setCourseInfo((JSONArray) msg.obj);
		}
	};
}

