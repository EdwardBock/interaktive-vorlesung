package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.interfaces.ArchiveObserverInterface;
import de.bockstallmann.interaktive.vorlesung.observer.ArchiveCounterObserver;
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
	private TextView tx_empty_info;
	private ScrollView sv_content;
	private RelativeLayout nav_wrapper;
	private View question_view;
	private TextView tx_actionbar;
	private RelativeLayout rl_progressbar;
	private LayoutInflater li;
	private String title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_archive);

		pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
		id = getIntent().getExtras().getInt(Constants.EXTRA_SESSION_ID);
		title = getIntent().getExtras().getString(Constants.EXTRA_SESSION_TITLE);

		archiveObservable = new ArchiveObservable();
		archiveObservable.addObserver(this);
		archiveObservable.addObserver(new ArchiveCounterObserver(
				(TextView) findViewById(R.id.question_counter),
				(TextView) findViewById(R.id.question_overall)));

		tx_actionbar = (TextView) findViewById(R.id.tx_actionbar);
		tx_actionbar.setText(title);
		
		rl_progressbar = (RelativeLayout) findViewById(R.id.rl_progressbar);
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
	}

	private void showLoading() {
		hideContentViews();
		rl_progressbar.setVisibility(View.VISIBLE);
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
		rl_progressbar.setVisibility(View.GONE);
		tx_empty_info.setVisibility(View.GONE);
		sv_content.setVisibility(View.GONE);
		nav_wrapper.setVisibility(View.GONE);
	}

	private void displayQuestion() {
		sv_content.removeAllViews();
		if (li == null) {
			li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		question_view = li.inflate(R.layout.archive_question, null);
		((TextView) question_view.findViewById(R.id.tx_question))
				.setText(archiveObservable.getActiveQuestion().getQuestion());

		((TextView) question_view.findViewById(R.id.tx_correct_answer))
				.setText(archiveObservable.getActiveQuestion()
						.getCorrectAnswer());

		if (archiveObservable.getActiveQuestion().getCount1() < 1
				&& archiveObservable.getActiveQuestion().getCount2() < 1
				&& archiveObservable.getActiveQuestion().getCount3() < 1
				&& archiveObservable.getActiveQuestion().getCount4() < 1) {
			Log.d("QuestionsArchive", "Keine Antworten vorhanden");
		}
		int r_id = -1;
		switch(archiveObservable.getActiveQuestion().getCorrectAnswerChar()) {
		case 'a':
			r_id = R.id.tx_a1;
			break;
		case 'b':
			r_id = R.id.tx_a2;
			break;
		case 'c':
			r_id = R.id.tx_a3;
			break;
		case 'd':
			r_id = R.id.tx_a4;
			break;

		}
		if(r_id != -1){
			((TextView) question_view.findViewById(r_id)).setBackgroundResource(R.drawable.bg_archive_answer_correct);
		}
		
		((TextView) question_view.findViewById(R.id.tx_1)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount1()));
		
		((TextView) question_view.findViewById(R.id.tx_2)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount2()));

		((TextView) question_view.findViewById(R.id.tx_3)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount3()));

		((TextView) question_view.findViewById(R.id.tx_4)).setText(Integer
				.toString(archiveObservable.getActiveQuestion().getCount4()));

		sv_content.addView(question_view);
	}
}
