package com.example.admin.mymusicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.example.admin.mymusicplayer.R;
import com.example.admin.mymusicplayer.model.entity.Song;
import com.example.admin.mymusicplayer.utility.Constants;
import com.example.admin.mymusicplayer.utility.SongManager;
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

        if(intent != null){
            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
                createNotification(Constants.CURRENT_SONG);
                playSong(Constants.CURRENT_SONG);
            }else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
                SongManager.previousControl(getApplicationContext());
            } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
                player.start();
            } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
                SongManager.nextControl(getApplicationContext());
            } else if (intent.getAction().equals(
                    Constants.ACTION.STOPFOREGROUND_ACTION)) {
                stopForeground(true);
                stopSelf(startId);
            }
        }
        Constants.SONG_CHANGE_HANDLER = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                createNotification(Constants.CURRENT_SONG);
                try{
                    playSong(Constants.CURRENT_SONG);
                    MainActivity.changeButton(Constants.CURRENT_SONG);
                }catch(Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });

        Constants.PLAY_PAUSE_HANDLER = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String message = (String)msg.obj;
                if(player == null)
                    return false;
                if(message.equalsIgnoreCase(getResources().getString(R.string.play))){
                    Constants.SONG_PAUSED = false;
                    player.start();
                }else if(message.equalsIgnoreCase(getResources().getString(R.string.pause))){
                    Constants.SONG_PAUSED = true;
                    player.pause();
                }
                createNotification(Constants.CURRENT_SONG);
                MainActivity.changeButton(Constants.CURRENT_SONG);
                return false;
            }
        });

        return START_STICKY;
    }

    public void playSong(Song songObject) {
        player.reset();
        try {
            player.setDataSource(songObject.getPath());
            player.prepare();
            player.start();
            createNotification(songObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createNotification(Song songObject) {
        RemoteViews simpleContentView = new RemoteViews(getApplicationContext().getPackageName(),R.layout.layout_notification);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(songObject.getName()).build();

        setListeners(simpleContentView);

        notification.contentView = simpleContentView;
        if(Constants.SONG_PAUSED){
            notification.contentView.setViewVisibility(R.id.button_pause_noti, View.GONE);
            notification.contentView.setViewVisibility(R.id.button_play_noti, View.VISIBLE);
        }else{
            notification.contentView.setViewVisibility(R.id.button_pause_noti, View.VISIBLE);
            notification.contentView.setViewVisibility(R.id.button_play_noti, View.GONE);
        }
        notification.contentView.setTextViewText(R.id.text_song_name_noti, songObject.getName());
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        startForeground(Constants.NOTIFICATION_ID, notification);
    }

    private void setListeners(RemoteViews view) {
        Intent previous = new Intent(Constants.NOTIFY_PREVIOUS);
        Intent delete = new Intent(Constants.NOTIFY_DELETE);
        Intent pause = new Intent(Constants.NOTIFY_PAUSE);
        Intent next = new Intent(Constants.NOTIFY_NEXT);
        Intent play = new Intent(Constants.NOTIFY_PLAY);
        Intent open = new Intent(Constants.NOTIFY_OPEN);
        PendingIntent oPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, open, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.statusbar, oPendingIntent);

        PendingIntent pPrevious = PendingIntent.getBroadcast(getApplicationContext(), 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_pre_noti, pPrevious);

        PendingIntent pDelete = PendingIntent.getBroadcast(getApplicationContext(), 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_delete_noti, pDelete);

        PendingIntent pPause = PendingIntent.getBroadcast(getApplicationContext(), 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_pause_noti, pPause);

        PendingIntent pNext = PendingIntent.getBroadcast(getApplicationContext(), 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_next_noti, pNext);

        PendingIntent pPlay = PendingIntent.getBroadcast(getApplicationContext(), 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.button_play_noti, pPlay);
    }

    @Override
    public void onDestroy() {
        if(player!= null){
            player.stop();
            player = null;
        }
        System.exit(0);
        super.onDestroy();
    }
}
