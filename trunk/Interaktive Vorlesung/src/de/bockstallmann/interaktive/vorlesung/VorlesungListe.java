package de.bockstallmann.interaktive.vorlesung;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class VorlesungListe extends Activity {
	private Button openPdfbtn;
	private Button exitbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vorlesung_liste);
        openPdfbtn = (Button) findViewById(R.id.openPDF_btn);
        openPdfbtn.setOnClickListener(openPDF);
        
        exitbtn = (Button) findViewById(R.id.exit_btn);
        exitbtn.setOnClickListener(exitApp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_vorlesung_liste, menu);
        return true;
    }

    private OnClickListener openPDF = new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent myIntent = new Intent(VorlesungListe.this, PdfReader.class);
			VorlesungListe.this.startActivity(myIntent);
			
		}
    };
    private OnClickListener exitApp = new OnClickListener(){
  		@Override
  		public void onClick(View v) {
  			finish();
  			
  			
  		}
      };
}
