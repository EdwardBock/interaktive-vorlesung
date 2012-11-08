package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;
import java.util.List;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.model.Session;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class CourseDetailsFactory extends ArrayAdapter<Session>{
	
	private int id;
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Session> sessions;
	
	public CourseDetailsFactory(Context theContext, int textViewResourceId,ArrayList<Session> Sessions) {
		super(theContext, textViewResourceId, Sessions);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		sessions = Sessions;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.session_row, null);
        }
		view = inflater.inflate(R.layout.session_row, parent, false);
		
		Session session = sessions.get(position);
		
		((TextView) view.findViewById(R.id.tx_session_row_title)).setText(session.getTitle());
		((TextView) view.findViewById(R.id.tx_session_row_description)).setText(session.getRoom()+"; "+session.getBegin()+"-"+session.getEnd()+" Uhr 08.11.2012");
		
		return view;
		
	}
	
	

}
