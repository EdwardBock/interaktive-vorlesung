package de.bockstallmann.interaktive.vorlesung.activities;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Messenger;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import de.bockstallmann.interaktive.vorlesung.support.JSONLoader;
import de.bockstallmann.interaktive.vorlesung.support.list.CoursesInfoJSONHandler;

public class CoursePreview extends FragmentActivity {

    private TextView short_info;
	private JSONLoader jsonLoader;
	private String courseInfo;
	private String pw;
	private Builder builder;
	private EditText pw_input;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_preview);
        short_info = (TextView)findViewById(R.id.short_info_content);
        int id = getIntent().getExtras().getInt(Constants.EXTRA_COURSE_ID);
        String title = getIntent().getExtras().getString(Constants.EXTRA_COURSE_TITLE);
        pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
        
        ((TextView)findViewById(R.id.actionbar_title)).setText(title);
        
        jsonLoader = new JSONLoader(new Messenger(new CoursesInfoJSONHandler(this)));
		jsonLoader.startGetCoursesInfo(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_course_preview, menu);
        return true;
    }
    
    
    public void setCourseInfo(JSONArray serverDaten){
		try {
			courseInfo = serverDaten.getJSONObject(0).getString("info");
			Log.d("Ausgabe",courseInfo);
		} catch (JSONException e) {
			Log.d("CoursesInfo -Problem", "courseInfo");
		}
		short_info.setText(courseInfo);
	}
    
    public void btn_add_click(final View view){
    	if(pw == ""){
    		final Intent intent = new Intent();
			setResult(RESULT_OK, intent);
			finish();
			return;
    	}
    	builder = new Builder(this);
    	builder.setTitle(R.string.dialog_pw_title);
		pw_input = new EditText(this);
		pw_input.setText("");
		pw_input.setHint(R.string.dialog_pw_hint);
		builder.setView(pw_input);
		builder.setPositiveButton(R.string.dialog_btn_ok, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if(!pw_input.getText().toString().contentEquals(pw)){
      			   Toast.makeText(CoursePreview.this, "Falsches Passwort", Toast.LENGTH_LONG).show();
			   } else {
				   final Intent intent = new Intent();
					setResult(RESULT_OK, intent);
					finish();
			   }
			}
		});
		builder.setNegativeButton(R.string.dialog_btn_cancel, null);
		builder.show();
    }
    /**
	 * Clickmethode für die Actionbaricons
	 * @param view
	 */
	public void actionbarClick(final View view){
		switch (view.getId()) {
			case R.id.actionbar_back:
			case R.id.actionbar_logo:
				finish();
				break;
		}
	}
}
