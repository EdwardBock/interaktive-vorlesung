package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;

public class SearchCourse extends Activity implements OnItemClickListener {

    private ListView list;
	private CoursesArrayAdapter courseListAdapter;
	private JSONLoader jsonLoader;
	private EditText et_search;
	private String search;
	private SQLDataHandler db;
	private int db_id ;
	private String semester;
	private String year;
	private String title;
	private String reader;
	private boolean qrcode_read = false;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_course);
        
        db = new SQLDataHandler(this);
        
        list = (ListView)findViewById(R.id.lv_courses);
        courseListAdapter = new CoursesArrayAdapter(this, R.layout.course_row, new ArrayList<Course>() );
        list.setOnItemClickListener(this);
        et_search = (EditText)findViewById(R.id.et_search);
        //QR-Code lesen
        try {
        	ImageButton scanner = (ImageButton)findViewById(R.id.btn_search_course_qr);
            scanner.setOnClickListener(new OnClickListener() {
                
                public void onClick(View v) {
                	Intent intent = new Intent("com.google.zxing.client.android.SCAN.private");
    				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
    				startActivityForResult(intent, 0);
                }
 
            });
             
        } catch (ActivityNotFoundException anfe) {
            Log.e("onCreate", "Scanner Not Found", anfe);
        }
        
    }
	@Override
	protected void onResume() {
		list.setAdapter(courseListAdapter);
		if(courseListAdapter.getCount() < 1 && !qrcode_read){
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
				search = et_search.getText().toString();
				searchCourses(search);
				break;
		}
	}
    private void searchCourses(String search1){
    	//Toast.makeText(this, search1, Toast.LENGTH_LONG).show();
    	//if(search1.length() < 1) return;
    	if(jsonLoader != null && jsonLoader.isAlive()){
    		jsonLoader.stop();
    		jsonLoader.destroy();
    	}
    	jsonLoader = new JSONLoader(new Messenger(new CoursesJSONHandler(courseListAdapter)));
    	courseListAdapter.clear();
		jsonLoader.searchCourses(search1);
    }
	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
		Course course = (Course)parent.getItemAtPosition(position);
		
		if(course.hasPassword()){
			Toast.makeText(this, "Brauche PW", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Füge zu SQL-Datenbank hinzu", Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent(this, CoursePreview.class);
		intent.putExtra(Constants.EXTRA_COURSE_ID, course.getID());
		intent.putExtra(Constants.EXTRA_COURSE_TITLE, course.getTitle());
		intent.putExtra(Constants.EXTRA_COURSE_PW, course.getPassword());
		db_id = course.getID();
		title = course.getTitle();
		semester = course.getSemester();
		year = course.getJahr();
		reader = course.getReader();
		

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
				
				//SQLite Datenbank füllen
				db.addCourse(new Course(db_id,title,reader,semester,year));
				finish();
				break;
			case 0:
				String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
               //Toast toast = Toast.makeText(this, ":"+contents+":", Toast.LENGTH_LONG);
               //toast.setGravity(Gravity.TOP, 25, 400);
               //toast.show();
                qrcode_read = true;
               searchCourses(contents);
                break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
