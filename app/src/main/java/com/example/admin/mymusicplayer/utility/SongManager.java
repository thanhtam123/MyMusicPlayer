package com.example.admin.mymusicplayer.utility;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.example.admin.mymusicplayer.R;
import com.example.admin.mymusicplayer.service.MusicService;

/**
 * Created by TamTT on 7/18/2018.
 */

public class SongManager {
    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public static void playControl(Context context) {
        sendMessage(context.getResources().getString(R.string.play));
    }

    public static void pauseControl(Context context) {
        sendMessage(context.getResources().getString(R.string.pause));
    }

    public static void nextControl(Context context) {
        boolean isServiceRunning = isServiceRunning(MusicService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if(Constants.SONG_LIST.size() > 0 ){
            if(Constants.SONG_NUMBER < (Constants.SONG_LIST.size()-1)){
                Constants.SONG_NUMBER++;
                Constants.CURRENT_SONG = Constants.SONG_LIST.get(Constants.SONG_NUMBER);
                Constants.SONG_CHANGE_HANDLER.sendMessage(Constants.SONG_CHANGE_HANDLER.obtainMessage());
            }else{
                Constants.SONG_NUMBER = 0;
                Constants.CURRENT_SONG = Constants.SONG_LIST.get(Constants.SONG_NUMBER);
                Constants.SONG_CHANGE_HANDLER.sendMessage(Constants.SONG_CHANGE_HANDLER.obtainMessage());
            }
        }
        Log.e("TAG",Constants.CURRENT_SONG.getName());
        Constants.SONG_PAUSED = false;
    }

    public static void previousControl(Context context) {
        boolean isServiceRunning = isServiceRunning(MusicService.class.getName(), context);
        if (!isServiceRunning)
            return;
        if(Constants.SONG_LIST.size() > 0 ){
            Log.e("TAG","pre"+Constants.SONG_NUMBER);
            if(Constants.SONG_NUMBER > 0){
                Constants.SONG_NUMBER--;
                Constants.CURRENT_SONG = Constants.SONG_LIST.get(Constants.SONG_NUMBER);
                Constants.SONG_CHANGE_HANDLER.sendMessage(Constants.SONG_CHANGE_HANDLER.obtainMessage());
            }else{
                Constants.SONG_NUMBER = Constants.SONG_LIST.size() - 1;
                Constants.CURRENT_SONG = Constants.SONG_LIST.get(Constants.SONG_NUMBER);
                Constants.SONG_CHANGE_HANDLER.sendMessage(Constants.SONG_CHANGE_HANDLER.obtainMessage());
            }
        }
        Log.e("TAG",Constants.CURRENT_SONG.getName());
        Constants.SONG_PAUSED = false;
    }

    private static void sendMessage(String message) {
        try{
            Constants.PLAY_PAUSE_HANDLER.sendMessage(Constants.PLAY_PAUSE_HANDLER.obtainMessage(0, message));
        }catch(Exception e){}
    }

}
