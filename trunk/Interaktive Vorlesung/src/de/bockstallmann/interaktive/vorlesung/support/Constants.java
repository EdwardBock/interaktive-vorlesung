package de.bockstallmann.interaktive.vorlesung.support;

public interface Constants {
	/**
	 * Extras f�r die Activity �bergabe
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
	 * Constants f�r Messenger message.
	 */
	public static final int MSG_ERROR = 0;
	public static final int MSG_SUCCESS = 1;

}
