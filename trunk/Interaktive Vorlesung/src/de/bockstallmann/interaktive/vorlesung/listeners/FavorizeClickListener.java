package de.bockstallmann.interaktive.vorlesung.listeners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class FavorizeClickListener implements OnClickListener {

	private SQLDataHandler dh;
	private ImageView fav;
	private Course courseObj;
	public FavorizeClickListener(Course course, ImageView favicon,  SQLDataHandler datahelper) {
		dh = datahelper;
		fav = favicon;
		courseObj = course;
		if(dh.hasCourseId(course.getID()))	favicon.setImageResource(R.drawable.ic_star_on);
	}
	@Override
	public void onClick(View v) {
		if(dh.hasCourseId(courseObj.getID())){
			if(dh.deleteCourse(this.courseObj)){
				this.fav.setImageResource(R.drawable.ic_star_off);
			}
		} else {
			if(dh.addCourse(this.courseObj)){
				this.fav.setImageResource(R.drawable.ic_star_on);
			}
		}
	}

}
