package com.example.database_practice_sqllite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {





    Button back ;

    ListView list2;

    ArrayList<String> list_items ;

    ArrayAdapter<String> arrayAdapter;


    private Context context;
    private SQLiteDatabase database;
    private SQL sql;


String id , name , loc1 , loc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        back = findViewById(R.id.but4);

          list2 = findViewById(R.id.list2);
          list_items = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item , list_items);

        list2.setAdapter(arrayAdapter);
//
//        SQL db = new SQL(this);
//        //ArrayList<HashMap<String, String>> userList = db.GetUsers();
////        ListAdapter adapter = new SimpleAdapter(Main2Activity.this, userList, R.layout.list_row,new String[]{"name","designation","location"}, new int[]{R.id.name, R.id.designation, R.id.location});
////        list2.setAdapter(adapter);
////
////        String s = userList.get(1).;

         SQL sql =  new SQL(this) ;
         Cursor cursor = sql.fetch();
         if (cursor.getCount() == 0){

         }else {
             while (cursor.moveToNext()){
                 id = cursor.getString(0);
                 name = cursor.getString(1);
                 loc1 = cursor.getString(2);
                 loc2 = cursor.getString(3);
                 //loc2 = cursor.getColumnName(2);
                 Log.d("MY_APP" , "eita sql er daabase hbe :p "+name +", "+loc1+", "+loc2);

                 list_items.add("Name : "+name+"\n Location\n Latitude: "+loc1+" Longitude : "+loc2);
             }
         }






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });






    }
}
