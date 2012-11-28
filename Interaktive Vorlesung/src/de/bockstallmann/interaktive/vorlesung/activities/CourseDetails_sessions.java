package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.model.Session;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.SessionsJSONHandler;

public class CourseDetails_sessions extends FragmentActivity implements OnItemClickListener {

    private ArrayList<Session> sessions;
    private ListView list;
    private CourseDetailsFactory cdf;
	private JSONLoader jsonLoader;
	private String pw;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_sessions);
        sessions = new ArrayList<Session>();
        cdf = new CourseDetailsFactory(this, R.layout.session_row, sessions);
        list = (ListView) findViewById(R.id.sessionList);
        
        int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
        pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
        
        jsonLoader = new JSONLoader(new Messenger(new SessionsJSONHandler(cdf)));
		jsonLoader.startGetSessionsByCourse(id);
    }
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	//Abfrage hinzuf�gen
    	
    	
    	list.setAdapter(cdf);
    	list.setOnItemClickListener(this);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details_sessions, menu);
        return true;
    }
    
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		
			Session session = (Session)parent.getItemAtPosition(position);
			Intent intent = new Intent(this, Session_Start.class);
			intent.putExtra(Constants.EXTRA_SESSION_ID, session.getID());
			intent.putExtra(Constants.EXTRA_COURSE_PW, pw);
			showPwCheck(intent);
			


	}
	
	private void showPwCheck(Intent intent) {
		FragmentManager fm = getSupportFragmentManager();
		Course_pw_check check = new Course_pw_check(pw,intent);
		check.show(fm, "Passwort checker");	
	}
}
