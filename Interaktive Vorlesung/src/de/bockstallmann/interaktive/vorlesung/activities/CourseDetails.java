package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.listeners.FavorizeActionbarListener;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class CourseDetails extends TabActivity  {

	
    private Course courseObj;
	private FavorizeActionbarListener actionbar_fav_listener;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        
		int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
		String title = getIntent().getExtras().getString(Constants.EXTRA_COURSE_TITLE);
		String reader = getIntent().getExtras().getString(Constants.EXTRA_COURSE_READER);
		String pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
		
		// TODO: Semester und Jahr sind noch Dummie Werte
		courseObj = new Course(id, title, reader, "WS", "2013");
		
        //Sucht den TabHost aus dem Layout
        TabHost tabhost = getTabHost();
        
        //Tab für Details
        TabSpec detailsspec = tabhost.newTabSpec("DETAILS");
        detailsspec.setIndicator("DETAILS");
        Intent detailsIntent = new Intent(this, CourseDetailsDetails.class);
        detailsIntent.putExtra(Constants.EXTRA_COURSE_ID, id);
        detailsspec.setContent(detailsIntent);
        
        //Tab für TERMINE
        TabSpec sessionsspec = tabhost.newTabSpec("TERMINE");
        sessionsspec.setIndicator("TERMINE");
        Intent sessionIntent = new Intent(this, CourseDetailsSessions.class);
        sessionIntent.putExtra(Constants.EXTRA_COURSE_ID, id);
        sessionIntent.putExtra(Constants.EXTRA_COURSE_PW, pw);
        sessionsspec.setContent(sessionIntent);
        
        //Tabs zum TabHost adden
        tabhost.addTab(detailsspec);
        tabhost.addTab(sessionsspec);
        
    }
	@Override
	protected void onResume() {
		super.onResume();
		// Favbutton zum Leben erwecken
		actionbar_fav_listener = new FavorizeActionbarListener(courseObj, (ImageButton)findViewById(R.id.btn_action_fav), new SQLDataHandler(this));
		
	}
	@Override
	protected void onPause() {
		actionbar_fav_listener.destroy();
		super.onPause();
	}
    
    // Clickhandler für die Actionbar
    public void actionbarClick(final View view){
    	switch (view.getId()) {
		case R.id.btn_action_fav:
			// wird von FavorizeActionbarListener verwaltet
			break;
		case R.id.btn_action_info:
			// info einblenden
			break;
		case R.id.actionbar_back:
			finish();
			break;
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details, menu);
        return true;
    }

    
}
