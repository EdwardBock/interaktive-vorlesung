package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.listeners.FavorizeActionbarListener;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.model.Session;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.CourseInfoJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.SessionsArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.SessionsJSONHandler;

public class ListSessions extends Activity implements OnItemClickListener {

	private int id;
	private String pw;
	private FavorizeActionbarListener actionbar_fav_listener;
	private String title;
	private String reader;
	private Course courseObj;
	private ListView lv_sessions;
	private SessionsArrayAdapter sessionListAdapter;
	private JSONLoader jsonLoader;
	private TextView tx_details;
	private ScrollView sv_details;
	private ImageButton btn_action_info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sessions);

		id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
		title = getIntent().getExtras().getString(Constants.EXTRA_COURSE_TITLE);
		reader = getIntent().getExtras().getString(
				Constants.EXTRA_COURSE_READER);
		pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);

		// TODO: Semester und Jahr sind noch Dummie Werte
		courseObj = new Course(id, title, reader, "WS", "2013");

		lv_sessions = (ListView) findViewById(R.id.lv_sessions);
		tx_details = (TextView) findViewById(R.id.tx_course_details);
		sv_details = (ScrollView) findViewById(R.id.sv_course_details);
		btn_action_info = (ImageButton) findViewById(R.id.btn_action_info);

		sessionListAdapter = new SessionsArrayAdapter(this,
				R.layout.session_row, new ArrayList<Session>());
		lv_sessions.setAdapter(sessionListAdapter);
		jsonLoader = new JSONLoader(new Messenger(new SessionsJSONHandler(
				(SessionsArrayAdapter) lv_sessions.getAdapter())));
		jsonLoader.startGetSessionsByCourse(id);

	}

	@Override
	protected void onResume() {
		lv_sessions.setOnItemClickListener(this);
		actionbar_fav_listener = new FavorizeActionbarListener(courseObj,
				(ImageButton) findViewById(R.id.btn_action_fav),
				new SQLDataHandler(this));
		super.onResume();
	}

	@Override
	protected void onPause() {
		actionbar_fav_listener.destroy();
		super.onPause();
	}

	// Clickhandler f�r die Actionbar
	public void actionbarClick(final View view) {
		switch (view.getId()) {
		case R.id.btn_action_fav:
			// wird von FavorizeActionbarListener verwaltet
			break;
		case R.id.btn_action_info:
			toggleInfo();
			break;
		case R.id.actionbar_back:
			finish();
			break;
		}
	}

	private void toggleInfo() {
		
		if (tx_details.getText().length() < 1) {
			tx_details.setText("L�dt Daten");
			jsonLoader = new JSONLoader(new Messenger(new CourseInfoJSONHandler(tx_details)));
			jsonLoader.startGetCoursesInfo(id);
		}
		if (sv_details.getVisibility() == View.GONE) {
			Log.d("Toggle", "Visible");
			sv_details.setVisibility(View.VISIBLE);
			final Animation fadeInFromTopAnimation = AnimationUtils
					.loadAnimation(this, R.anim.slide_in_from_top);
			sv_details.startAnimation(fadeInFromTopAnimation);
			btn_action_info.setImageResource(R.drawable.ic_menu_close);
		} else {
			final Animation fadeInFromTopAnimation = AnimationUtils
					.loadAnimation(this, R.anim.slide_out_from_bottom);
			fadeInFromTopAnimation
					.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationStart(Animation anim) {
						}
						@Override
						public void onAnimationEnd(Animation anim) {
							sv_details.setVisibility(View.GONE);
							btn_action_info.setImageResource(R.drawable.ic_menu_info);
						}
						@Override
						public void onAnimationRepeat(Animation anim) {
						}
					});
			sv_details.startAnimation(fadeInFromTopAnimation);
			sv_details.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemClick(final AdapterView<?> parent, final View view,
			final int position, final long id) {
		Session session = (Session) parent.getItemAtPosition(position);
		Intent intent = new Intent(this, Questions.class);
		intent.putExtra(Constants.EXTRA_SESSION_ID, session.getID());
		intent.putExtra(Constants.EXTRA_COURSE_PW, pw);
		startActivity(intent);
		// showPwCheck(intent);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if(sv_details.getVisibility() == View.VISIBLE){
				toggleInfo();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
