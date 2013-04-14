package de.bockstallmann.interaktive.vorlesung.support;

import android.content.Context;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.model.Course;

public class FavorizerFactory {
	public static boolean favorize(final Context context, final SQLDataHandler datahelper, final Course course) {
		if(!datahelper.hasCourseId(course.getID()) && datahelper.addCourse(course)){
			Toast.makeText(context, "Favorit hinzugefügt!", Toast.LENGTH_SHORT).show();
			return true;
		}
		Toast.makeText(context, "Konnte Favorit nicht hinzufügen...", Toast.LENGTH_SHORT).show();
		return false;
	}
	public static boolean unfavorize(Context context, SQLDataHandler datahelper, Course course){
		if(datahelper.hasCourseId(course.getID()) &&datahelper.deleteCourse(course)){
			Toast.makeText(context, "Favorit gelöscht!", Toast.LENGTH_SHORT).show();
			return true;
		}
		Toast.makeText(context, "Konnte Favorit nicht löschen...", Toast.LENGTH_SHORT).show();
		return false;
	}
}
