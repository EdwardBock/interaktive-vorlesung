package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapterFavourite;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;

public class ListCourses extends FragmentActivity implements OnItemClickListener, OnItemLongClickListener {

    private ListView list;
	private CoursesArrayAdapterFavourite courseListAdapter;
	private SQLDataHandler db;
	private EditText et_search;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_courses);
        db  = new SQLDataHandler(this);
        //
        list = (ListView)findViewById(R.id.lv_courses);
        courseListAdapter = new CoursesArrayAdapterFavourite(this, R.layout.course_row, new ArrayList<Course>() );
        TextView tv = (TextView) findViewById(R.id.tx_listcourses_emptyList);
		list.setEmptyView(tv);
		et_search = (EditText)findViewById(R.id.et_search);
    }
	@Override
	protected void onResume() {
		super.onResume();
		
		// Adapter an ListView übergeben
		list.setAdapter(courseListAdapter);
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        courseListAdapter.setCourses(db.getCourses());
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
				//startActivity(new Intent(this,SearchCourse.class));
				toogleViewSlide(et_search);
				break;
			case R.id.actionbar_qr:
				Intent intent = new Intent("com.google.zxing.client.android.SCAN.private");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
				break;
		}
		
	}
	private void toogleViewSlide(final View activateView) {
		if (activateView.getVisibility() == View.GONE)
	    {
	        //fade in from top
	        activateView.setVisibility(View.VISIBLE);
	        final Animation fadeInFromTopAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_top);
	        activateView.startAnimation(fadeInFromTopAnimation);
	    } else {
	    	//fade outfrom bottom
	        final Animation fadeInFromTopAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out_from_bottom);
	        fadeInFromTopAnimation.setAnimationListener(new AnimationListener()
            {
                public void onAnimationStart(Animation anim){}
                public void onAnimationEnd(Animation anim) { activateView.setVisibility(View.GONE); }
                public void onAnimationRepeat(Animation anim) {}
            });
	        activateView.startAnimation(fadeInFromTopAnimation);
	        
	    }
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
				//db.addCourse(new Course(db_id,title,reader,semester,year));
				break;
			case 0:
				String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
               Toast toast = Toast.makeText(this, ":"+contents+":", Toast.LENGTH_LONG);
               //toast.setGravity(Gravity.TOP, 25, 400);
               //toast.show();
                //qrcode_read = true;
               //searchCourses(contents);
                break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(et_search.getVisibility() == View.VISIBLE){
				toogleViewSlide(et_search);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
    
}
