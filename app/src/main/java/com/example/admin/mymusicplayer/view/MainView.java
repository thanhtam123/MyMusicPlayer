package com.example.admin.mymusicplayer.view;

import com.example.admin.mymusicplayer.model.entity.Song;

import java.util.ArrayList;

/**
 * Created by TamTT on 7/17/2018.
 */

public interface MainView {
    void displaySongs(ArrayList<Song> arrayList);
    void controlPlayMusic(Song songObject);
}
