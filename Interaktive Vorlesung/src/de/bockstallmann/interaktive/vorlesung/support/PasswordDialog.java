package de.bockstallmann.interaktive.vorlesung.support;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import de.bockstallmann.interaktive.vorlesung.model.Course;

public class PasswordDialog extends AlertDialog {

	public PasswordDialog(Context context, Course course, EditText pw, OnClickListener okClick) {
		super(context);
		this.setTitle(course.getTitle());
	    this.setMessage("Bitte gib das Passwort ein:");
	    this.setView(pw);
	    this.setButton("Ok", okClick);
	    this.setButton2("Abbrechen",new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
	}
	
}
