package de.bockstallmann.interaktive.vorlesung.support.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class CoursesArrayAdapter extends ArrayAdapter<Course> {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Course> items;
	private SQLDataHandler sqlData;
	private Comparator<Course> comperator;

	public CoursesArrayAdapter(final Context theContext, final int resourceId, final ArrayList<Course> listeItems, SQLDataHandler data) {
		super(theContext, resourceId, listeItems);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		items = listeItems;
		sqlData = data;
		comperator = new Comparator<Course>() {
			@Override
			public int compare(Course object1, Course object2) {
				return object1.getTitle().compareToIgnoreCase(object2.getTitle());
			}
		};
		sortList(items);
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent){
		View view = convertView;

		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.course_row, null);
        }

		view = inflater.inflate(R.layout.course_row, parent, false);
		
		Course course = items.get(position);

		((TextView)view.findViewById(R.id.tx_course_row_title)).setText(course.getTitle());
		((TextView)view.findViewById(R.id.tx_course_row_description)).setText(course.getSemester()+course.getJahr()+"; "+course.getReader());

		// Favicon
		ImageView favicon = (ImageView) view.findViewById(R.id.ic_fav);
		if(sqlData.hasCourseId(course.getID()))	favicon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_on));
		
		Log.d("AdapterView", "jetzt ausgeführt");
		return view;
	}

	public ArrayList<Course> getCourseList(){
		return items;
	}
	
	public void setCourses(ArrayList<Course> course){
		//items = course;
		items.clear();
		for(int i = 0; i< course.size();i++){
			items.add(course.get(i));
		}
		this.notifyDataSetChanged();
	}
	private void sortList(ArrayList<Course> courses) {
		Collections.sort(courses, comperator);
		items = courses;
	}

	public void addCourses(JSONArray serverDaten) {
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				if(sqlData.hasCourseId(Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")))) continue;
				items.add(new Course(
						Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")), 
						serverDaten.getJSONObject(i).getString("title"), 
						serverDaten.getJSONObject(i).getString("user_id"),
						serverDaten.getJSONObject(i).getString("semester"),
						serverDaten.getJSONObject(i).getString("year"),
						serverDaten.getJSONObject(i).getString("pw")));
			} catch (Exception e) {
				Log.d("CoursesAdapter", "problem bei i = "+i);
				continue;
			}
		}
		this.notifyDataSetChanged();
	}
	public Course getCourseAtPosition(int position){
		return items.get(position);
	}
}
