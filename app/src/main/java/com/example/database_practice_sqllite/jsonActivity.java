package com.example.database_practice_sqllite;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class jsonActivity extends AsyncTask<Void, Void, Void> {

    String data = "";
    ArrayList<employeeData> arrayList = new ArrayList<employeeData>();

    ProgressDialog progressDialog;

    Context context;
    ListView listView;
    ArrayList<employeeData> list_items;

    recycleradapter recyclerAdapter;
    RecyclerView recyclerView;

    boolean dataSet = false;


    ArrayAdapter<employeeData> arrayAdapter;

    public jsonActivity(Context context, ListView listView, ArrayList<employeeData> list_items, ArrayAdapter<employeeData> arrayAdapter, RecyclerView recyclerView, recycleradapter recyclerAdapter) {
        this.context = context;
        this.listView = listView;
        this.list_items = list_items;
        this.arrayAdapter = arrayAdapter;
        this.recyclerAdapter = recyclerAdapter;
        this.recyclerView = recyclerView;
        SharedPreferences preferences = context.getSharedPreferences("DB_DATABASE", MODE_PRIVATE);
        dataSet = preferences.getBoolean("SET_SQLITE", false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://anontech.info/courses/cse491/employees.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = "";
            while (s != null) {
                s = bufferedReader.readLine();
                data = data + s;
            }

            Log.d("MY_APP" ,"json string array : "+ data);

            JSONArray jsonArray = new JSONArray(data);
            Log.d("MY_APP" , "length : "+ jsonArray.length());
            String latitude = "";
            String longitude = "";
            String location ;
            String name = "";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                 name = jsonObject.getString("name");
                 location = jsonObject.getString("location");

                if(location.equals("null")){
                    latitude = "null";
                    longitude = "null";
                } else {
                    if (!location.equals(JSONObject.NULL)) {
                        // location = jsonObject.getString("location");
                        JSONObject jsonObject1 = new JSONObject(location);
                        latitude = jsonObject1.getString("latitude");
                        longitude = jsonObject1.getString("longitude");
                    }
                }
                //sql entry//
                SQL sql = new SQL(context);
                SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
                long check = sql.insertData(name, latitude , longitude);

                if(check > 0){
                    SharedPreferences.Editor editor = context.getSharedPreferences("DB_DATABASE", MODE_PRIVATE).edit();
                    editor.putBoolean("SET_SQLITE", true);
                    editor.apply();
                }




                Log.d("MY_APP" , name+" "+location+" "+latitude+" "+longitude);

                arrayList.add(new employeeData(name, latitude, longitude));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//        String data ;
//        Log.d("MY_APP" , "size ta ki "+ arrayList.size());
//        for (int i =0 ; i<list_items.size() ; i++){
//            //data = "Name : " + arrayList.get(i).name+"\n"+"Latitude" +arrayList.get(i).latitudeLocation+"\n" +"Longitude "+ arrayList.get(i).longitudeLocation;
//
//            //list_items.add(data);
//
//            //list_items.add(new employeeData(arrayList.get(i).name),)
        //  Log.d("MY_APP" , " ki hoitese oita dekhbo : "+arrayList.get(i).name);
//        }
        //listView.setAdapter(arrayAdapter);

        recyclerAdapter = new recycleradapter(context, arrayList);
        recyclerView.setAdapter(recyclerAdapter);
        progressDialog.dismiss();
    }




}
