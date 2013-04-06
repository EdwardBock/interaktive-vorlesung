package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import de.bockstallmann.interaktive.vorlesung.R;

public class QuestionsArchive extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_archive);

    }
    /**
	 * Clickmethode für die Vor-Zurück-Navigation
	 * 
	 * @param view
	 */
	public void navigationClick(final View view) {
		switch (view.getId()) {
		case R.id.btn_answer_next:
			break;
		case R.id.btn_answer_prev:
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
