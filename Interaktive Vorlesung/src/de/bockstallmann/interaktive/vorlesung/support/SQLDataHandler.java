package de.bockstallmann.interaktive.vorlesung.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDataHandler extends SQLiteOpenHelper {
	
	private final static int DB_VERSION = 3;
	
	public SQLDataHandler(final Context context){
		super(context, Constants.DB_NAME, null, DB_VERSION);
	}
	@Override
	public void onCreate(final SQLiteDatabase database) {
		database.execSQL(Constants.CREATE_TABLE_COURSES);
	}
	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// erst bei einem Update von notwendig
		onCreate(db);
	}
}
