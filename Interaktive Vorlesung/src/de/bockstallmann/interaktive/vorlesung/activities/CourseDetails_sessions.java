package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

import java.util.ArrayList;
import de.bockstallmann.interaktive.vorlesung.model.Session;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.SessionsJSONHandler;

public class CourseDetails_sessions extends Activity {

    private ArrayList<Session> sessions;
    private ListView list;
    private CourseDetailsFactory cdf;
	private JSONLoader jsonLoader;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_sessions);
        sessions = new ArrayList<Session>();
        cdf = new CourseDetailsFactory(this, R.layout.session_row, sessions);
        list = (ListView) findViewById(R.id.sessionList);
        
        int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
        
        jsonLoader = new JSONLoader(new Messenger(new SessionsJSONHandler(cdf)));
		jsonLoader.startGetSessionsByCourse(id);
    }
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	//Abfrage hinzufügen
    	
    	
    	list.setAdapter(cdf);    	
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details_sessions, menu);
        return true;
    }
}
