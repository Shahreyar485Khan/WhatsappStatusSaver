package com.all.document.reader.pdf.doc.ppt.xls.instagramimagedownloader.DBConnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.LocaleData;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "databaseee.db";
    public static final String TABLE_NAME = "chat_tablesss";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TEXT";
    public static final String COL_4 = "TIME";
    public static final String COL_5 = "CHART";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("etag","dbCreated");
        db.execSQL("create table " + TABLE_NAME +" ( _id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TEXT TEXT,TIME INTEGER,CHART TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String text,String time) {

        char str = name.charAt(0);

        String newStr = String.valueOf(str);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,text);
        contentValues.put(COL_4,time);
        contentValues.put(COL_5,newStr);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }


    public boolean isDuplicate(String text, String title) {
        Cursor cur = searchByName(title);

        boolean isTextPresent = false;
        while(cur.moveToNext()){
            String dbText=cur.getString(cur.getColumnIndex(DatabaseHelper.COL_3));
            if(dbText != null) {
                if (dbText.equals(text)) {
                    isTextPresent = true;
                    break;
                }
            }
        }

        return  isTextPresent;

    }


    public Cursor getDistinctRecord(){

        String[] columns = new String[] { DatabaseHelper.COL_1, DatabaseHelper.COL_2, DatabaseHelper.COL_3,DatabaseHelper.COL_4,DatabaseHelper.COL_5};
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(true, DatabaseHelper.TABLE_NAME,columns, null, null, DatabaseHelper.COL_2, null, null, null);
     //   Cursor cursor = db.rawQuery("SELECT DISTINCT NAME FROM my_database.db", null);

//        Cursor cursor = db.rawQuery("select distinct "+DatabaseHelper.COL_2 +" from "+TABLE_NAME,null);

        if (cursor != null) {
            cursor.moveToFirst();
//            Log.d("tagg", ""+cursor.getString(1));

        }
        return cursor;
    }

    public Cursor searchByName(String name ){

        String[] columns = new String[] { DatabaseHelper.COL_1, DatabaseHelper.COL_2, DatabaseHelper.COL_3 ,DatabaseHelper.COL_4,DatabaseHelper.COL_5};
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME,
                columns,
                DatabaseHelper.COL_2+" LIKE '"+name+"%'", null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }


 /*   public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }*/

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_NAME, null, null);
    }

}
