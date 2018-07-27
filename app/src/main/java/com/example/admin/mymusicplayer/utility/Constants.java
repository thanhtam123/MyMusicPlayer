package com.example.admin.mymusicplayer.utility;

import android.os.Handler;

import com.example.admin.mymusicplayer.model.entity.Song;

import java.util.ArrayList;

/**
 * Created by TamTT on 7/18/2018.
 */

public class Constants {
    public static final String KEY_PASS_SONG_PLAYED = "com.example.admin.mymusicplayer.key_pass_song";
    public static ArrayList<Song> SONG_LIST = new ArrayList<>();

    public static int SONG_NUMBER = 0;
    public static Song CURRENT_SONG = null;
    public static Handler SONG_CHANGE_HANDLER;
    public static Handler PLAY_PAUSE_HANDLER;

    public static final int NOTIFICATION_ID = 101;
    public static final String NOTIFY_PREVIOUS = "com.example.admin.mymusicplayer.previous";
    public static final String NOTIFY_DELETE =   "com.example.admin.mymusicplayer.delete";
    public static final String NOTIFY_PAUSE =    "com.example.admin.mymusicplayer.pause";
    public static final String NOTIFY_PLAY =     "com.example.admin.mymusicplayer.play";
    public static final String NOTIFY_NEXT =     "com.example.admin.mymusicplayer.next";
    public static final String NOTIFY_OPEN = "com.example.admin.mymusicplayer.open";
    public static boolean SONG_PAUSED = false;
    public interface ACTION {
        String PREV_ACTION = "com.example.admin.mymusicplayer.previous";
        String PLAY_ACTION = "com.example.admin.mymusicplayer.play";
        String NEXT_ACTION = "com.example.admin.mymusicplayer.next";
        String STARTFOREGROUND_ACTION = "com.example.admin.mymusicplayer.start";
        String STOPFOREGROUND_ACTION  = "com.example.admin.mymusicplayer.cdelete";
    }
}
