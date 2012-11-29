package de.bockstallmann.interaktive.vorlesung.activities;

import org.json.JSONArray;
import org.json.JSONException;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesInfoJSONHandler;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CoursePreview extends FragmentActivity {

    private TextView short_info;
	private JSONLoader jsonLoader;
	private String courseInfo;
	private String pw;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_preview);
        short_info = (TextView)findViewById(R.id.short_info_content);
        int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
        String title = getIntent().getExtras().getString(Constants.EXTRA_COURSE_TITLE);
        pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
        jsonLoader = new JSONLoader(new Messenger(new CoursesInfoJSONHandler(this)));
		jsonLoader.startGetCoursesInfo(id);
		
		
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_preview, menu);
        return true;
    }
    
    
    public void setCourseInfo(JSONArray serverDaten){
		try {
			courseInfo = serverDaten.getJSONObject(0).getString("info");
			
			Log.d("Ausgabe",courseInfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("CoursesInfo -Problem", "courseInfo");
		}
		short_info.setText(courseInfo);
	}
    
    public void btn_add_click(final View view){
    	Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
 	    // Get the layout inflater
 	    LayoutInflater inflater = this.getLayoutInflater();

 	    // Inflate and set the layout for the dialog
 	    // Pass null as the parent view because its going in the dialog layout

 	    builder.setView(inflater.inflate(R.layout.pw_check, null))
 	    // Add action buttons
 	           
 	           .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
 	               public void onClick(DialogInterface dialog, int id) {
 	            	   dialog.cancel();
 	               }
 	           })
 	           .setPositiveButton("Starten", new DialogInterface.OnClickListener() {
 	               @Override
 	               public void onClick(DialogInterface dialog, int id) {  
 	            		   EditText ed = (EditText) findViewById(R.id.password);
 	            		   if(!ed.getText().toString().contentEquals(pw)){
 	            			   Toast.makeText(CoursePreview.this, "Falsches Passwort", Toast.LENGTH_LONG).show();
 	            		   }else Toast.makeText(CoursePreview.this, "eingabe richtig", Toast.LENGTH_LONG).show();;
 	               }
 	           }); 
 	    builder.setTitle("Die Vorlesung ist Passwortgeschützt!");
 	    builder.create();
    }
}
