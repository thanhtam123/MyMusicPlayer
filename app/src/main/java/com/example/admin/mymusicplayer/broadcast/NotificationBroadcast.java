package com.example.admin.mymusicplayer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import com.example.admin.mymusicplayer.service.MusicService;
import com.example.admin.mymusicplayer.utility.Constants;
import com.example.admin.mymusicplayer.utility.SongManager;
import com.example.admin.mymusicplayer.view.MainActivity;

/**
 * Created by TamTT on 7/19/2018.
 */

public class NotificationBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    if(!Constants.SONG_PAUSED){
                        SongManager.pauseControl(context);
                    }else{
                        SongManager.playControl(context);
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                    break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    Log.e("TAG", "TAG: KEYCODE_MEDIA_NEXT");
                    SongManager.nextControl(context);
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    Log.e("TAG", "TAG: KEYCODE_MEDIA_PREVIOUS");
                    SongManager.previousControl(context);
                    break;
            }
        }
        else{
            if (intent.getAction().equals(Constants.NOTIFY_PLAY)) {
                SongManager.playControl(context);
            } else if (intent.getAction().equals(Constants.NOTIFY_PAUSE)) {
                SongManager.pauseControl(context);
            } else if (intent.getAction().equals(Constants.NOTIFY_NEXT)) {
                SongManager.nextControl(context);
            } else if (intent.getAction().equals(Constants.NOTIFY_DELETE)) {
                Intent i = new Intent(context, MusicService.class);
                context.stopService(i);
            } else if (intent.getAction().equals(Constants.NOTIFY_PREVIOUS)) {
                SongManager.previousControl(context);
            } else if (intent.getAction().equals(Constants.NOTIFY_OPEN)) {
                Intent in = new Intent(context, MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        }
    }
    public String ComponentName() {
        return this.getClass().getName();
    }
}