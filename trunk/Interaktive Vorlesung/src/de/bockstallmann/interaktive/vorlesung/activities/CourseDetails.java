package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.support.v4.app.NavUtils;

public class CourseDetails extends TabActivity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        
        TabHost tabhost = getTabHost();
        
        //Tab f�r Details
        TabSpec detailsspec = tabhost.newTabSpec("DETAILS");
        detailsspec.setIndicator("DATEILS");
        Intent detailsIntent = new Intent(this, CourseDetails_details.class);
        detailsspec.setContent(detailsIntent);
        
        //Tab f�r TERMINE
        TabSpec sessionsspec = tabhost.newTabSpec("TERMINE");
        sessionsspec.setIndicator("TERMINE");
        Intent sessionIntent = new Intent(this, CourseDetails_sessions.class);
        sessionsspec.setContent(sessionIntent);
        
        tabhost.addTab(detailsspec);
        tabhost.addTab(sessionsspec);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details, menu);
        return true;
    }


    
}
