package com.example.database_practice_sqllite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        //vibrator.

       // Toast.makeText(context , " alarm bajetese" , Toast.LENGTH_SHORT).show();
        Log.d("MY_APP" , "Alarm Ringing");
        MediaPlayer m = MediaPlayer.create(context , R.raw.siren);
        m.setLooping(true);
        m.start();



    }
}
