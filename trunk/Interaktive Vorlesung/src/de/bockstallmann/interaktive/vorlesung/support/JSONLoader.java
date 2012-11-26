package de.bockstallmann.interaktive.vorlesung.support;

import org.json.JSONArray;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
/**
 * Thread f�r das Laden von Serverdaten. Ruft ServerCommunication auf.
 * @author Edward
 *
 */
public class JSONLoader extends Thread {

	private JSONArray serverDaten;
	private String scriptPath;
	private Messenger m;
	
	public JSONLoader(Messenger messenger){
		m = messenger;
	}
	
	@Override
	public void run() {
		// is loading
		serverDaten = ServerCommunication.getJSONDaten(scriptPath);
		//Log.d("Loader", "serverDaten: "+serverDaten.toString());
		Message message = Message.obtain();
		if(serverDaten == null){
			// had error
			Log.d("Loader", "serverDaten == null");
			message.arg1 = Constants.MSG_ERROR;
		} else {
			Log.d("Loader", "Fertig");
			// is finished
			message.arg1 = Constants.MSG_SUCCESS;
			message.obj = serverDaten;
		}
		try {
			m.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * JSON R�ckgabe des scripts "script_all_courses.php"
	 */
	public void startGetCourses() {
		scriptPath = "script_all_courses.php";
		this.start();
	}
	public void searchCourses(final String search) {
		scriptPath = "script_all_courses.php?search="+search;
		this.start();
	}
	public void startGetCoursesInfo(final int id){
		scriptPath = "script_courses_info.php?id="+id;
		this.start();
	}
	public void startGetSessionsByCourse(final int id){
		scriptPath = "script_all_sessions.php?id="+id;
		this.start();
	}
}
