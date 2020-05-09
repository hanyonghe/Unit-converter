package com.hanyong.unitconvert2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecordDB {
//    database constants
    public static final String DB_NAME = "records.db";
    public static final int DB_VERSION = 1;

//      table constants--table and column name
    public static final String RECORD_TABLE = "record";

    public static final String RECORD_ID = "_id";
    public static final int  RECORD_ID_COL = 0;
    public static final String FROM_AMOUNT = "from_amount";
    public static final int FROM_AMOUNT_COL = 1;
    public static final String TO_AMOUNT = "to_amount";
    public static final int TO_AMOUNT_COL = 2;
    public static final String FROM_UNIT = "from_unit";
    public static final int FROM_UNIT_COL = 3;
    public static final String TO_UNIT = "to_unit";
    public static final int TO_UNIT_COL = 4;

    //CREATE AND DROP TABLE STATEMENTS
    public static final String CREATE_TABLE =
            "CREATE TABLE " + RECORD_TABLE + " ( "+
            RECORD_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FROM_AMOUNT     + " DOUBLE NOT NULL, "+
            TO_AMOUNT       + " DOUBLE NOT NULL, "+
            FROM_UNIT       + " VARCHAR(20) NOT NULL, "+
            TO_UNIT         + " VARCHAR(20) NOT NULL);";
    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + RECORD_TABLE;



    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            create table
            db.execSQL(CREATE_TABLE);
//            insert one row
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RecordDB.DROP_TABLE);
        onCreate(db);
        }
    }
//    create database and database helper objects
    private DBHelper dbHelper;
    private  SQLiteDatabase db;

//    constructor
    public RecordDB(Context context) {dbHelper = new DBHelper(context,DB_NAME,null,DB_VERSION);}
//      private methods
    private void openReadableDB(){db=dbHelper.getReadableDatabase();}
    private void openWritableDB(){db=dbHelper.getWritableDatabase();}
    private void closeDB(){
        if(db != null)
            db.close();
    }

    //get records from the datebase
    public ArrayList<Record> getRecord(){
        this.openReadableDB();
      Cursor cursor = db.query(RECORD_TABLE, null, null, null, null, null, RECORD_ID+" DESC","10");
        ArrayList<Record> records = new ArrayList<Record>();
        while(cursor.moveToNext()){
            records.add(getRecordFromCursor(cursor));
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return records;
    }

    private static Record getRecordFromCursor(Cursor cursor) {
        if(cursor == null || cursor.getCount() == 0){
            return null;
        } else {
            try {
                Record record = new Record(
                        cursor.getLong(RECORD_ID_COL),
                        cursor.getLong(FROM_AMOUNT_COL),
                        cursor.getLong(TO_AMOUNT_COL),
                        cursor.getString(FROM_UNIT_COL),
                        cursor.getString(TO_UNIT_COL));
                 return record;
            }
            catch (Exception e){
                Log.d("Exception",e+"");
                return null;
            }
        }
    }

    public long insertRecord(Record record){
        ContentValues cv = new ContentValues();
        cv.put(FROM_AMOUNT,record.getFrom_amount());
        cv.put(TO_AMOUNT,record.getTo_amount());
        cv.put(FROM_UNIT,record.getFrom_unit());
        cv.put(TO_UNIT,record.getTo_unit());
        this.openWritableDB();
        long rowID = db.insert(RECORD_TABLE,null,cv);
        this.closeDB();
        return rowID;
    }
    public void deleteRecords(){
        this.openWritableDB();
        db.execSQL("DELETE FROM " + RECORD_TABLE + " WHERE " + RECORD_ID + " != -1" );
        this.closeDB();
    }

}
