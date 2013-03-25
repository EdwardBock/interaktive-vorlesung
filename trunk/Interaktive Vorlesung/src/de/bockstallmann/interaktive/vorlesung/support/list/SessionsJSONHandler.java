package de.bockstallmann.interaktive.vorlesung.support.list;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import de.bockstallmann.interaktive.vorlesung.support.Constants;

public class SessionsJSONHandler extends Handler {
	
	private SessionsArrayAdapter saa;

	public SessionsJSONHandler(SessionsArrayAdapter sessionsArrayAdapter){
		saa = sessionsArrayAdapter;
	}
	public void handleMessage(Message msg) {
		if(msg.arg1 == Constants.MSG_SUCCESS){
			saa.addSessions((JSONArray) msg.obj);
		}
	};
}
