package com.rohan.passwordgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;


public class Mydb extends SQLiteOpenHelper {
    private static final String DATABASENAME = "Password.db";
    private static final String PASSWORDTABLE = "Password_table";
    private static final String NAME = "website_name";
    private static final String PASSWORD = "password";
    private static final String CATAGORY = "catagory";
    private static final String KEYID = "id";
    private static final int VERSION = 1;
    public Mydb(@Nullable Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+PASSWORDTABLE+
                "(" +KEYID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ NAME + " TEXT, " + PASSWORD +" TEXT, "+CATAGORY +"TEXT " +")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+PASSWORDTABLE);
        onCreate(db);

    }

    public void addPass(String name,String pass){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(NAME,name);
        values.put(PASSWORD,pass);
        database.insert(PASSWORDTABLE,null,values);
    }
    public ArrayList<passwordManager> fetch(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor=null;
        ArrayList<passwordManager> arrcon = new ArrayList<>();
        try {

            cursor = database.rawQuery("SELECT * FROM " + PASSWORDTABLE, null);


            while (cursor.moveToNext()) {
                passwordManager passwordManager = new passwordManager();
                passwordManager.id = cursor.getInt(cursor.getColumnIndexOrThrow(KEYID));
                passwordManager.name = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
                passwordManager.Pass = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD));
                arrcon.add(passwordManager);
            }
        } finally {

            if (cursor != null) {
                cursor.close();
            }
        }
        return arrcon;

    }

    public void deletePass(int id){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(PASSWORDTABLE,KEYID +" =?",new String[]{String.valueOf(id)});
        database.close();
    }

}
