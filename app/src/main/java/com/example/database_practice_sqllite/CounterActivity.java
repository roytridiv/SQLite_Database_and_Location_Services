package com.example.database_practice_sqllite;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CounterActivity extends AppCompatActivity {


    private TextView t ;
    Button back ;
    private CountDownTimer countDownTimer ;
    private long timeLeftmilisec ;

    private long times;

    private boolean timerunning ;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Vibrator vibrator;

    MediaPlayer m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlertReciever.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        vibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);

        t = findViewById(R.id.start);
        back = findViewById(R.id.goBack);

        LayoutInflater layoutInflater = LayoutInflater.from(CounterActivity.this);
        View promtView = layoutInflater.inflate(R.layout.promt , null );
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(CounterActivity.this);
        alertdialog.setView(promtView);

        final EditText edit = promtView.findViewById(R.id.editText);
        alertdialog.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Log.d("MY_APP" , "ki paitesi  :  "+edit.getText());


                            String  s  = edit.getText().toString();

                        Log.d("MY_APP" , "ki paitesi  :  n"+s+"kokokokok");

                        if(s != ""){
                            Log.d("MY_APP" , "if e dhukse ");

                            int num = Integer.parseInt(s);

                            timeLeftmilisec = num * 1000;

                            Log.d("MY_APP" , ""+timeLeftmilisec);

                            start();
                        }else{
                            Toast.makeText(CounterActivity.this , "Null value found ! Please enter a number" , Toast.LENGTH_SHORT ).show();
                        }



                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertdialog.create();
        alertdialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
                vibrator.cancel();
                m.stop();
                Intent intent = new Intent(CounterActivity.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });





    }

    public void start()
    {

//        countDownTimer = new CountDownTimer(timeLeftmilisec, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                Log.d("MY_APP" , ""+millisUntilFinished);
//                timeLeftmilisec = millisUntilFinished;
//
//                updateTimer();
//
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();

        times = timeLeftmilisec;
        t.setText(String.format("%s ", times/1000));
        final Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(times >= 0){
                                    t.setText(String.format("%s ", times/1000));
                                    times = times - 1000;
                                }else if(times == -1000){
                                    t.setText("TIMES UP !!!");
                                    startAlarm();
                                    times = times - 1000;
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        welcomeThread.start();


    }



    private void startAlarm() {
        Log.d("MY_APP" , "alarm bajbe");
//        alarmManager.set(AlarmManager.RTC_WAKEUP ,2000 , pendingIntent );

        //vibrator.vibrate(10000);
        vibrator.vibrate(10000);
        m = MediaPlayer.create(this , R.raw.siren);
        m.setLooping(true);
        m.start();
    }


    private void cancelAlarm() {
        alarmManager.cancel(pendingIntent);
        //Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        m.stop();
        vibrator.cancel();

        Intent intent = new Intent(CounterActivity.this , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

        super.onBackPressed();
    }
}
