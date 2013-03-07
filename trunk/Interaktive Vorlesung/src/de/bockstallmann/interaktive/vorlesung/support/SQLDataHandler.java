package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.model.Course;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDataHandler extends SQLiteOpenHelper {
	
	private final static int DB_VERSION = 3;
	
	public static String TABLE_COURSES = "courses";
	public static String TABLE_COURSE_ID = "_id";
	public static String TABLE_COURSE_TITLE = "title";
	public static String TABLE_COURSE_READER = "reader";
	public static String TABLE_COURSE_YEAR = "year";
	public static String TABLE_COURSE_SEMESTER = "semester";
	
	
	
	public SQLDataHandler(final Context context){
		super(context, Constants.DB_NAME, null, DB_VERSION);
	}
	@Override
	public void onCreate(final SQLiteDatabase database) {
		//database.execSQL(Constants.DELETE_TABLE_COURSES);
		database.execSQL(Constants.CREATE_TABLE_COURSES);
	}
	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		// erst bei einem Update von notwendig
		onCreate(db);
	}
	
	public void addCourse(Course course){
		SQLiteDatabase db = this.getWritableDatabase();
		
		String sqlQuery = "SELECT _id FROM " + TABLE_COURSES+" WHERE _id="+course.getID();
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if(!cursor.moveToFirst()){
			ContentValues values = new ContentValues();
			values.put(TABLE_COURSE_ID, course.getID());
			values.put(TABLE_COURSE_TITLE , course.getTitle());
			values.put(TABLE_COURSE_READER , course.getReader());
			values.put(TABLE_COURSE_SEMESTER , course.getSemester());
			values.put(TABLE_COURSE_YEAR , course.getJahr());
			
			db.insert(TABLE_COURSES, null, values);
			db.close();
		}
	}
	
	public ArrayList<Course> getCourses(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Course> courses = new ArrayList<Course>();
		String sqlQuery = "SELECT _id,title,reader,semester,year FROM " + TABLE_COURSES;
		
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if(cursor.moveToFirst()){
			do{
			Course course = new Course(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
			courses.add(course);
			}while(cursor.moveToNext());
		}
		return courses;
	}
	
	public boolean hasCourseId(int id){
		Cursor c = this.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_COURSES+" WHERE "+TABLE_COURSE_ID+" = ?", new String[]{id+""});
		if(c.getCount() > 0) return true;
		return false;
	}
	
	public void deleteCourse(Course course){
		SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSES, TABLE_COURSE_ID + " = ?",
                new String[] { String.valueOf(course.getID()) });
        db.close();
	}
}
