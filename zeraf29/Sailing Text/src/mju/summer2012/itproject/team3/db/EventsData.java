package mju.summer2012.itproject.team3.db;

import static android.provider.BaseColumns._ID;
import static mju.summer2012.itproject.team3.db.Constants.*;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsData extends SQLiteOpenHelper{
	private static final String DATABASE_NAME="sailingtext.db";
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase myDb;
	
	/**Create a helper object for the Events database */
	public EventsData(Context ctx){
		super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
		 myDb = getWritableDatabase();


	}
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+MAP_ID+" INTEGER NOT NULL, "
		+P_TIME+" INTEGER NOT NULL, "+SCORE+" INTEGER NOT NULL, "+C_TIME+" DATETIME DEFAULT CURRENT_TIMESTAMP);");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);
	}
}
