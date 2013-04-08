package de.bockstallmann.interaktive.vorlesung.observer;

import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.interfaces.ArchiveObserverInterface;
import de.bockstallmann.interaktive.vorlesung.interfaces.CollectionObserverInterface;
import de.bockstallmann.interaktive.vorlesung.support.ArchiveObservable;
import de.bockstallmann.interaktive.vorlesung.support.CollectionObservable;

public class ArchiveCounterObserver implements ArchiveObserverInterface {
	private TextView tx_counter, tx_overview;
	private int c, o;

	public ArchiveCounterObserver(TextView counter, TextView overall){
		tx_counter = counter;
		tx_counter.setText("");
		c = 0;
		tx_overview = overall;
		tx_overview.setText("");
		o = 0;
	}

	@Override
	public void update(String command, ArchiveObservable session_archive) {
		tx_counter.setText(Integer.toString(session_archive.getActiveQuestionCountPosition()));
		tx_overview.setText(Integer.toString(session_archive.countQuestions()));
	}
}
