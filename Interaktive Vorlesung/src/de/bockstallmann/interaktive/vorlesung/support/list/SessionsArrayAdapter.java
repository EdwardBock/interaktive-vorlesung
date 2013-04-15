package de.bockstallmann.interaktive.vorlesung.support.list;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Session;

public class SessionsArrayAdapter extends ArrayAdapter<Session> {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Session> items;

	public SessionsArrayAdapter(final Context theContext, final int resourceId, final ArrayList<Session> items) {
		super(theContext, resourceId, items);
		this.context = theContext;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		this.items = items;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent){
		View view = convertView;

		if (view == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.session_row, null);
        }

		view = inflater.inflate(R.layout.session_row, parent, false);
		
		Session session = items.get(position);

		((TextView)view.findViewById(R.id.tx_session_row_title)).setText(session.getTitle());
		((TextView)view.findViewById(R.id.tx_session_row_description)).setText(session.getRoom()+"; "+session.getBegin()+"-"+session.getEnd());
		
		
		if(session.isArchived()){
			((ImageView)view.findViewById(R.id.iv_session_state)).setBackgroundResource(R.drawable.ic_archive);
		} else if(session.isActive()){
			((ImageView)view.findViewById(R.id.iv_session_state)).setBackgroundResource(R.drawable.ic_online);
		} else {
			((ImageView)view.findViewById(R.id.iv_session_state)).setBackgroundResource(R.drawable.ic_offline);
		}
		
		return view;
	}
	
	public void addSessions(JSONArray serverDaten) {
		for (int i = 0; i < serverDaten.length(); i++) {
			try {
				items.add(new Session(serverDaten.getJSONObject(i).getInt("_id"),
						serverDaten.getJSONObject(i).getString("room"),
						serverDaten.getJSONObject(i).getString("info"),
						serverDaten.getJSONObject(i).getString("date_begin"),
						serverDaten.getJSONObject(i).getString("date_end"),
						serverDaten.getJSONObject(i).getInt("active"),
						serverDaten.getJSONObject(i).getBoolean("archived")));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		this.notifyDataSetChanged();
	}
	public Session getSessionAtPosition(int position){
		return items.get(position);
	}	
	
}
