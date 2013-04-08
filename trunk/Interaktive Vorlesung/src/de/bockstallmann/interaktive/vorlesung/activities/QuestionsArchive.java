package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.interfaces.ArchiveObserverInterface;
import de.bockstallmann.interaktive.vorlesung.observer.ArchiveCounterObserver;
import de.bockstallmann.interaktive.vorlesung.observer.CounterObserver;
import de.bockstallmann.interaktive.vorlesung.support.ArchiveObservable;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.SessionArchiveJSONHandler;

public class QuestionsArchive extends Activity implements
		ArchiveObserverInterface {

	private JSONLoader jsonLoader;
	private String pw;
	private int id;
	private ArchiveObservable archiveObservable;
	private ImageView ic_reload;
	private TextView tx_empty_info;
	private ScrollView sv_content;
	private RelativeLayout nav_wrapper;
	private View question_view;
	private TextView tx_actionbar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_archive);

		pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
		id = getIntent().getExtras().getInt(Constants.EXTRA_SESSION_ID);

		archiveObservable = new ArchiveObservable();
		archiveObservable.addObserver(this);
		archiveObservable.addObserver(new ArchiveCounterObserver(
				(TextView) findViewById(R.id.question_counter),
				(TextView) findViewById(R.id.question_overall)));
		
		tx_actionbar = (TextView) findViewById(R.id.tx_actionbar);
		
		ic_reload = (ImageView) findViewById(R.id.img_reload_arrows);
		tx_empty_info = (TextView) findViewById(R.id.tx_empty_info);
		sv_content = (ScrollView) findViewById(R.id.sv_content);
		nav_wrapper = (RelativeLayout) findViewById(R.id.navigation_wrapper);

		jsonLoader = new JSONLoader(new Messenger(
				new SessionArchiveJSONHandler(archiveObservable)));
		jsonLoader.startGetSessionArchive(id);

		showLoading();

	}

	/**
	 * Clickmethode für die Vor-Zurück-Navigation
	 * 
	 * @param view
	 */
	public void navigationClick(final View view) {
		switch (view.getId()) {
		case R.id.btn_next:
			archiveObservable.nextQuestion();
			break;
		case R.id.btn_prev:
			archiveObservable.prevQuestion();
			break;
		}
	}

	/**
	 * Clickmethode für die Actionbaricons
	 * 
	 * @param view
	 */
	public void actionbarClick(final View view) {
		switch (view.getId()) {
		case R.id.actionbar_back:
		case R.id.actionbar_logo:
			finish();
			break;
		}
	}

	@Override
	public void update(String command, ArchiveObservable archiveObservalbe) {
		if (command == ArchiveObservable.CMD_EMPTY) {
			showEmptyInfo();
		} else if (command == ArchiveObservable.CMD_LIST) {
			displayQuestion();
			showContent();
		} else if (command == ArchiveObservable.CMD_CHANGED) {
			displayQuestion();
		}
		updateActionbarText();
	}

	private void updateActionbarText() {
		if(archiveObservable.countQuestions() <=0){
			tx_actionbar.setText("");
		} else {
			tx_actionbar.setText(archiveObservable.getActiveCollection().getTitle());
		}
	}

	private void showLoading() {
		hideContentViews();
		ic_reload.setVisibility(View.VISIBLE);
		ic_reload.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.rotation));
	}

	private void showContent() {
		hideContentViews();
		sv_content.setVisibility(View.VISIBLE);
		nav_wrapper.setVisibility(View.VISIBLE);
	}

	private void showEmptyInfo() {
		hideContentViews();
		tx_empty_info.setVisibility(View.VISIBLE);
	}

	private void hideContentViews() {
		ic_reload.setVisibility(View.GONE);
		ic_reload.clearAnimation();
		tx_empty_info.setVisibility(View.GONE);
		sv_content.setVisibility(View.GONE);
		nav_wrapper.setVisibility(View.GONE);
	}

	private void displayQuestion() {
		sv_content.removeAllViews();
		if (question_view == null) {
			LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			question_view = li.inflate(R.layout.archive_question, null);
		}
		
		((TextView) question_view.findViewById(R.id.tx_question))
				.setText(archiveObservable.getActiveQuestion().getQuestion());

		((TextView) question_view.findViewById(R.id.tx_correct_answer))
				.setText(archiveObservable.getActiveQuestion()
						.getCorrectAnswer());
		
		if(archiveObservable.getActiveQuestion().getCount1() < 1 &&
				archiveObservable.getActiveQuestion().getCount2() < 1 &&
				archiveObservable.getActiveQuestion().getCount3() < 1 &&
				archiveObservable.getActiveQuestion().getCount4() < 1){
			Log.d("QuestionsArchive", "Keine Antworten vorhanden");
		}
		
		((TextView) question_view.findViewById(R.id.tx_a1)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount1()));

		((TextView) question_view.findViewById(R.id.tx_a2)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount2()));

		((TextView) question_view.findViewById(R.id.tx_a3)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount3()));

		((TextView) question_view.findViewById(R.id.tx_a4)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount4()));

		sv_content.addView(question_view);
	}
}
