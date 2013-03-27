package de.bockstallmann.interaktive.vorlesung.observer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.interfaces.CollectionObserverInterface;
import de.bockstallmann.interaktive.vorlesung.support.CollectionObservable;

public class QuestionObserver implements CollectionObserverInterface, OnClickListener {
	
	private View question_view;
	private Button btn_a, btn_b, btn_c, btn_d, btn_skip;
	private Drawable res_selected;
	private Drawable res_unselected;
	private int res_selected_font_color, res_unselected_font_color;
	private Context c;
	private CollectionObservable collection;
	private TextView tx_question;

	public QuestionObserver(Context context, ScrollView scrollView,
			CollectionObservable collectionObservable) {
		c = context;
		collection = collectionObservable;
		scrollView.removeAllViews();

		if (question_view == null) {
			LayoutInflater li = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			question_view = li.inflate(R.layout.question_single, null);
		}
		tx_question = (TextView) question_view
				.findViewById(R.id.tx_question);
		btn_a = (Button) question_view.findViewById(R.id.btn_answer_a);
		btn_b = (Button) question_view.findViewById(R.id.btn_answer_b);
		btn_c = (Button) question_view.findViewById(R.id.btn_answer_c);
		btn_d = (Button) question_view.findViewById(R.id.btn_answer_d);
		btn_skip = (Button) question_view.findViewById(R.id.btn_answer_skip);
		btn_a.setOnClickListener(this);
		btn_b.setOnClickListener(this);
		btn_c.setOnClickListener(this);
		btn_d.setOnClickListener(this);
		btn_skip.setOnClickListener(this);
		res_selected = c.getResources().getDrawable(
				R.drawable.btn_answer_selected);
		res_selected_font_color = c.getResources().getColor(
				R.color.btn_answer_selected_font);
		res_unselected = c.getResources().getDrawable(
				R.drawable.btn_answer_unselected);
		res_unselected_font_color = c.getResources().getColor(
				R.color.btn_answer_unselected_font);

		scrollView.addView(question_view);
	}

	@Override
	public void update(String command, CollectionObservable collection) {
		if (command == CollectionObservable.CMD_ANSWER) {
			resetButtons();
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
			
		} else if (command == CollectionObservable.CMD_LIST && collection.size()> 0) {
			tx_question.setText(collection.getQuestion().getQuestion());
			resetButtons();
			btn_a.setText(collection.getQuestion().getAnswer1());
			btn_b.setText(collection.getQuestion().getAnswer2());
			btn_c.setText(collection.getQuestion().getAnswer3());
			btn_d.setText(collection.getQuestion().getAnswer4());
		}
		if (collection.getAnswer() == CollectionObservable.NONE) {
			btn_skip.setText(R.string.questions_btn_skip);
		} else {
			btn_skip.setText(R.string.questions_btn_send);
		}
	}

	private void resetButtons() {
		btn_a.setBackgroundDrawable(res_unselected);
		btn_b.setBackgroundDrawable(res_unselected);
		btn_c.setBackgroundDrawable(res_unselected);
		btn_d.setBackgroundDrawable(res_unselected);
		btn_a.setTextColor(res_unselected_font_color);
		btn_b.setTextColor(res_unselected_font_color);
		btn_c.setTextColor(res_unselected_font_color);
		btn_d.setTextColor(res_unselected_font_color);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
			if (collection.getAnswer() == CollectionObservable.NONE) {
				Log.d("Questions", "Frage übersprichen und hinten anreihen");
				if (!collection.preventAndNext()) {
					Toast.makeText(c, "Letzte Frage", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Log.d("Questions", "Antwort abschicken");
				collection.saveAnswerAndNext();
			}
			break;
		default:
			break;
		}
	}

}
