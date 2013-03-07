package de.bockstallmann.interaktive.vorlesung.support.list;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class CoursesArrayAdapterFavourite extends ArrayAdapter<Course>{
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Course> items;
	private SQLDataHandler db;
	
	public CoursesArrayAdapterFavourite(Context theContext,  final int resourceId, final ArrayList<Course> listeItems) {
		super(theContext, resourceId, listeItems);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		items = listeItems;
		db  = new SQLDataHandler(theContext);
	}


	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent){
		View view = convertView;
		 
		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.course_row_delete, null);
        }

		view = inflater.inflate(R.layout.course_row_delete, parent, false);
		
		Course course = items.get(position);

		((TextView)view.findViewById(R.id.tx_course_row_delete_title)).setText(course.getTitle());
		((TextView)view.findViewById(R.id.tx_course_row_delete_description)).setText(course.getSemester()+course.getJahr()+"; "+course.getReader());
		
		ImageButton btn_delete = (ImageButton) view.findViewById(R.id.btn_course_row_delete);
		btn_delete.setFocusable(false);
		btn_delete.setFocusableInTouchMode(false);
		btn_delete.setTag(position);
		btn_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteCourse(Integer.parseInt(v.getTag().toString()),items.get(Integer.parseInt(v.getTag().toString())));
			}
		});
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
		//Log.d("DB eintrag", items.get(0).getTitle());
		this.notifyDataSetChanged();
	}
	
	public void deleteCourse(int position, Course course){
		items.remove(position);
		db.deleteCourse(course);
		this.notifyDataSetChanged();
	}
}

