package com.example.admin.mymusicplayer.model;

import com.example.admin.mymusicplayer.model.entity.SongObject;

import java.util.ArrayList;

/**
 * Created by TamTT on 7/17/2018.
 */

public interface ILoadListSongs {
    void loadListSongsSuccess(ArrayList<SongObject> arrayList);
    void loadListSongsFail();
}
