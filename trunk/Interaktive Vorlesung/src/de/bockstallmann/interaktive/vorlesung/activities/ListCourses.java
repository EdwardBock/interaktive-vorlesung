package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;

public class ListCourses extends FragmentActivity implements OnItemClickListener, OnItemLongClickListener {

    private ListView list;
	private CoursesArrayAdapter courseListAdapter;
	protected JSONArray serverDaten;
	private JSONLoader jsonLoader;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_courses);
        
        list = (ListView)findViewById(R.id.lv_courses);
        courseListAdapter = new CoursesArrayAdapter(this, R.layout.course_row, new ArrayList<Course>() );
        
        jsonLoader = new JSONLoader(new Messenger(new CoursesJSONHandler(courseListAdapter)));
		jsonLoader.startGetCourses();
		
    }
	@Override
	protected void onResume() {
		super.onResume();
    	
		// Adapter an ListView übergeben
		list.setAdapter(courseListAdapter);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
		
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_courses, menu);
        return true;
    }
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Toast.makeText(this, "LongClick: Kontext action menü Favoriten", Toast.LENGTH_SHORT).show();
		return false;
	}
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		Course course = (Course)parent.getItemAtPosition(position);
		Intent intent = new Intent(this, CourseDetails.class);
		intent.putExtra(Constants.EXTRA_COURSE_ID, course.getID());
		intent.putExtra(Constants.EXTRA_COURSE_TITLE, course.getTitle());
		intent.putExtra(Constants.EXTRA_COURSE_READER, course.getReader());
		intent.putExtra(Constants.EXTRA_COURSE_PW, course.getPassword());
		startActivity(intent);
	}
	/**
	 * Clickmethode für die Actionbaricons
	 * @param view
	 */
	public void actionbarClick(final View view){
		Toast.makeText(this, "ID: "+view.getId(), Toast.LENGTH_SHORT).show();
		switch (view.getId()) {
			case R.id.actionbar_add:
				startActivity(new Intent(this,SearchCourse.class));
				break;
		}
		
	}
}
