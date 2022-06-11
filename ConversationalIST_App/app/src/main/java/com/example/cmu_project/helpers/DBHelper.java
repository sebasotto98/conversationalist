package com.example.cmu_project.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ConversationalIST.db";
    public static final String MESSAGES_TABLE_NAME = "messages";
    public static final String MESSAGES_COLUMN_ID = "id";
    public static final String MESSAGES_COLUMN_DATA = "data";
    public static final String MESSAGES_COLUMN_USERNAME = "username";
    public static final String MESSAGES_COLUMN_TIMESTAMP = "timestamp";
    public static final String MESSAGES_COLUMN_TYPE = "type"; //needs to be parsed to int
    public static final String MESSAGES_COLUMN_CHATROOM = "chatroom";
    public static final String MESSAGES_COLUMN_POSITION = "position";

    //change this and onUpgrade will be called
    private static final int VERSION = 48;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper", "Creating new database.");
        db.execSQL(
                "create table messages " +
                "(id integer primary key, data text,username text,timestamp text," +
                                            " type text, chatroom text, position integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS messages");
        onCreate(db);
    }

    public boolean insertMessage (String data, String username, String timestamp, String type, String chatroom, int position) {
        //debug
        Log.d("DBHelper", "data = " + data);
        Log.d("DBHelper", "username = " + username);
        Log.d("DBHelper", "timestamp = " + timestamp);
        Log.d("DBHelper", "type = " + type);
        Log.d("DBHelper", "chatroom = " + chatroom);
        Log.d("DBHelper", "position = " + position);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGES_COLUMN_DATA, data);
        contentValues.put(MESSAGES_COLUMN_USERNAME, username);
        contentValues.put(MESSAGES_COLUMN_TIMESTAMP, timestamp);
        contentValues.put(MESSAGES_COLUMN_TYPE, type);
        contentValues.put(MESSAGES_COLUMN_CHATROOM, chatroom);
        contentValues.put(MESSAGES_COLUMN_POSITION, position);
        db.insert(MESSAGES_TABLE_NAME, null, contentValues);

        return true;
    }

    public Cursor getAllChatroomMessages(String chatroom){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] args = {chatroom};

        String sortOrder = MESSAGES_COLUMN_POSITION + " ASC";
        String selection = MESSAGES_COLUMN_CHATROOM + " = ?";

        Cursor cs = db.query(
                MESSAGES_TABLE_NAME,        //The table to query
                null,               //The array of columns to return (pass null to get all)
                selection,   //The columns for the WHERE clause
                args,                       // The values for the WHERE clause
                null,               // don't group the rows
                null,                 // don't filter by row groups
                sortOrder                 // The sort order
        );
        return cs;
    }
}
