package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Messenger;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.interfaces.CollectionObserverInterface;
import de.bockstallmann.interaktive.vorlesung.model.Question;
import de.bockstallmann.interaktive.vorlesung.observer.CounterObserver;
import de.bockstallmann.interaktive.vorlesung.observer.QuestionObserver;
import de.bockstallmann.interaktive.vorlesung.support.CollectionJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.CollectionObservable;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;

public class Questions extends Activity implements OnClickListener,
		CollectionObserverInterface {

	private String pw;
	private int id;
	private CollectionObservable collection;
	private ImageButton btn_reload;
	private JSONLoader jsonLoader;
	private ScrollView sv_question;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);

		pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
		id = getIntent().getExtras().getInt(Constants.EXTRA_SESSION_ID);

		// layout elements
		sv_question = (ScrollView) findViewById(R.id.sv_question);
		btn_reload = (ImageButton) findViewById(R.id.btn_reload_questions);
		btn_reload.setOnClickListener(this);
		

		// Observable wird erstellt und Observer hinzugefügt
		collection = new CollectionObservable();
		collection.addObserver(this);
		collection.addObserver(new QuestionObserver(this, sv_question, collection));
		collection.addObserver(new CounterObserver(
				(TextView)findViewById(R.id.question_counter),
				(TextView)findViewById(R.id.question_overall)));
	}

	@Override
	public void update(String command, CollectionObservable collectionObservable) {
		if (command == CollectionObservable.CMD_LIST) {
			if (collection.size() > 0) {
				showQuestion(collection.getQuestion());
			} else {
				showReloadButton();
			}
		}
	}

	private void showReloadButton() {
		if (btn_reload.getVisibility() == View.GONE) {
			sv_question.setVisibility(View.GONE);
			btn_reload.setVisibility(View.VISIBLE);
		}
	}

	private void showQuestion(Question question) {
		if (sv_question.getVisibility() == View.GONE) {
			btn_reload.setVisibility(View.GONE);
			sv_question.setVisibility(View.VISIBLE);
		}		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reload_questions:
			jsonLoader = new JSONLoader(new Messenger(
					new CollectionJSONHandler(collection)));
			jsonLoader.startGetCollectionBySession(id);
			break;
		default:
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
}
