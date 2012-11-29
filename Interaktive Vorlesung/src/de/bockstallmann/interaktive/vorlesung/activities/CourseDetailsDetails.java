package de.bockstallmann.interaktive.vorlesung.activities;

import org.json.JSONArray;
import org.json.JSONException;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseDetailsFactory;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesInfoJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class CourseDetailsDetails extends Activity {

    private TextView short_info;
    private String courseInfo;
    private JSONLoader jsonLoader;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details_details);
        short_info = (TextView)findViewById(R.id.short_info_content);
        int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
        jsonLoader = new JSONLoader(new Messenger(new CoursesInfoJSONHandler(this)));
		jsonLoader.startGetCoursesInfo(id);
    }
	
	@Override
	protected void onResume(){
		super.onResume();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_details_details, menu);
        return true;
    }
    
	public void setCourseInfo(JSONArray serverDaten){
		try {
			courseInfo = serverDaten.getJSONObject(0).getString("info");
			Log.d("Ausgabe",courseInfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.d("CoursesInfo -Problem", "courseInfo");
		}
		short_info.setText(courseInfo);
	}
	
}
