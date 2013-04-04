package de.bockstallmann.interaktive.vorlesung.support;

import java.util.ArrayList;

import de.bockstallmann.interaktive.vorlesung.model.Course;
import de.bockstallmann.interaktive.vorlesung.model.Question;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLDataHandler extends SQLiteOpenHelper {

	private final static int DB_VERSION = 4;

	public SQLDataHandler(final Context context) {
		super(context, Constants.DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(final SQLiteDatabase database) {
		database.execSQL(Constants.CREATE_TABLE_COURSES);
		database.execSQL(Constants.CREATE_TABLE_QUESTS);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		// erst bei einem Update von notwendig
		if(oldVersion == 3){ 
			db.execSQL(Constants.CREATE_TABLE_QUESTS);
		}
		onCreate(db);
	}
	/**
	 * Kurs zur Datenbank hinzufügen.
	 * @param course
	 * @return
	 */
	public boolean addCourse(Course course) {
		long ret = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		String sqlQuery = "SELECT _id FROM " + Constants.TABLE_COURSES + " WHERE _id="
				+ course.getID();
		Cursor cursor = db.rawQuery(sqlQuery, null);
		if (!cursor.moveToFirst()) {
			ContentValues values = new ContentValues();
			values.put(Constants.TABLE_COURSE_ID, course.getID());
			values.put(Constants.TABLE_COURSE_TITLE, course.getTitle());
			values.put(Constants.TABLE_COURSE_READER, course.getReader());
			values.put(Constants.TABLE_COURSE_SEMESTER, course.getSemester());
			values.put(Constants.TABLE_COURSE_YEAR, course.getJahr());

			ret = db.insert(Constants.TABLE_COURSES, null, values);
			db.close();
		}
		if (ret > 0)
			return true;
		return false;
	}

	public ArrayList<Course> getCourses() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Course> courses = new ArrayList<Course>();
		String sqlQuery = "SELECT _id,title,reader,semester,year FROM "
				+ Constants.TABLE_COURSES;

		Cursor cursor = db.rawQuery(sqlQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Course course = new Course(
						Integer.parseInt(cursor.getString(0)),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4));
				courses.add(course);
			} while (cursor.moveToNext());
		}
		db.close();
		return courses;
	}

	public boolean hasCourseId(int id) {
		boolean bool = false;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_COURSES + " WHERE "
				+ Constants.TABLE_COURSE_ID + " = ?", new String[] { id + "" });
		if (c.getCount() > 0)
			return true;
		c.close();
		db.close();
		return bool;
	}

	public boolean deleteCourse(Course course) {
		int ret = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		ret = db.delete(Constants.TABLE_COURSES, Constants.TABLE_COURSE_ID + " = ?",
				new String[] { String.valueOf(course.getID()) });
		db.close();
		if (ret > 0)
			return true;
		return false;
	}
	/**
	 * Question zur Datenbank hinzufügen.
	 * @param course
	 * @return
	 */
	public boolean addQuestion(Question question) {
		long ret = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Constants.TABLE_QUEST_ID, question.getId());
		values.put(Constants.TABLE_QUEST_QUESTION, question.getQuestion());
		values.put(Constants.TABLE_QUEST_ANSWER, question.getCorrectAnswer());

		ret = db.insert(Constants.TABLE_QUESTS, null, values);
		db.close();
		
		if (ret > 0)
			return true;
		return false;
	}

	public boolean hasQuestion(int id) {
		boolean bool = false;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + Constants.TABLE_QUESTS + " WHERE "
				+ Constants.TABLE_QUEST_ID + " = ?", new String[] { id + "" });
		if (c.getCount() > 0) return true;
		c.close();
		db.close();
		return bool;
	}
}
