package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import de.bockstallmann.interaktive.vorlesung.activities.CourseDetailsDetails;
import de.bockstallmann.interaktive.vorlesung.activities.CoursePreview;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CoursesInfoJSONHandler extends Handler {
	
	private CourseDetailsDetails coursedetails;
	private CoursePreview coursepreview;

	public CoursesInfoJSONHandler(CourseDetailsDetails courseDetails){
		coursedetails = courseDetails;
	}
	public CoursesInfoJSONHandler(CoursePreview coursePreview){
		coursepreview = coursePreview;
	}
	public void handleMessage(Message msg) {
		Log.d("Handler","in FUnktion");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			if(coursedetails != null){
				coursedetails.setCourseInfo((JSONArray) msg.obj);
			}else if(coursepreview != null){
				coursepreview.setCourseInfo((JSONArray) msg.obj);
			}
		}
	};
}

