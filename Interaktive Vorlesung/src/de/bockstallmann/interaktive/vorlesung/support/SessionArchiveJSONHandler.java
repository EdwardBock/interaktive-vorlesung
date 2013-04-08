package de.bockstallmann.interaktive.vorlesung.support;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SessionArchiveJSONHandler extends Handler{
	
	private ArchiveObservable ao;
	
	public SessionArchiveJSONHandler(ArchiveObservable archiveObservable) {
		ao = archiveObservable;
	}
	@Override
	public void handleMessage(Message msg) {
		Log.d("Handler Session Archive","läuft");
		if(msg.arg1 == Constants.MSG_SUCCESS){
			ao.setCollections((JSONArray) msg.obj);
		}
	};
}
