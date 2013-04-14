package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.listeners.CourseListListener;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.PasswordDialog;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;

public class ListCourses extends FragmentActivity implements
		OnItemClickListener, OnItemLongClickListener {

	private ListView list;
	private CoursesArrayAdapter courseListAdapter;
	private SQLDataHandler db;
	private EditText et_search;
	private CourseListListener courseListListener;
	private CoursesArrayAdapter savedAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_courses);

		// Dynamische und interaktive Views heraussuchen
		list = (ListView) findViewById(R.id.lv_courses);
		TextView tv = (TextView) findViewById(R.id.tx_listcourses_emptyList);
		list.setEmptyView(tv);
		et_search = (EditText) findViewById(R.id.et_search);

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Alle Datenbank Kurse anzeigen
		db = new SQLDataHandler(this);
		if(savedAdapter == null){
			courseListAdapter = new CoursesArrayAdapter(this, R.layout.course_row,
				db.getCourses(), db);
		} else {
			courseListAdapter = savedAdapter;
		}
		
		// Adapter und Listener übergeben
		list.setAdapter(courseListAdapter);
		list.setOnItemClickListener(this);
		list.setOnItemLongClickListener(this);
		courseListListener = new CourseListListener(list, et_search,
				findViewById(R.id.rl_progressbar));
		if(savedAdapter == null){
			courseListListener.loadNextCourses();
		}
		
	}

	@Override
	protected void onStop() {
		savedAdapter = (CoursesArrayAdapter) list.getAdapter();
		courseListListener.destroyListener();
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_list_courses, menu);
		return true;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Toast.makeText(this, "LongClick: Kontext action menü Favoriten",
				Toast.LENGTH_SHORT).show();
		return false;
	}

	@Override
	public void onItemClick(final AdapterView<?> parent, final View view,
			final int position, final long id) {
		final Course course = (Course) parent.getItemAtPosition(position);
		final Intent intent = buildSessionIntent(course);
		if(course.hasPassword() && !db.hasCourseId(course.getID())){
			final EditText pw= new EditText(this);
			new PasswordDialog(this, course, pw, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            Editable value = pw.getText(); 
		            if(course.getPassword().equals(value.toString())){
		            	startActivity(intent);
		            } else {
		            	Toast.makeText(ListCourses.this, "Flasches Passwort!", Toast.LENGTH_SHORT).show();
		            }
		        }
		    }).show();
		} else {
			startActivity(intent);
		}
	}
	
	private Intent buildSessionIntent(Course course){
		Intent intent = new Intent(this, ListSessions.class)
		.putExtra(Constants.EXTRA_COURSE_ID, course.getID())
		.putExtra(Constants.EXTRA_COURSE_TITLE, course.getTitle())
		.putExtra(Constants.EXTRA_COURSE_READER, course.getReader())
		.putExtra(Constants.EXTRA_COURSE_SEMESTER, course.getSemester())
		.putExtra(Constants.EXTRA_COURSE_YEAR, course.getYear())
		.putExtra(Constants.EXTRA_COURSE_PW, course.getPassword());
		return intent;
	}

	/**
	 * Clickmethode für die Actionbaricons
	 * 
	 * @param view
	 */
	public void actionbarClick(final View view) {
		switch (view.getId()) {
		case R.id.actionbar_add:
			// startActivity(new Intent(this,SearchCourse.class));
			toggleViewSlide(et_search);
			break;
		case R.id.actionbar_qr:
			Intent intent = new Intent(
					"com.google.zxing.client.android.SCAN.private");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, Constants.RC_QR_CODE);
			break;
		}

	}

	private void toggleViewSlide(final View activateView) {
		if (activateView.getVisibility() == View.GONE) {
			// fade in from top
			activateView.setVisibility(View.VISIBLE);
			// final Animation fadeInFromTopAnimation =
			// AnimationUtils.loadAnimation(this, R.anim.fade_in_from_top);
			// activateView.startAnimation(fadeInFromTopAnimation);
			et_search.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT);
		} else {
			// fade outfrom bottom
			/*
			 * final Animation fadeInFromTopAnimation =
			 * AnimationUtils.loadAnimation(this, R.anim.fade_out_from_bottom);
			 * fadeInFromTopAnimation.setAnimationListener(new
			 * AnimationListener() { public void onAnimationStart(Animation
			 * anim){} public void onAnimationEnd(Animation anim) {
			 * activateView.setVisibility(View.GONE); } public void
			 * onAnimationRepeat(Animation anim) {} });
			 * activateView.startAnimation(fadeInFromTopAnimation);
			 */
			activateView.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Constants.RC_QR_CODE:
				String contents = data.getStringExtra("SCAN_RESULT");
				//String format = data.getStringExtra("SCAN_RESULT_FORMAT");
				if (!contents.matches(Constants.QR_REGEX)) {
					Toast.makeText(this, "Konnte keine Präsentation finden...",	Toast.LENGTH_SHORT).show();
					return;
				}
				
				Matcher values = Pattern.compile(Constants.QR_REGEX).matcher(contents);
				
				String ret = "";
				for(int i = 0; i < values.groupCount(); i++){
					ret += values.group(i)+" ";
				}
				Toast.makeText(this, ret, Toast.LENGTH_LONG).show();
				// TODO: PW abfragen und favorit speichern
				if(true) return;
				int session_id = Integer.parseInt(values.group(3));
				int course_id = Integer.parseInt(values.group(2));				
				Toast.makeText(this ,"Starte course: "+course_id+"  session: "+session_id, Toast.LENGTH_SHORT).show();
				Course this_course = courseListAdapter.getCourseById(course_id);
				if(this_course == null){
					Toast.makeText(this ,"Veranstaltung nicht gefunden...", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = buildSessionIntent(this_course);
				intent.putExtra("session_id", session_id);
				startActivity(intent);
				break;
			default:
				break;
			}
		} else {
			Log.d("ListCourses", "ResultCode: "+resultCode);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (et_search.getVisibility() == View.VISIBLE) {
				et_search.setText("");
				toggleViewSlide(et_search);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
