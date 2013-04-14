package de.bockstallmann.interaktive.vorlesung.listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.FavorizerFactory;
import de.bockstallmann.interaktive.vorlesung.support.PasswordDialog;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class FavorizeActionbarListener implements OnClickListener {

	private SQLDataHandler dh;
	private ImageButton fav;
	private Course courseObj;
	private Context c;
	public FavorizeActionbarListener(Context context, Course course, ImageButton favicon,  SQLDataHandler datahelper) {
		dh = datahelper;
		fav = favicon;
		c = context;
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
			if(courseObj.hasPassword()){
				
				final EditText pw = new EditText(c);
				new PasswordDialog(c, courseObj, pw, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            if(courseObj.getPassword().equals(pw.getEditableText().toString())){
			            	if(FavorizerFactory.favorize(c, dh, courseObj)){
			    				fav.setImageResource(R.drawable.ic_star_on);
			    			}
			            } else {
			            	Toast.makeText(c, "Flasches Passwort!", Toast.LENGTH_SHORT).show();
			            }
			        }
			    }).show();
			} else {
				if(FavorizerFactory.favorize(c, dh, courseObj)){
    				fav.setImageResource(R.drawable.ic_star_on);
    			}
			}
			
		}
	}
	public void destroy() {
		dh.close();
		fav.setOnClickListener(null);
	}

}
