package de.bockstallmann.interaktive.vorlesung.support;

public interface Constants {
	/**
	 * Extras für die Activity übergabe
	 */
	public static final String EXTRA_COURSE_ID = "course_id";
	public static final String EXTRA_COURSE_TITLE = "course_title";
	public static final String EXTRA_COURSE_READER = "course_reader";
	
	/**
	 * JSON Serverresponse Namen.
	 */
	public static final String JSON_STATUS = "status";
	public static final String JSON_DATEN = "daten";
	public static final String JSON_MESSAGE = "message";
	
	/**
	 * Constants für Messenger message.
	 */
	public static final int MSG_ERROR = 0;
	public static final int MSG_SUCCESS = 1;
	/**
	 * SQL-Lite Konstanten
	 */
	public static final String DB_NAME = null;
	public static final String TABLE_COURSES = "courses";
	public static final String TABLE_COURSE_ID = "_id";
	public static final String TABLE_COURSE_TITLE = "title";
	public static final String TABLE_COURSE_DESCRIPTION = "description";
	public static final String TABLE_COURSE_USER_ID = "user_id";
	public static final String TABLE_COURSE_SEMESTER = "semester";
	public static final String TABLE_COURSE_PW = "pw";
	public static final String CREATE_TABLE_COURSES = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSES +
			" ("+
			TABLE_COURSE_ID+" INTEGER PRIMARY KEY," +
			TABLE_COURSE_TITLE+" VARCHAR(100) NOT NULL, " +
			TABLE_COURSE_DESCRIPTION+" TEXT, " +
			TABLE_COURSE_USER_ID+" INTEGER," +
			TABLE_COURSE_SEMESTER+" VARCHAR(10) NOT NULL," +
			TABLE_COURSE_PW+" VARCHAR(40)"
			+" )";

}
