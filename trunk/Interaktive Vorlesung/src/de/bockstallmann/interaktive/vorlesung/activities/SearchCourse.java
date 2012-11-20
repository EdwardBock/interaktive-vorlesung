package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Messenger;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;

public class SearchCourse extends Activity {

    private ListView list;
	private CoursesArrayAdapter courseListAdapter;
	private JSONLoader jsonLoader;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        
        list = (ListView)findViewById(R.id.lv_courses);
        courseListAdapter = new CoursesArrayAdapter(this, R.layout.course_row, new ArrayList<Course>() );
        
        jsonLoader = new JSONLoader(new Messenger(new CoursesJSONHandler(courseListAdapter)));
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_search_course, menu);
        return true;
    }
    
    /**
	 * Clickmethode für die Actionbaricons
	 * @param view
	 */
	public void actionbarClick(final View view){
		switch (view.getId()) {
			case R.id.actionbar_back:
			case R.id.actionbar_logo:
				finish();
				break;
		}
	}
    
}
