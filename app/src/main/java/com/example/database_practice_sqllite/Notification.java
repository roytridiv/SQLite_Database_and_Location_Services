package com.example.database_practice_sqllite;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notification {

    private  String channel_id = "my_Notification";
    private  int notification_id = 001;

    private String title , text ;

    Context context;

    public Notification(String title , String text , Context context) {

        this.context = context ;
        this.title = title;
        this.text = text;
    }

    public void display(){

        notiChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , channel_id);
        builder.setSmallIcon(R.drawable.ic_sms_black_icon);
        builder.setContentTitle(title);
        builder.setContentText(text);
        //builder.setSound();
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notification_id,builder.build());
    }

    public  void notiChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence charSequence = "My NOTIFICATION";
            String description = "this is a personal noti";
            int impotance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(channel_id , charSequence , impotance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

}
