package de.bockstallmann.interaktive.vorlesung.listeners;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.FavorizerFactory;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class FavorizeClickListener implements OnClickListener {

	private SQLDataHandler dh;
	private ImageView fav;
	private Course courseObj;
	private Context c;
	private ImageView l;
	public FavorizeClickListener(Context context, Course course, ImageView favicon, ImageView lock,  SQLDataHandler datahelper) {
		dh = datahelper;
		fav = favicon;
		l = lock;
		courseObj = course;
		c = context;
		if(dh.hasCourseId(course.getID()))	favicon.setImageResource(R.drawable.ic_star_on);
	}
	@Override
	public void onClick(View v) {
		if(dh.hasCourseId(courseObj.getID())){
			if(FavorizerFactory.unfavorize(c,dh,this.courseObj)){
				this.fav.setImageResource(R.drawable.ic_star_off);
				this.l.setImageResource(R.drawable.ic_lock_closed);
			}
		} else {
			final EditText password = new EditText(c);
			new AlertDialog.Builder(c)
		    .setTitle(courseObj.getTitle())
		    .setMessage("Bitte gib das Passwort ein:")
		    .setView(password)
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            Editable value = password.getText(); 
		            Log.d("PW", value.toString()+"|"+courseObj.getPassword()+"|");
		            if(courseObj.getPassword().equals(value.toString())){
		            	if(FavorizerFactory.favorize(c, dh, courseObj)){
		    				fav.setImageResource(R.drawable.ic_star_on);
		    				l.setImageResource(R.drawable.ic_lock_open);
		    			}
		            } else {
		            	Toast.makeText(c, "Flasches Passwort!", Toast.LENGTH_SHORT).show();
		            }
		        }
		    }).setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		        }
		    }).show();
		}
	}

}
