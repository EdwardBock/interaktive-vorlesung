package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;

public class CoursesArrayAdapter extends ArrayAdapter<Course> {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Course> items;

	public CoursesArrayAdapter(final Context theContext, final int resourceId, final ArrayList<Course> listeItems) {
		super(theContext, resourceId, listeItems);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		items = listeItems;
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
		((TextView)view.findViewById(R.id.tx_course_row_description)).setText(course.getSemester()+"; "+course.getReader());
		
		return view;
	}

	public ArrayList<Course> getCourseList(){
		return items;
	}
}
