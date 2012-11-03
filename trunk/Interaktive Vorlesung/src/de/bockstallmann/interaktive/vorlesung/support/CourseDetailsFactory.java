package de.bockstallmann.interaktive.vorlesung.support;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class CourseDetailsFactory{
	
	private String courseid;
	private TextView tv;

	public CourseDetailsFactory(final Context theContext, TextView tevi){
		courseid = Constants.EXTRA_COURSE_ID;
		tv = tevi;
	}
	
	public View createTabContent(int tab) {
		switch(tab){
			//Wenn die Details aufgerufen werden
			case 1:
				tv.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
				return tv;
		//Wenn die Termine aufgerufen werden
			case 2:
				break;
		}
		
		
		return null;
	}
	
	

}
