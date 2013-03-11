package de.bockstallmann.interaktive.vorlesung.listeners;

import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesArrayAdapter;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesJSONHandler;
import android.os.Messenger;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;

public class CourseListListener implements OnScrollListener, TextWatcher {
	
	private ListView lv_courses;
	private EditText et_search;
	private JSONLoader jsonLoader;
	private int page = 1;
	public CourseListListener(final ListView list, EditText search) {
		lv_courses = list;
		lv_courses.setOnScrollListener(this);
		et_search = search;
		et_search.addTextChangedListener(this);
		loadNextCourses();
		
	}
	// Methoden für Scroll Listener
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
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
	
	private void loadNextCourses(){
		jsonLoader = new JSONLoader(new Messenger(new CoursesJSONHandler((CoursesArrayAdapter)lv_courses.getAdapter())));
		if(et_search.getText().toString() == ""){
			jsonLoader.startGetCourses(page++);
		} else {
			jsonLoader.searchCourses(et_search.getText().toString(), page++);
		}
		
	}
}
