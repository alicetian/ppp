package edu.calstatela.cs594.mapcampusmap;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	// fields for the DatabaseOpenHelper
	final static String DB_NAME = "coursework_db";
	final private static Integer VERSION = 1;
	final static String _ID = "id";
	//---------------------------------------------------------
	final static String COLUMN_CLASSROOM_NAME = "name";
	final static String COLUMN_CLASSROOM_LAT = "lat";
	final static String COLUMN_CLASSROOM_LON = "lon";

	// fields for the Adapter
	private Context context;
	private DatabaseOpenHelper helper;
	private SQLiteDatabase sqldb;
	private String[] classRoomColumns = { _ID, COLUMN_CLASSROOM_NAME,
			COLUMN_CLASSROOM_LAT, COLUMN_CLASSROOM_LON };


	public DBAdapter(Context c) {
		this.context = c;
	}

	public DBAdapter open()  throws SQLException {
		helper = new DatabaseOpenHelper(context);
		sqldb = helper.getWritableDatabase();
//		insertClassRoom("ETA220", 35.0, 127.0);
		return this;
	}

	

	public void close() {
		helper.close();
	}
	
	public long insertClassRoom(String name, Double lat, Double lon) {
		ContentValues vals = new ContentValues();
		vals.put(COLUMN_CLASSROOM_NAME, name);
		vals.put(COLUMN_CLASSROOM_LAT, lat);
		vals.put(COLUMN_CLASSROOM_LON, lon);
		return sqldb.insert("classrooms", null, vals);
	}
	
	public void deleteClassRoomByName( String name ) {
		sqldb.execSQL("delete from classrooms where name like '" + name + "'");
	}
	
	public List<ClassRoom> getClassRooms() {
		List<ClassRoom> classRooms = new ArrayList<ClassRoom>();
		Cursor cursor = sqldb.query("classrooms", classRoomColumns, null, null, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()) {
			classRooms.add(classRoomFromCursor(cursor));
			cursor.moveToNext();
		}
		return classRooms;
	}
	
	public ClassRoom getClassRoomByName(String name) {
		List<ClassRoom> classRooms = getClassRooms();
		for(ClassRoom cr : classRooms) {
			if(cr.getName().equalsIgnoreCase(name))
				return cr;
		}
		return null;
	}
	
	public ClassRoom getClassRoomById(Integer id) {
		List<ClassRoom> classRooms = getClassRooms();
		for(ClassRoom cr : classRooms) {
			if(cr.getId().equals(id))
				return cr;
		}
		return null;
	}
	
	private ClassRoom classRoomFromCursor(Cursor cursor) {
		ClassRoom cr = new ClassRoom();
		cr.setId(cursor.getInt(0));
		cr.setName(cursor.getString(1));
		cr.setLat(cursor.getDouble(2));
		cr.setLon(cursor.getDouble(3));
		return cr;
	}
	
	
	
/*
 * class DatabaseOpenHelper
 */
	private class DatabaseOpenHelper extends SQLiteOpenHelper {

		final private static String CREATE_CLASSROOM = "CREATE TABLE classrooms ("
				+ _ID
				+ "  INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_CLASSROOM_NAME
				+ " TEXT,"
				+ COLUMN_CLASSROOM_LAT
				+ " REAL," + COLUMN_CLASSROOM_LON + " REAL)";
		

		final private Context mContext;

		public DatabaseOpenHelper(Context context) {
			super(context, DB_NAME, null, VERSION);
			this.mContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_CLASSROOM);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS classrooms");
			onCreate(db);
		}
		
//		public void deleteClassRoomByName( SQLiteDatabase db, String name ) {
//			db.execSQL("delete from classrooms where name like '" + name + "'");
//		}

		public void deleteDatabase() {
			mContext.deleteDatabase(DB_NAME);
		}
	}

}
