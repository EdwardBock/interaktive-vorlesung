package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class Session_Start extends FragmentActivity {

	String pw;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session__start);
        pw = getIntent().getExtras().getString(Constants.EXTRA_COURSE_PW);
        ImageButton back = (ImageButton) findViewById(R.id.actionbar_back);
		back.setOnClickListener(back_handler);
    }
    
    View.OnClickListener back_handler = new View.OnClickListener() {
        public void onClick(View v) {
          finish();
        }
      };
    
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_session__start, menu);
        return true;
    }
}
