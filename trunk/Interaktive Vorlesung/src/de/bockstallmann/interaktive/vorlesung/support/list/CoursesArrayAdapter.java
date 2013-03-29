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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.listeners.FavorizeClickListener;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.SQLDataHandler;

public class CoursesArrayAdapter extends ArrayAdapter<Course> implements Filterable {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Course> displayItems, allItems;
	private SQLDataHandler sqlData;
	private Comparator<Course> comperator;
	private Filter filter;

	public CoursesArrayAdapter(final Context theContext, final int resourceId, final ArrayList<Course> dbItems, SQLDataHandler data) {
		super(theContext, resourceId, dbItems);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		comperator = new Comparator<Course>() {
			@Override
			public int compare(Course object1, Course object2) {
				return object1.getTitle().compareToIgnoreCase(object2.getTitle());
			}
		};
		// Favoriten sortieren
		displayItems = dbItems;
		Collections.sort(displayItems, comperator);
		allItems = displayItems;
		sqlData = data;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent){
		View view = convertView;

		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.course_row, null);
        }

		view = inflater.inflate(R.layout.course_row, parent, false);
		
		Course course = displayItems.get(position);

		((TextView)view.findViewById(R.id.tx_course_row_title)).setText(course.getTitle());
		((TextView)view.findViewById(R.id.tx_course_row_description)).setText(course.getSemester()+course.getJahr()+"; "+course.getReader());

		// Favicon
		ImageView favicon = (ImageView) view.findViewById(R.id.ic_fav);	

		favicon.setOnClickListener(new FavorizeClickListener(course, favicon, sqlData));
		
		return view;
	}
	@Override
	public int getCount() {
		return displayItems.size();
	}
	
	public void addCourses(JSONArray serverDaten) {
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				if(sqlData.hasCourseId(Integer.parseInt(serverDaten.getJSONObject(i).getString("_id")))) continue;
				allItems.add(new Course(
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
		displayItems = allItems;
		this.notifyDataSetChanged();
	}
	public Course getCourseAtPosition(int position){
		return displayItems.get(position);
	}	
	
	@Override
	public Filter getFilter() {
		if (filter == null){
	      filter  = new CourseFilter();
	    }
	    return filter;
	}
	
	private class CourseFilter extends Filter{
		
	    @Override
	    protected void publishResults(CharSequence prefix,
	                                  FilterResults results) {
	      // NOTE: this function is *always* called from the UI thread.
	    	displayItems =  (ArrayList<Course>)results.values;
	        notifyDataSetChanged();
	    }
	    
	    @Override
		protected FilterResults performFiltering(CharSequence prefix) {
	          // NOTE: this function is *always* called from a background thread, and
	          // not the UI thread. 

	          FilterResults results = new FilterResults();
	          ArrayList<Course> i = new ArrayList<Course>();

	          if (prefix!= null && prefix.toString().length() > 0) {

	              for (int index = 0; index < allItems.size(); index++) {
	                  Course c = allItems.get(index);
	                  if(c.getTitle().toLowerCase().contains(prefix.toString().toLowerCase())){
	                    i.add(c);  
	                  }
	              }
	              results.values = i;
	              results.count = i.size();                   
	          }
	          else{
	              synchronized (allItems){
	                  results.values = allItems;
	                  results.count = allItems.size();
	              }
	          }

	          return results;
	    }
	  }	
}
