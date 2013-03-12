package de.bockstallmann.interaktive.vorlesung.listeners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class FavorizeActionbarListener implements OnClickListener {

	private SQLDataHandler dh;
	private ImageButton fav;
	private Course courseObj;
	public FavorizeActionbarListener(Course course, ImageButton favicon,  SQLDataHandler datahelper) {
		dh = datahelper;
		fav = favicon;
		fav.setOnClickListener(this);
		courseObj = course;
		if(dh.hasCourseId(course.getID()))	favicon.setImageResource(R.drawable.ic_menu_star_on);
	}
	@Override
	public void onClick(View v) {
		if(dh.hasCourseId(courseObj.getID())){
			if(dh.deleteCourse(this.courseObj)){
				this.fav.setImageResource(R.drawable.ic_menu_star_off);
			}
		} else {
			if(dh.addCourse(this.courseObj)){
				this.fav.setImageResource(R.drawable.ic_menu_star_on);
			}
		}
	}
	public void destroy() {
		dh.close();
		fav.setOnClickListener(null);
	}

}
