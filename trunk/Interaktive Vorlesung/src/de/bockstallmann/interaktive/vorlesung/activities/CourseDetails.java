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
import android.widget.Button;
import android.widget.ImageButton;
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
		String title = getIntent().getExtras().getString(Constants.EXTRA_COURSE_TITLE);
		String reader = getIntent().getExtras().getString(Constants.EXTRA_COURSE_READER);
        
		//Log.d("STATE ede:", "Übergeben: "+String.valueOf(id));
		
		ImageButton back = (ImageButton) findViewById(R.id.actionbar_back);
		back.setOnClickListener(back_handler);
		
        //Sucht den TabHost aus dem Layout
        TabHost tabhost = getTabHost();
        
        //Tab für Details
        TabSpec detailsspec = tabhost.newTabSpec("DETAILS");
        detailsspec.setIndicator("DETAILS");
        Intent detailsIntent = new Intent(this, CourseDetails_details.class);
        detailsIntent.putExtra(Constants.EXTRA_COURSE_ID, id);
        detailsspec.setContent(detailsIntent);
        
        //Tab für TERMINE
        TabSpec sessionsspec = tabhost.newTabSpec("TERMINE");
        sessionsspec.setIndicator("TERMINE");
        Intent sessionIntent = new Intent(this, CourseDetails_sessions.class);
        sessionIntent.putExtra(Constants.EXTRA_COURSE_ID, id);
        sessionsspec.setContent(sessionIntent);
        
        //Tabs zum TabHost adden
        tabhost.addTab(detailsspec);
        tabhost.addTab(sessionsspec);
        
        
    }
    
    View.OnClickListener back_handler = new View.OnClickListener() {
        public void onClick(View v) {
          finish();
        }
      };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details, menu);
        return true;
    }


    
}
