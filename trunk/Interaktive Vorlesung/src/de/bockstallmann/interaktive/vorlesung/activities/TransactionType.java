package de.bockstallmann.interaktive.vorlesung.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;
import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.support.Constants;

public class TransactionType extends Activity {

	private  Intent intent;
	private boolean scan_done = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_type);
		intent = new Intent(this, Questions.class);
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(!scan_done){
			Intent intent = new Intent("com.google.zxing.client.android.SCAN.private");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){

			String contents = data.getStringExtra("SCAN_RESULT");
            String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
           Toast.makeText(this, contents, Toast.LENGTH_LONG).show();
           if(contents.contains("|")){
        	   scan_done = true;
               intent.putExtra(Constants.EXTRA_SESSION_ID, Integer.parseInt(contents.substring(contents.indexOf("|")+1)));
               startActivity(intent);
               finish(); 
           }
           
		} else if(resultCode == RESULT_CANCELED){
			finish();		
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
