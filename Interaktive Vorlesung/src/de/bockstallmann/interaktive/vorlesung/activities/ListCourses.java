package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

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

public class ListCourses extends Activity implements OnItemClickListener, OnItemLongClickListener {

    private ListView list;
	private CoursesArrayAdapter courseListAdapter;
	private ArrayList<Course> courseList;

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
		
		
		
		for (int i = 0; i < 20; i++) {
			courseList.add(new Course("Course: "+i, "bla bla", false));
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(this, CourseDetails.class).putExtra(Constants.EXTRA_COURSE_ID, "id"));
	}

    
}
