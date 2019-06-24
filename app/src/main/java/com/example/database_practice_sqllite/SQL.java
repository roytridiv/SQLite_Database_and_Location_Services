package com.example.database_practice_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;


public class SQL extends SQLiteOpenHelper {


    public static final String DATABASE_NAME="employeeDB";
    public static final String TABLE_NAME="employeeTable";


    public SQL(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL("create table "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255),loc1 VARCHAR(255),loc2 VARCHAR(255))");
        }catch (Exception e){
            Log.d("dbPera",e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL("drop table if exists employeeDB");
            onCreate(db);
        } catch (Exception e){
            Log.d("dbPera",e.toString());
        }


    }

    public long insertData(String name,String loc1,String loc2){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("loc1",loc1);
        contentValues.put("loc2",loc2);
        long insertid =sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return insertid;

    }

    public Cursor fetch(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME ,null);
        return cursor;


    }

    public int  updateValue(String loc1 , String loc2 , String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("loc1",loc1);
        cv.put("loc2",loc2);

        sqLiteDatabase.update(TABLE_NAME , cv , "name='"+id + "'", null );
       return 1 ;

    }

//    public boolean CheckIsDataAlreadyInDBorNot( String dbfield , String fieldValue) {
//        SQLiteDatabase sqldb = this.getReadableDatabase();
//        String Query = "Select * from " + TABLE_NAME + " where " + dbfield + " = " + fieldValue;
//        Cursor cursor = sqldb.rawQuery(Query, null);
//        if(cursor.getCount() <= 0){
//            cursor.close();
//            return false;
//        }
//        cursor.close();
//        return true;
//    }

//
//    public static final String databaseName = "EmployeeDatabase";
//    public static final String table = "Employeetable";
////    private static final String KEY_NAME = "Name";
////    private static final String KEY_LOC1 = "LatitudeLocation";
////    private static final String KEY_LOC2 = "LongitudeLocation";
//
//    public SQL(Context context) {
//        super(context , databaseName , null , 1);
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        try{
//            db.execSQL("CREATE TABLE EmployeeDatabase(id INTEGER PRIMARY KEY AUTOINCREMENT , Name TEXT(255))");
//
//            Log.d("MY_APP_DEBUG" , "create hoise");
//
//        }catch (Exception e){
//            Log.d("MY_APP_DEBUG" , "create hoinai");
//        }
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//        db.execSQL("DROP TABLE IF EXISTS employeeDatabase");
//        onCreate(db);
//
//    }
//    public boolean insertdata(String name, String latitudeLocation , String longitudeLocation){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("Name" ,name);
//        //contentValues.put("loc1",latitudeLocation);
//       // contentValues.put("loc2",longitudeLocation);
//
//        long result = db.insert(databaseName , null , contentValues);
//
//        if (result== -1){
//           return false ;
//        }
//        else {
//            return true ;
//
//        }
//
//    }
//
//    // Get User Details
//    public ArrayList<HashMap<String, String>> GetUsers(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
//        String query = "SELECT name, location, designation FROM "+ table;
//        Cursor cursor = db.rawQuery(query,null);
//        while (cursor.moveToNext()){
//            HashMap<String,String> user = new HashMap<>();
//            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME )));
//            user.put("latitudeLocation",cursor.getString(cursor.getColumnIndex(KEY_LOC1)));
//            user.put("longitudeLocation",cursor.getString(cursor.getColumnIndex(KEY_LOC2)));
//            userList.add(user);
//        }
//        return  userList;
//    }
//
//
//    // Get User Details based on userid
////    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid){
////        SQLiteDatabase db = this.getWritableDatabase();
////        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
////        String query = "SELECT name, location, designation FROM "+ table;
////        Cursor cursor = db.query(table, new String[]{KEY_NAME, KEY_LOC1, KEY_LOC2}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
////        if (cursor.moveToNext()){
////            HashMap<String,String> user = new HashMap<>();
////            user.put("name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));
////            user.put("designation",cursor.getString(cursor.getColumnIndex(KEY_DESG)));
////            user.put("location",cursor.getString(cursor.getColumnIndex(KEY_LOC)));
////            userList.add(user);
////        }
////        return  userList;
////    }
//
//
//    // Delete User Details
////    public void DeleteUser(int userid){
////        SQLiteDatabase db = this.getWritableDatabase();
////        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
////        db.close();
////    }
//
//    public Cursor getifo(SQL sql){
//
//        SQLiteDatabase db = sql.getReadableDatabase();
//        String[] colomns = {KEY_NAME,KEY_LOC1,KEY_LOC2};
//
//        Cursor cursor = db.query(table , colomns ,
//                null , null ,
//                null , null ,
//                null);
//        return cursor;
//
//
//    }



}
