package de.bockstallmann.interaktive.vorlesung.activities;

import de.bockstallmann.interaktive.vorlesung.R;
import de.bockstallmann.interaktive.vorlesung.model.Session;
import de.bockstallmann.interaktive.vorlesung.support.Constants;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.inputmethod.InputBinding;
import android.widget.EditText;
import android.widget.Toast;

public class Course_pw_check extends DialogFragment{

	private static String PW;
	private Intent startIntent;
	
	Course_pw_check(String pw, Intent intent){
		PW="bla";
		startIntent= intent;
	} 
	
	


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout

	    builder.setView(inflater.inflate(R.layout.pw_check, null))
	    // Add action buttons
	           
	           .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   Course_pw_check.this.getDialog().cancel();
	               }
	           })
	           .setNeutralButton("Starten", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {  
	            		   EditText ed = (EditText) getDialog().findViewById(R.id.password);
	            		   if(!ed.getText().toString().contentEquals(PW)){
	            			   Toast.makeText(getActivity(), "Falsches Passwort", Toast.LENGTH_LONG).show();
	            		   }else startActivity(startIntent);
	               }
	           }); 
	    builder.setTitle("Test");
	    return builder.create();
	}
}
