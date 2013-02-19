package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.R.layout;
import de.bockstallmann.interaktive.vorlesung.R.menu;
import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class TransactionType extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_type);
		
		ImageButton btn_qr = (ImageButton) findViewById(R.id.btn_transactiontype_qrcode);
		ImageButton btn_fav = (ImageButton) findViewById(R.id.btn_transactiontype_favourite);
		final Intent intent = new Intent(this, ListCourses.class);
		
		btn_qr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
			}
		});
		btn_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(intent);				
			}
		});
	}

	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){

			String contents = data.getStringExtra("SCAN_RESULT");
            String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
           Toast.makeText(this, contents.substring(contents.indexOf("|")+1), Toast.LENGTH_LONG).show();
           Intent intent = new Intent(this, SessionStart.class);
           intent.putExtra(Constants.EXTRA_SESSION_ID, Integer.parseInt(contents.substring(contents.indexOf("|")+1)));

           startActivity(intent);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_transaction_type, menu);
		return true;
	}
	

}
