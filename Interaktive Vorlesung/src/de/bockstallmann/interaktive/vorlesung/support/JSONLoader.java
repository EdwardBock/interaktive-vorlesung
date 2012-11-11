package de.bockstallmann.interaktive.vorlesung.support;

import org.json.JSONArray;

import android.os.Handler;
import android.util.Log;
/**
 * Thread für das Laden von Serverdaten. Ruft ServerCommunication auf.
 * @author Edward
 *
 */
public class JSONLoader extends Thread {

	private JSONArray serverDaten;
	private String scriptPath;
	
	public JSONLoader(){
	}
	
	@Override
	public void run() {
		// is loading
		serverDaten = ServerCommunication.getJSONDaten(scriptPath);
		Log.d("Loader", "serverDaten: "+serverDaten.toString());
		if(serverDaten == null){
			// had error
			Log.d("Loader", "serverDaten == null");
		} else {
			Log.d("Loader", "Fertig");
			// is finished
		}		
	}
	/**
	 * JSON Rückgabe des scripts "script_all_courses.php"
	 */
	public void startGetCourses() {
		scriptPath = "script_all_courses.php";
		this.start();
	}
}
