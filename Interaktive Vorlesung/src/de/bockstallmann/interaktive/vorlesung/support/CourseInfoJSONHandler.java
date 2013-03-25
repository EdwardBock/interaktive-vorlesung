package de.bockstallmann.interaktive.vorlesung.support;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class CourseInfoJSONHandler extends Handler {

	private TextView cd;

	public CourseInfoJSONHandler(TextView courseDetails) {
		cd = courseDetails;
	}

	public void handleMessage(Message msg) {
		if (msg.arg1 == Constants.MSG_SUCCESS) {
			cd.setText( msg.obj.toString());
		}
	};
}
