package de.bockstallmann.interaktive.vorlesung.observer;

import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.interfaces.CollectionObserverInterface;
import de.bockstallmann.interaktive.vorlesung.support.CollectionObservable;

public class CounterObserver implements CollectionObserverInterface {
	private TextView tx_counter, tx_overview;
	private int c, o;

	public CounterObserver(TextView counter, TextView overall){
		tx_counter = counter;
		tx_counter.setText("");
		c = 0;
		tx_overview = overall;
		tx_overview.setText("");
		o = 0;
	}

	@Override
	public void update(String command, CollectionObservable collection) {
		if(command == CollectionObservable.CMD_LIST){
			o = collection.size();
			if(c >= o){
				if(o == 0){
					c = 0;
				} else {
					c = 1;
				}
				
			} else {
				c++;
			}
			updateDisplay();
		}
	}
	public void updateDisplay(){
		tx_overview.setText(Integer.toString(o));
		tx_counter.setText(Integer.toString(c));
	}
}
