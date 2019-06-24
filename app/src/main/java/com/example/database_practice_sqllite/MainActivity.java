package com.example.database_practice_sqllite;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button fetch, insert, check;

    ListView listView ;


    ArrayList<employeeData> list_items , sqlList;



    ArrayAdapter<employeeData> arrayAdapter , newFetch;
    jsonActivity json;

    String data = "";

//    public MainActivity(Context context){
//
//    }

    //recycler view things



    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private recycleradapter recycleradapter , newAdapter;
    private String id , name , loc1 , loc2;


    boolean dataSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(internetOn()){
            SharedPreferences preferences = getSharedPreferences("DB_DATABASE", MODE_PRIVATE);
            dataSet = preferences.getBoolean("SET_SQLITE", false);

            recyclerView = findViewById(R.id.recyclerView);
            linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);


            fetch = findViewById(R.id.but1);

            check = findViewById(R.id.but3);

            list_items = new ArrayList<>();

            if(dataSet){
                fetch.setVisibility(View.GONE);
                check.setVisibility(View.GONE);

                sqlList = new ArrayList<>();

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

                        sqlList.add(new employeeData(name , loc1 , loc2));
                        newAdapter = new recycleradapter(MainActivity.this, sqlList);
                        recyclerView.setAdapter(newAdapter);
                    }
                }
            }

            //arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, list_items);

            //listView.setAdapter(arrayAdapter);

            // Main2Activity m = new Main2Activity(MainActivity.this);


            fetch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dataSet){
                        Toast.makeText(MainActivity.this, "Fetching from SQLITE", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Fetching from JSON", Toast.LENGTH_SHORT).show();
                        json = new jsonActivity(MainActivity.this, listView, list_items, arrayAdapter, recyclerView, recycleradapter);
                        if (!json.list_items.isEmpty()) {
                            insert();
                        } else {
                            json.list_items.clear();
                        }
                        json.execute();
                    }

                }
            });


            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

        }else{

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Warning !")
                    .setMessage("Your internet connection is off , Please turn on mobile data or wifi to proceed.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            internetOn();
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            alertDialog.setCancelable(false);
            alertDialog.show();

        }

//        SharedPreferences preferences = getSharedPreferences("DB_DATABASE", MODE_PRIVATE);
//        dataSet = preferences.getBoolean("SET_SQLITE", false);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//
//        fetch = findViewById(R.id.but1);
//
//        check = findViewById(R.id.but3);
//
//        list_items = new ArrayList<>();
//
//        if(dataSet){
//            fetch.setVisibility(View.GONE);
//            check.setVisibility(View.GONE);
//
//            sqlList = new ArrayList<>();
//
//            SQL sql =  new SQL(this) ;
//            Cursor cursor = sql.fetch();
//            if (cursor.getCount() == 0){
//
//            }else {
//                while (cursor.moveToNext()){
//                    id = cursor.getString(0);
//                    name = cursor.getString(1);
//                    loc1 = cursor.getString(2);
//                    loc2 = cursor.getString(3);
//                    //loc2 = cursor.getColumnName(2);
//                    Log.d("MY_APP" , "eita sql er daabase hbe :p "+name +", "+loc1+", "+loc2);
//
//                    sqlList.add(new employeeData(name , loc1 , loc2));
//                    newAdapter = new recycleradapter(MainActivity.this, sqlList);
//                    recyclerView.setAdapter(newAdapter);
//                }
//            }
//        }
//
//        //arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, list_items);
//
//        //listView.setAdapter(arrayAdapter);
//
//       // Main2Activity m = new Main2Activity(MainActivity.this);
//
//
//        fetch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(dataSet){
//                    Toast.makeText(MainActivity.this, "Fetching from SQLITE", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Fetching from JSON", Toast.LENGTH_SHORT).show();
//                    json = new jsonActivity(MainActivity.this, listView, list_items, arrayAdapter, recyclerView, recycleradapter);
//                    if (!json.list_items.isEmpty()) {
//                        insert();
//                    } else {
//                        json.list_items.clear();
//                    }
//                    json.execute();
//                }
//
//            }
//        });
//
//
//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//        });


    }

    public void  insert(){

        if (json.arrayList.isEmpty()) {
            Toast.makeText(MainActivity.this, "First fetch data then store it", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Insertion Done", Toast.LENGTH_SHORT).show();

            SQL sql = new SQL(MainActivity.this);
            SQLiteDatabase sqLiteDatabase = sql.getWritableDatabase();
            for (int i = 0; i < json.arrayList.size(); i++) {
                sql.insertData(json.arrayList.get(i).name,
                        json.arrayList.get(i).latitudeLocation,
                        json.arrayList.get(i).longitudeLocation);
                Log.d("dbpera", "insert hoye gelo" + json.arrayList.get(i));
                SharedPreferences.Editor editor = getSharedPreferences("DB_DATABASE", MODE_PRIVATE).edit();
                editor.putBoolean("SET_SQLITE", true);
                editor.apply();
            }

        }
    }

    private boolean internetOn(){
        boolean have_wifi = false ;
        boolean have_data = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI")){
                if(info.isConnected()){
                    have_wifi=true;
                }

            }
            if (info.getTypeName().equalsIgnoreCase("MOBILE")){

                if (info.isConnected()){
                    have_data = true;
                }


            }
        }
        return have_wifi || have_data ;
    }


}
