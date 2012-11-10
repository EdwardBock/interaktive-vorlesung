package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import de.bockstallmann.interaktive.vorlesung.support.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.ServerCommunication;

public class ListCourses extends Activity implements OnItemClickListener, OnItemLongClickListener {

    private ListView list;
	private CoursesArrayAdapter courseListAdapter;
	private ArrayList<Course> courseList;
	private Thread thread;
	protected JSONArray serverDaten;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_courses);
        
        list = (ListView)findViewById(R.id.lv_courses);
        courseList = new ArrayList<Course>();
        
    }
	@Override
	protected void onResume() {
		super.onResume();
		
		thread = new Thread(new Runnable() {
			public void run() {			
				serverDaten = ServerCommunication.getJSONDaten("script_all_courses.php");
            }
        });
		
    	thread.start();
    	
		if( courseList.size() < 1 ){
			for (int i = 0; i < 20; i++) {
				courseList.add(new Course(i,"Course: "+i, "Prof Dr. rer Nat Jedermann","WS 12/13", false));
			}
		}
		
		
		courseListAdapter = new CoursesArrayAdapter(this, R.layout.course_row, courseList );
		
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
		Toast.makeText(this, "LongClick: Kontext action menü", Toast.LENGTH_SHORT).show();
		return false;
	}
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		Course course = (Course)parent.getItemAtPosition(position);
		Intent intent = new Intent(this, CourseDetails.class);
		intent.putExtra(Constants.EXTRA_COURSE_ID, course.getID());
		intent.putExtra(Constants.EXTRA_COURSE_TITLE, course.getTitle());
		intent.putExtra(Constants.EXTRA_COURSE_READER, course.getReader());
		startActivity(intent);
	}    
}
