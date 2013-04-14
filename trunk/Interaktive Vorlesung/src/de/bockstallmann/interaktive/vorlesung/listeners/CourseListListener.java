package de.bockstallmann.interaktive.vorlesung.listeners;

import android.os.Messenger;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;

public class CourseListListener implements OnScrollListener, TextWatcher {
	
	private ListView lv_courses;
	private EditText et_search;
	private JSONLoader jsonLoader;
	private int page = 1;
	private RelativeLayout rl_progressbar;
	
	
	public CourseListListener(final ListView list, EditText search, View progressbar) {
		lv_courses = list;
		lv_courses.setOnScrollListener(this);
		et_search = search;
		et_search.addTextChangedListener(this);
		rl_progressbar = (RelativeLayout)progressbar;		
	}
	// Methoden für Scroll Listener
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		/*if( lv_courses.getAdapter().getCount() < 1 || jsonLoader.isAlive()) return;
		if(firstVisibleItem + visibleItemCount >= totalItemCount){
			//loadContent();
			Log.d("ScrollListener", "Muss nachladen?!");
		}*/
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}
	// Methoden für TextWatcher
	@Override
	public void afterTextChanged(Editable s) {}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,	int after) {}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		CoursesArrayAdapter adapter = (CoursesArrayAdapter)this.lv_courses.getAdapter();
		adapter.getFilter().filter(s);
	}
	
	public void loadNextCourses(){
		rl_progressbar.setVisibility(View.VISIBLE);
		jsonLoader = new JSONLoader(new Messenger(new CoursesJSONHandler((CoursesArrayAdapter)lv_courses.getAdapter(), rl_progressbar)));
		if(et_search.getText().toString() == ""){
			jsonLoader.startGetCourses(page++);
		} else {
			jsonLoader.searchCourses(et_search.getText().toString(), page++);
		}
		
	}
	public void destroyListener(){
		et_search.removeTextChangedListener(this);
		lv_courses.setOnScrollListener(null);
	}
}
