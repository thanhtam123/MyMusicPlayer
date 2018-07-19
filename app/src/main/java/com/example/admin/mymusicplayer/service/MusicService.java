package com.example.admin.mymusicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.admin.mymusicplayer.utility.Constants;
import com.example.admin.mymusicplayer.R;
import com.example.admin.mymusicplayer.model.entity.SongObject;
import com.example.admin.mymusicplayer.view.MainActivity;

import java.io.IOException;

/**
 * Created by TamTT on 7/18/2018.
 */

public class MusicService extends Service {

    public static MediaPlayer player;
    private AudioManager audioManager;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SongObject songObject = intent.getParcelableExtra(Constants.KEY_PASS_SONG_PLAYED);
        playSong(songObject);
        return START_STICKY;
    }

    public void playSong(SongObject songObject) {
        player.reset();
        try {
            player.setDataSource(songObject.getPath());
            player.prepare();
            player.start();
            createNotification(songObject);
            //Constants.TOTAL_DURATION = player.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNotification(SongObject songObject) {
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.layout_notification);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.KEY_PASS_SONG_PLAYED, songObject);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification customNotification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_notification)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(Constants.NOTIFICATION_ID, customNotification);
    }

}
