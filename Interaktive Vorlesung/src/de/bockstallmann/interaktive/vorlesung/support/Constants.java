package de.bockstallmann.interaktive.vorlesung.support;

public interface Constants {
	
	public static final int RC_ADD_COURSE = 1;
	
	/**
	 * Extras f�r die Activity �bergabe
	 */
	public static final String EXTRA_COURSE_ID = "course_id";
	public static final String EXTRA_COURSE_TITLE = "course_title";
	public static final String EXTRA_COURSE_READER = "course_reader";
	public static final String EXTRA_COURSE_PW ="course_pw";
	public static final String EXTRA_COURSE_SEMESTER = "course_semester";
	public static final String EXTRA_COURSE_YEAR = "course_year";
	
	
	/**
	 * Extras f�r die Session�bergabe
	 */
	public static final String EXTRA_SESSION_ID = "session_id";
	
	
	
	/**
	 * JSON Serverresponse Namen.
	 */
	public static final String JSON_STATUS = "status";
	public static final String JSON_DATEN = "daten";
	public static final String JSON_MESSAGE = "message";
	
	
	/**
	 * Constants f�r Messenger message.
	 */
	public static final int MSG_ERROR = 0;
	public static final int MSG_SUCCESS = 1;
	/**
	 * SQL-Lite Konstanten
	 */
	public static final String DB_NAME = "InterVorlesung";
	public static final String TABLE_COURSES = "courses";
	public static final String TABLE_COURSE_ID = "_id";
	public static final String TABLE_COURSE_TITLE = "title";
	public static final String TABLE_COURSE_READER = "reader";
	public static final String TABLE_COURSE_YEAR = "year";
	public static final String TABLE_COURSE_SEMESTER = "semester";
	public static final String TABLE_COURSE_PW = "pw";
	public static final String DELETE_TABLE_COURSES = "DROP TABLE TABLE_COURSES";
	public static final String CREATE_TABLE_COURSES = "CREATE TABLE IF NOT EXISTS " + TABLE_COURSES +
			" ("+
			TABLE_COURSE_ID+" INTEGER PRIMARY KEY," +
			TABLE_COURSE_TITLE+" VARCHAR(100) NOT NULL, " +
			TABLE_COURSE_READER+" VARCHAR(100)NOT NULL, " +
			TABLE_COURSE_SEMESTER+" VARCHAR(10)NOT NULL, " +
			TABLE_COURSE_YEAR+" VARCHAR(10)NOT NULL) "; /*+
			TABLE_COURSE_USER_ID+" INTEGER," +
			TABLE_COURSE_SEMESTER+" VARCHAR(10) NOT NULL," +
			TABLE_COURSE_PW+" VARCHAR(40)"
			+" )";*/

}
