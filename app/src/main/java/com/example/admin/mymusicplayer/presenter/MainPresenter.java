package com.example.admin.mymusicplayer.presenter;

import com.example.admin.mymusicplayer.model.ILoadListSongs;
import com.example.admin.mymusicplayer.model.LoadListSongs;
import com.example.admin.mymusicplayer.model.entity.SongObject;
import com.example.admin.mymusicplayer.view.MainView;

import java.util.ArrayList;

/**
 * Created by TamTT on 7/17/2018.
 */

public class MainPresenter implements ILoadListSongs{

    private MainView mainView;
    private LoadListSongs loadListSongs;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }

    /*public void loadListSongs(Context context){
        loadListSongs = new LoadListSongs(this);
        loadListSongs.loadListSongFromDevice(context);
    }*/
    @Override
    public void loadListSongsSuccess(ArrayList<SongObject> arrayList) {
        mainView.displaySongs(arrayList);
    }

    @Override
    public void loadListSongsFail() {
    }
}
