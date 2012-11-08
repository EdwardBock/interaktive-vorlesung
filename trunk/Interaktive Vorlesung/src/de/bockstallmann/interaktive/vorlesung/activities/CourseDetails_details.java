package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class CourseDetails_details extends Activity {

    private TextView short_info;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_details);
        
        int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
        //Abfrage hinzufügen       
    }
	
	@Override
	protected void onResume(){
		super.onResume();
		short_info = (TextView)findViewById(R.id.short_info_content);
        short_info.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details_details, menu);
        return true;
    }
}
