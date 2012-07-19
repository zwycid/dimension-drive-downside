package mju.summer2012.itproject.team.db;

import static android.provider.BaseColumns._ID;
import static mju.summer2012.itproject.team.db.Constants.*;
import mju.summer2012.itproject.team3.lk.sailing.text.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class TestDBActivity extends Activity {
	private static String[] FROM = {_ID,TIME,TITLE,};
	private static String ORDER_BY = TIME+" DESC";
	
	private EventsData events;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rankingrelaticelayout);
        events = new EventsData(this);
        try{
        	addEvent("Hello, Android!");
        	Cursor cursor = getEvents();
        	showEvents(cursor);
        }finally{
        	events.close();
        }
    }

    private void addEvent(String string){
    	SQLiteDatabase db = events.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(TIME, System.currentTimeMillis());
    	values.put(SCORE,120);
    	db.insertOrThrow(TABLE_NAME, null, values);
    }
    
	private Cursor getEvents(){
    	SQLiteDatabase db = events.getReadableDatabase();
    	Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, ORDER_BY);
    	startManagingCursor(cursor);
    	return cursor;
    }
    
    private void showEvents(Cursor cursor){
    	StringBuilder builder = new StringBuilder(
    			"Saved events:\n");
    	while(cursor.moveToNext()){
    		long id=cursor.getLong(0);
    		long time = cursor.getLong(1);
    		long score = cursor.getLong(2);
    		builder.append(id).append(": ");
    		builder.append(time).append(": ");
    		builder.append(score).append("\n");
    	}
    	TextView text = (TextView) findViewById(R.id.text);
    	text.setText(builder);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_test_db, menu);
        return true;
    }

    
}
