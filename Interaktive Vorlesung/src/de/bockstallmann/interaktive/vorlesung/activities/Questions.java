package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.interfaces.CollectionObserver;
import de.bockstallmann.interaktive.vorlesung.model.Question;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CollectionJSONHandler;
import de.bockstallmann.interaktive.vorlesung.support.list.CollectionObservable;

public class Questions extends Activity implements OnClickListener,
		CollectionObserver {

	private String pw;
	private int id;
	private CollectionObservable collection;
	private ImageButton btn_reload;
	private JSONLoader jsonLoader;
	private ScrollView sv_question;
	private LayoutInflater li;
	private View question_view;
	private Button btn_a, btn_b, btn_c, btn_d, btn_skip;
	private Drawable res_selected;
	private Drawable res_unselected;
	private int res_selected_font_color, res_unselected_font_color;

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

		// Fragenliste wird von der Activity überwacht
		collection = new CollectionObservable();
		collection.addObserver(this);
	}

	@Override
	public void update(String command) {
		if (command == CollectionObservable.CMD_LIST) {
			Log.d("Questions", "Collection meldet veränderung! Size: "
					+ collection.size());
			if (collection.size() > 0) {
				showQuestion(collection.getQuestion());
			} else {
				showReloadButton();
			}
		} else if (command == CollectionObservable.CMD_ANSWER) {
			btn_a.setBackgroundDrawable(res_unselected);
			btn_b.setBackgroundDrawable(res_unselected);
			btn_c.setBackgroundDrawable(res_unselected);
			btn_d.setBackgroundDrawable(res_unselected);
			btn_a.setTextColor(res_unselected_font_color);
			btn_b.setTextColor(res_unselected_font_color);
			btn_c.setTextColor(res_unselected_font_color);
			btn_d.setTextColor(res_unselected_font_color);
			if (collection.getAnswer() == CollectionObservable.A) {
				btn_a.setBackgroundDrawable(res_selected);
				btn_a.setTextColor(res_selected_font_color);
			} else if (collection.getAnswer() == CollectionObservable.B) {
				btn_b.setBackgroundDrawable(res_selected);
				btn_b.setTextColor(res_selected_font_color);
			} else if (collection.getAnswer() == CollectionObservable.C) {
				btn_c.setBackgroundDrawable(res_selected);
				btn_c.setTextColor(res_selected_font_color);
			} else if (collection.getAnswer() == CollectionObservable.D) {
				btn_d.setBackgroundDrawable(res_selected);
				btn_d.setTextColor(res_selected_font_color);
			}
			if (collection.getAnswer() == CollectionObservable.NONE) {
				btn_skip.setText(R.string.questions_btn_skip);
			} else {
				btn_skip.setText(R.string.questions_btn_send);
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
		Log.d("Questions", "Frage zeigen: " + question.getQuestion());
		if (sv_question.getVisibility() == View.GONE) {
			btn_reload.setVisibility(View.GONE);
			sv_question.setVisibility(View.VISIBLE);
		}
		sv_question.removeAllViews();
		if (question_view == null) {
			li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			question_view = li.inflate(R.layout.question_single, null);
		}
		TextView tx_question = (TextView) question_view
				.findViewById(R.id.tx_question);
		tx_question.setText(question.getQuestion());
		if (btn_a == null || btn_b == null || btn_c == null || btn_d == null
				|| btn_skip == null) {
			btn_a = (Button) question_view.findViewById(R.id.btn_answer_a);
			btn_b = (Button) question_view.findViewById(R.id.btn_answer_b);
			btn_c = (Button) question_view.findViewById(R.id.btn_answer_c);
			btn_d = (Button) question_view.findViewById(R.id.btn_answer_d);
			btn_skip = (Button) question_view
					.findViewById(R.id.btn_answer_skip);
			btn_a.setOnClickListener(this);
			btn_b.setOnClickListener(this);
			btn_c.setOnClickListener(this);
			btn_d.setOnClickListener(this);
			btn_skip.setOnClickListener(this);
			res_selected = getResources().getDrawable(
					R.drawable.btn_answer_selected);
			res_selected_font_color = getResources().getColor(
					R.color.btn_answer_selected_font);
			res_unselected = getResources().getDrawable(
					R.drawable.btn_answer_unselected);
			res_unselected_font_color = getResources().getColor(
					R.color.btn_answer_unselected_font);
		}

		btn_a.setText(question.getAnswer1());
		btn_b.setText(question.getAnswer2());
		btn_c.setText(question.getAnswer3());
		btn_d.setText(question.getAnswer4());
		sv_question.addView(question_view);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reload_questions:
			jsonLoader = new JSONLoader(new Messenger(
					new CollectionJSONHandler(collection)));
			jsonLoader.startGetCollectionBySession(id);
			break;
		case R.id.btn_answer_a:
			collection.setAnswer(CollectionObservable.A);
			break;
		case R.id.btn_answer_b:
			collection.setAnswer(CollectionObservable.B);
			break;
		case R.id.btn_answer_c:
			collection.setAnswer(CollectionObservable.C);
			break;
		case R.id.btn_answer_d:
			collection.setAnswer(CollectionObservable.D);
			break;
		case R.id.btn_answer_skip:
			if(collection.getAnswer() == CollectionObservable.NONE){
				Log.d("Questions", "Frage übersprichen und hinten anreihen");
				if(!collection.preventAndNext()){
					Toast.makeText(this, "Letzte Frage", Toast.LENGTH_SHORT).show();
				}
			} else {
				Log.d("Questions", "Antwort abschicken");
			}
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
