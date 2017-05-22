package mvsrnews.library;


import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
	Context c;
   
	private static Database sinstance; {
		
	}
	private static final int DATABASE_VERSION = 7;
	private static final String DATABASE_NAME = "mvsrnews3";
	private static final String TABLE_LOGIN = "newsnd";
	private static final String SERVER_KEY_ID = "id";
	private static final String UNIQUE_KEY_ID = "uid";
	private static final String KEY_HEADLINES = "headlines";
	private static final String KEY_DESC = "description";
	private static final String KEY_TIME = "addtime";
	private static final String KEY_IMAGE = "imagename";
	private static final String KEY_IMAGEBYTES = "imagebytes";
	private static final String KEY_POSTBY = "postedby";
	private static final String KEY_BRANCH = "branch";
	private static final String NOTIFY_TABLE = "notify";
	
	
	
	public static Database getInstance(Context context) {

	   
	    if (sinstance == null) {
	      sinstance = new Database(context.getApplicationContext());
	    }
	    return sinstance;
	  }
	
	public Database(Context con) {
		super(con ,DATABASE_NAME , null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "( "
				+ UNIQUE_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," 
				+ SERVER_KEY_ID + " INTEGER  ,"
				+ KEY_HEADLINES + " TEXT," 
				+ KEY_DESC + " TEXT,"
                + KEY_TIME + " TEXT," 
                + KEY_POSTBY + " TEXT,"
				+ KEY_IMAGE + " TEXT," 
				+ KEY_BRANCH + " TEXT,"
				+ KEY_IMAGEBYTES + " TEXT )";
		
		db.execSQL(CREATE_LOGIN_TABLE);
String CREATE_NOTIFY="CREATE TABLE "+NOTIFY_TABLE+" ( uid INTEGER PRIMARY KEY,notified INTEGER )";
		
		db.execSQL(CREATE_NOTIFY);
		insert_notifytable(db);
		
		Log.d("database","tables created");

	}

	@Override
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
              
		db.execSQL("DROP table if exists "+TABLE_LOGIN);
		db.execSQL("DROP table if exists notify");
		onCreate(db);
		
	} 

	
	void insert_notifytable(SQLiteDatabase db){
		
		ContentValues cv=new ContentValues();
		cv.put("uid", 100);
		cv.put("notified", 0);
		db.insert(NOTIFY_TABLE, null, cv);
		
	}
	
	public void putnotifiedvalue(int val) {
		SQLiteDatabase sdb=this.getWritableDatabase();
		
		ContentValues cv=new ContentValues();
		cv.put("notified", val);
		
		sdb.update(NOTIFY_TABLE, cv,"uid=100", null);
		
		sdb.close();
	}
	
	public int getnotifiedvalue(){
		int val=0;
		SQLiteDatabase sdb=this.getReadableDatabase();
		
		String query="select * from "+NOTIFY_TABLE+" where uid=100";
		Cursor c=sdb.rawQuery(query, null);
		c.moveToFirst();
		if(c!=null)
		val=c.getInt(1);
		c.close();
		return val;
	}
	
	public void addUser(int id,String headlines, String desc, String time,String imagename,String imagebyte,String postedby,String branch) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SERVER_KEY_ID,id);
		values.put(KEY_HEADLINES, headlines);
		values.put(KEY_DESC, desc);
		values.put(KEY_TIME, time);
		values.put(KEY_IMAGE, imagename);
		values.put(KEY_IMAGEBYTES, imagebyte);
		values.put(KEY_POSTBY, postedby);
		values.put(KEY_BRANCH, branch);
		
		db.insert(TABLE_LOGIN, null, values);

		db.close();
		
		
	}

	public HashMap<String, String> getUserDetails(int server_id) {
		
	
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE id=" + server_id;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);
		
		
		cursor.moveToFirst();
		
		
		try{
	 
		user.put("headlines", cursor.getString(2));
		user.put("desc", cursor.getString(3));
		user.put("time", cursor.getString(4));
		user.put("postby", cursor.getString(5));
		
		user.put("image", cursor.getString(6));
    	user.put("imagebyte",cursor.getString(8));
    	user.put("branch", cursor.getString(7));
    	
    
		cursor.close();
		db.close();
		}catch(CursorIndexOutOfBoundsException e){
		}
		
		
		
		return user;
		
		
	}

	public int getRowCount() {
		
		
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		
		int rowCount = cursor.getCount();
		
	
		cursor.close();
	
		db.close();

		return rowCount;
	}
	
	
	public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
      String query="DELETE FROM "+ TABLE_LOGIN;
        
        db.rawQuery(query, null);
        db.close();
	}

	public int toprow_id() {
		
		int highest_server_id=0;
		
		
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN +" ORDER BY id desc" ;

		SQLiteDatabase db = this.getReadableDatabase();
		

		Cursor cursor = db.rawQuery(selectQuery, null);
		
		cursor.moveToFirst();
		
		highest_server_id=cursor.getInt(1);
		
		
		cursor.close();
		db.close();
		return highest_server_id;
		
	}
	
  public int Server_lastrow_id() {
		
		int top1=0;
		
		
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN ;

		SQLiteDatabase db = this.getReadableDatabase();
		

		Cursor cursor = db.rawQuery(selectQuery, null);
		
		cursor.moveToLast();
		
		top1=cursor.getInt(1);
		
		cursor.close();
		db.close();
		return top1;
		
	}
  public int lastrow_id() {
		
		int least_server_id=0;
		
		
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN +" ORDER BY id desc" ;

		SQLiteDatabase db = this.getReadableDatabase();
		

		Cursor cursor = db.rawQuery(selectQuery, null);
		
		cursor.moveToLast();
		
		least_server_id=cursor.getInt(1);
		
		cursor.close();
		db.close();
		return least_server_id;
		
		
	}
	
	
	public Cursor rowIds(String branch2)
	{
		String selectQuery = "SELECT id FROM " + TABLE_LOGIN +" WHERE branch="+branch2 ;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor=db.rawQuery(selectQuery, null);
		return cursor;
		
	}

}
