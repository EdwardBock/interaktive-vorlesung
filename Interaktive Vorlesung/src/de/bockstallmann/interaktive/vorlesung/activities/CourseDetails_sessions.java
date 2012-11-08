package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

import java.util.ArrayList;
import de.bockstallmann.interaktive.vorlesung.model.Session;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;

public class CourseDetails_sessions extends Activity {

    private ArrayList<Session> sessions;
    private ListView list;
    private CourseDetailsFactory cdf;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_sessions);
        list = (ListView) findViewById(R.id.sessionList);
        sessions = new ArrayList<Session>();
    }
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	//Abfrage hinzufügen
    	if(sessions.size() < 1){
	    	for(int i = 1; i<=8; i++){
	    		sessions.add(new Session("H1","Einführung in die Thematik "+i,"12","14"));
	    	}
    	}
    	cdf = new CourseDetailsFactory(this, R.layout.session_row, sessions);
    	
    	list.setAdapter(cdf);    	
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details_sessions, menu);
        return true;
    }
}
