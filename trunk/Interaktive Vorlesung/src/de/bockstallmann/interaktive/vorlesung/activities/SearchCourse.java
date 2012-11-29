package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;

public class SearchCourse extends Activity implements OnItemClickListener {

    private ListView list;
	private CoursesArrayAdapter courseListAdapter;
	private JSONLoader jsonLoader;
	private EditText et_search;
	private String search;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        
        list = (ListView)findViewById(R.id.lv_courses);
        courseListAdapter = new CoursesArrayAdapter(this, R.layout.course_row, new ArrayList<Course>() );
        list.setOnItemClickListener(this);
        et_search = (EditText)findViewById(R.id.et_search);
    }
	@Override
	protected void onResume() {
		list.setAdapter(courseListAdapter);
		if(courseListAdapter.getCount() < 1){
			jsonLoader = new JSONLoader(new Messenger(new CoursesJSONHandler(courseListAdapter)));
			jsonLoader.startGetCourses();
		}
		super.onResume();
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
			case R.id.btn_search:
				searchCourses();
				break;
		}
	}
    private void searchCourses(){
    	search = et_search.getText().toString();
    	if(search.length() < 1) return;
    	if(jsonLoader != null && jsonLoader.isAlive()){
    		jsonLoader.stop();
    		jsonLoader.destroy();
    	}
    	jsonLoader = new JSONLoader(new Messenger(new CoursesJSONHandler(courseListAdapter)));
    	courseListAdapter.clear();
		jsonLoader.searchCourses(search);
    }
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
		Course course = (Course)parent.getItemAtPosition(position);
		
		if(course.hasPassword()){
			Toast.makeText(this, "Brauche PW", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Füge zu SQL-Datenbank hinzu", Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent(this, CoursePreview.class);
		intent.putExtra(Constants.EXTRA_COURSE_ID, course.getID());
		intent.putExtra(Constants.EXTRA_COURSE_TITLE, course.getTitle());
		intent.putExtra(Constants.EXTRA_COURSE_READER, course.getReader());
		intent.putExtra(Constants.EXTRA_COURSE_PW, course.getPassword());
		startActivityForResult(intent, Constants.RC_ADD_COURSE);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case Constants.RC_ADD_COURSE:
				Toast.makeText(this, 
						"OK! Zur Datenbank hinzufügen", 
						Toast.LENGTH_SHORT).show();
				finish();
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
