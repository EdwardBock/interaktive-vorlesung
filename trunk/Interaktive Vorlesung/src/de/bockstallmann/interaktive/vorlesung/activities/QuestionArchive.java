package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import de.bockstallmann.interaktive.vorlesung.R;

public class QuestionArchive extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_archive);

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
}
