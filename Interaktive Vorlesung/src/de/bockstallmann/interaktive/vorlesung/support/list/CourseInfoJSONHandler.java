package de.bockstallmann.interaktive.vorlesung.support.list;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.support.Constants;

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
