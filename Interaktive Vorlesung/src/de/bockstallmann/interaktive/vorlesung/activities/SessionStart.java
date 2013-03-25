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
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.QuestionJSONHandler;
import android.os.Bundle;
import android.os.Messenger;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
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
	private int currentQuestion;
	
	ProgressDialog pd;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session__start);
        pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
        id = getIntent().getExtras().getInt(Constants.EXTRA_SESSION_ID);
        questions = new ArrayList<Question>();
        
        pd = new ProgressDialog(this);
        pd.setMessage("Loading Questions!");
        pd.setCancelable(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
        
		btn_a1 = (Button) findViewById(R.id.btn_session_start_a1);
		btn_a2 = (Button) findViewById(R.id.btn_session_start_a2);
		btn_a3 = (Button) findViewById(R.id.btn_session_start_a3);
		btn_a4 = (Button) findViewById(R.id.btn_session_start_a4);
		btn_a1.setClickable(false);
		btn_a2.setClickable(false);
		btn_a3.setClickable(false);
		btn_a4.setClickable(false);
		tv_q = (TextView) findViewById(R.id.tx_session_start_question);
		act = this;
		btn_reload = (ImageButton) findViewById(R.id.btn_session_start_reload);
		btn_reload.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				pd.show();
				jsonLoader = new JSONLoader(new Messenger(new QuestionJSONHandler(act,1)));
				jsonLoader.startGetQuestionsBySessions(id);
				
			}
		});
		btn_prev = (ImageButton) findViewById(R.id.btn_session_start_previous);
		btn_prev.setClickable(false);
		btn_prev.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(questions.size()>0){
					previousQuestion();		
				}
			}
		});
		
		btn_next = (ImageButton) findViewById(R.id.btn_session_start_next);
		btn_next.setClickable(false);
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(questions.size()>0){
					nextQuestion();
				}
			}
		});
    }
    public void stoploading(){
    	pd.dismiss();
    }
    
    public void addQuestions(JSONArray serverdaten){
    	try {
	    		if(questions.size() > 0 && Integer.parseInt(serverdaten.getJSONObject(0).getString("_id")) == questions.get(0).getId()){
	    			pd.dismiss();
	    			return;
	    		}
	    		questions.clear();
	    		btn_a1.setClickable(true);
	    		btn_a2.setClickable(true);
	    		btn_a3.setClickable(true);
	    		btn_a4.setClickable(true);
	    		btn_prev.setClickable(true);
	    		btn_next.setClickable(true);
	    			for(int i=0;i<serverdaten.length();i++){
	    				questions.add(new Question(
							Integer.parseInt(serverdaten.getJSONObject(i).getString("_id")),
							serverdaten.getJSONObject(i).getString("question"),
							serverdaten.getJSONObject(i).getString("a"),
							serverdaten.getJSONObject(i).getString("b"),
							serverdaten.getJSONObject(i).getString("c"),
							serverdaten.getJSONObject(i).getString("d")));
	    			}
			}catch (Exception e) {
				Log.d("SessionStart",e.toString());
			} 
    	
    	tv_q.setText(questions.get(0).getQuestion());
    	btn_a1.setText(questions.get(0).getAnswer1());
    	btn_a2.setText(questions.get(0).getAnswer2());
    	btn_a3.setText(questions.get(0).getAnswer3());
    	btn_a4.setText(questions.get(0).getAnswer4());
    	pd.dismiss();
    }
    
    public void nextQuestion(){
    	if(currentQuestion < (questions.size()-1)){
    		currentQuestion++;
    	}
    	
    	tv_q.setText(questions.get(currentQuestion).getQuestion());
    	btn_a1.setText(questions.get(currentQuestion).getAnswer1());
    	btn_a2.setText(questions.get(currentQuestion).getAnswer2());
    	btn_a3.setText(questions.get(currentQuestion).getAnswer3());
    	btn_a4.setText(questions.get(currentQuestion).getAnswer4()); 
    	
    	if(questions.get(currentQuestion).getAnswered()){
    		tv_q.setText(questions.get(currentQuestion).getQuestion()+"(bereits beantwortet)");
    		btn_a1.setClickable(false);
    		btn_a2.setClickable(false);
    		btn_a3.setClickable(false);
    		btn_a4.setClickable(false);
    	}else{
    		btn_a1.setClickable(true);
    		btn_a2.setClickable(true);
    		btn_a3.setClickable(true);
    		btn_a4.setClickable(true);
    	}
    }
    
    public void previousQuestion(){
    	if(currentQuestion > 0 ){
    		currentQuestion--;
    	}
    	tv_q.setText(questions.get(currentQuestion).getQuestion());
    	btn_a1.setText(questions.get(currentQuestion).getAnswer1());
    	btn_a2.setText(questions.get(currentQuestion).getAnswer2());
    	btn_a3.setText(questions.get(currentQuestion).getAnswer3());
    	btn_a4.setText(questions.get(currentQuestion).getAnswer4());    
    	
    	if(questions.get(currentQuestion).getAnswered()){
    		tv_q.setText(questions.get(currentQuestion).getQuestion()+"(bereits beantwortet)");
    		btn_a1.setClickable(false);
    		btn_a2.setClickable(false);
    		btn_a3.setClickable(false);
    		btn_a4.setClickable(false);
    	}
    }
    
    public void answeredHandler(final View view){
    	jsonLoader = new JSONLoader(new Messenger(new QuestionJSONHandler(this,0)));
		
    	switch (view.getId()) {
    	case R.id.btn_session_start_a1:
    		questions.get(currentQuestion).setAnswered(true);
    		jsonLoader.countA(questions.get(currentQuestion).getId());
    		nextQuestion();
    		break;
    	case R.id.btn_session_start_a2:
    		questions.get(currentQuestion).setAnswered(true);
    		jsonLoader.countB(questions.get(currentQuestion).getId());
    		nextQuestion();
    		break;
    	case R.id.btn_session_start_a3:
    		questions.get(currentQuestion).setAnswered(true);
    		jsonLoader.countC(questions.get(currentQuestion).getId());
    		nextQuestion();
    		break;
    	case R.id.btn_session_start_a4:
    		questions.get(currentQuestion).setAnswered(true);
    		jsonLoader.countD(questions.get(currentQuestion).getId());
    		nextQuestion();
    		break;
    	}
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
   		}
   	}
    
    
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_session__start, menu);
        return true;
    }
}
