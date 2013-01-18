package de.bockstallmann.interaktive.vorlesung.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.model.Question;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.QuestionJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.view.View.OnClickListener;

public class SessionStart extends Activity {

	private String pw;
	private int id;
	private ImageButton btn_prev;
	private ImageButton btn_next;
	private ImageButton btn_reload;
	private Button btn_a1;
	private Button btn_a2;
	private Button btn_a3;
	private Button btn_a4;
	private TextView tv_q;
	
	private JSONLoader jsonLoader;
	private SessionStart act;
	
	private ArrayList<Question> questions;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session__start);
        pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
        id = getIntent().getExtras().getInt(Constants.EXTRA_SESSION_ID);
        questions = new ArrayList<Question>();
        ImageButton back = (ImageButton) findViewById(R.id.actionbar_back);
		back.setOnClickListener(back_handler);
		
		btn_a1 = (Button) findViewById(R.id.btn_session_start_a1);
		btn_a2 = (Button) findViewById(R.id.btn_session_start_a2);
		btn_a3 = (Button) findViewById(R.id.btn_session_start_a3);
		btn_a4 = (Button) findViewById(R.id.btn_session_start_a4);
		tv_q = (TextView) findViewById(R.id.tx_session_start_question);
		act = this;
		btn_reload = (ImageButton) findViewById(R.id.btn_session_start_reload);
		btn_reload.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				jsonLoader = new JSONLoader(new Messenger(new QuestionJSONHandler((act))));
				jsonLoader.startGetQuestionsBySessions(id);
				
			}
		});
		btn_prev = (ImageButton) findViewById(R.id.btn_session_start_previous);
		
		btn_next = (ImageButton) findViewById(R.id.btn_session_start_next);
    }
    
    public void addQuestions(JSONArray serverdaten){
    	for(int i=0;i<serverdaten.length();i++){
    		try {
				questions.add(new Question(
						Integer.parseInt(serverdaten.getJSONObject(i).getString("_id")),
						serverdaten.getJSONObject(i).getString("question"),
						serverdaten.getJSONObject(i).getString("a"),
						serverdaten.getJSONObject(i).getString("b"),
						serverdaten.getJSONObject(i).getString("c"),
						serverdaten.getJSONObject(i).getString("d")));
			
			} catch (Exception e) {
				Log.d("SessionStart",e.toString());
				continue;
			} 
    	}
    	tv_q.setText(questions.get(0).getQuestion());
    	btn_a1.setText(questions.get(0).getAnswer1());
    	btn_a2.setText(questions.get(0).getAnswer2());
    	btn_a3.setText(questions.get(0).getAnswer3());
    	btn_a4.setText(questions.get(0).getAnswer4());
    }
    
    View.OnClickListener back_handler = new View.OnClickListener() {
        public void onClick(View v) {
          finish();
        }
      };
    
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_session__start, menu);
        return true;
    }
}
