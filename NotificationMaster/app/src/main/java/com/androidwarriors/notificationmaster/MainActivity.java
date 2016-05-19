package com.androidwarriors.notificationmaster;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_not_simple, btn_not_expand, btn_not_btn;
    private  NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager=(NotificationManager) this.getApplicationContext().getSystemService(this.NOTIFICATION_SERVICE);
        btn_not_simple= (Button) findViewById(R.id.btn_not_simple);
        btn_not_expand= (Button) findViewById(R.id.btn_not_expand);
        btn_not_btn= (Button) findViewById(R.id.btn_not_btn);

        btn_not_simple.setOnClickListener(this);
        btn_not_expand.setOnClickListener(this);
        btn_not_btn.setOnClickListener(this);

    }


    void simpleNotification(){

        Intent resultIntent = new Intent(this, MainActivity.class);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("I'm a simple notification") // main title of the notification
                .setContentText("I'm the text of the simple notification") // notification text
                .setContentIntent(resultPending); // notification intent

        // mId allows you to update the notification later on.
        mNotificationManager.notify(10, mBuilder.build());
    }

    void expandedNotification(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Building the expandable content
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            String lorem = this.getResources().getString(R.string.long_lorem);
            String [] content = lorem.split("\\.");

            inboxStyle.setBigContentTitle("This is a big title");
            for (String line : content) {
                inboxStyle.addLine(line);
            }

            // Building the notification
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Expandable notification") // title of notification
                    .setContentText("This is an example of an expandable notification") // text inside the notification
                    .setStyle(inboxStyle); // adds the expandable content to the notification

            mNotificationManager.notify(11, nBuilder.build());

        } else {
            Toast.makeText(this, "Can't show", Toast.LENGTH_LONG).show();
        }
    }



    void notificationWithButton(){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // Prepare intent which is triggered if the  notification button is pressed
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

            // Building the notifcation
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Button notification") // notification title
                    .setContentText("Expand to show the buttons...") // content text
                    .setTicker("Showing button notification") // status bar message
                    .addAction(R.drawable.accept, "Accept", pIntent) // accept notification button
                    .addAction(R.drawable.cancel, "Cancel", pIntent); // cancel notification button

            mNotificationManager.notify(1001, nBuilder.build());

        } else {
            Toast.makeText(this, "You need a higher version", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.btn_not_btn:
        notificationWithButton();
        break;
    case R.id.btn_not_expand:
        expandedNotification();
        break;
    case R.id.btn_not_simple:
        simpleNotification();
        break;

}
    }
}
