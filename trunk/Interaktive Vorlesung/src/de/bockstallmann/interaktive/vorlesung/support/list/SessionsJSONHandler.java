package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import de.bockstallmann.interaktive.vorlesung.support.Constants;

public class SessionsJSONHandler extends Handler {
	
	private SessionsArrayAdapter saa;
	private View progressbar;

	public SessionsJSONHandler(SessionsArrayAdapter sessionsArrayAdapter, View progressbar){
		saa = sessionsArrayAdapter;
		this.progressbar = progressbar;
		this.progressbar.setVisibility(View.VISIBLE);
	}
	@Override
	public void handleMessage(Message msg) {
		if(msg.arg1 == Constants.MSG_SUCCESS){
			saa.addSessions((JSONArray) msg.obj);
		}
		progressbar.setVisibility(View.GONE);
	};
}
