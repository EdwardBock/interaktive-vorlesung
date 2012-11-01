package de.bockstallmann.interaktive.vorlesung.support;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost.TabContentFactory;

public class CourseDetailsFactory implements TabContentFactory{
	
	private String courseid;

	public CourseDetailsFactory(final Context theContext, long id){
		courseid = Constants.EXTRA_COURSE_ID;
		System.out.println(id);
	}
	@Override
	public View createTabContent(String tag) {
		ListView listview;
		
		
		return null;
	}
	
	

}
