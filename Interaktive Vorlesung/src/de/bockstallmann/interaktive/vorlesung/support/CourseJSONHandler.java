package de.bockstallmann.interaktive.vorlesung.support;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class CourseJSONHandler extends Handler {

	private TextView cd;

	public CourseJSONHandler(TextView courseDetails) {
		cd = courseDetails;
	}

	@Override
	public void handleMessage(Message msg) {
		if (msg.arg1 == Constants.MSG_SUCCESS) {
			JSONArray json = (JSONArray) msg.obj;
			try {
				cd.setText( json.getJSONObject(0).getString("info"));
			} catch (JSONException e) {
				cd.setText( msg.obj.toString());
				e.printStackTrace();
			}
		}
	};
}
