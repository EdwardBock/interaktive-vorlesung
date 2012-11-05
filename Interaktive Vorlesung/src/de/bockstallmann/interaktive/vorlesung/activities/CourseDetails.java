package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class CourseDetails extends TabActivity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        
		int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
        
		Log.d("STATE ede:", "Übergeben: "+String.valueOf(id));
		
        //Sucht den TabHost aus dem Layout
        TabHost tabhost = getTabHost();
        
        //Tab für Details
        TabSpec detailsspec = tabhost.newTabSpec("DETAILS");
        detailsspec.setIndicator("DATEILS");
        Intent detailsIntent = new Intent(this, CourseDetails_details.class);
        detailsspec.setContent(detailsIntent);
        
        //Tab für TERMINE
        TabSpec sessionsspec = tabhost.newTabSpec("TERMINE");
        sessionsspec.setIndicator("TERMINE");
        Intent sessionIntent = new Intent(this, CourseDetails_sessions.class);
        sessionsspec.setContent(sessionIntent);
        
        //Tabs zum TabHost adden
        tabhost.addTab(detailsspec);
        tabhost.addTab(sessionsspec);
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details, menu);
        return true;
    }


    
}
