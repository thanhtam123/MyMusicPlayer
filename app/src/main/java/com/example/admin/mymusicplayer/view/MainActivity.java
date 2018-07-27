package com.example.admin.mymusicplayer.view;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.mymusicplayer.R;
import com.example.admin.mymusicplayer.model.entity.Song;
import com.example.admin.mymusicplayer.service.MusicService;
import com.example.admin.mymusicplayer.utility.Constants;
import com.example.admin.mymusicplayer.utility.SongManager;
import com.example.admin.mymusicplayer.view.adapter.ListSongAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, OnRecyclerViewItemClickListener, View.OnClickListener{


    private RecyclerView recyclerViewListSong;
    private LinearLayoutManager layout;
    private ListSongAdapter adapter;
    static TextView text_song_name;
    static ImageButton btnPlay, btnPre, btnNext, btnPause;
    private String[] permissionList = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WAKE_LOCK};
    private final int MY_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionList, MY_PERMISSION_REQUEST_CODE);
        } else {
            setContentView(R.layout.activity_main);
            SongViewModel model = ViewModelProviders.of(this).get(SongViewModel.class);
            model.loadListSongs().observe(this, new Observer<List<Song>>() {
                @Override
                public void onChanged(@Nullable List<Song> songObjects) {
                    initControls((ArrayList<Song>)songObjects);
                }
            });
        }
    }

    public void initControls(ArrayList<Song> arrayList){
        recyclerViewListSong = findViewById(R.id.rv_list_song);
        layout = new LinearLayoutManager(this);
        recyclerViewListSong.setLayoutManager(layout);
        adapter = new ListSongAdapter(MainActivity.this, arrayList);
        adapter.setOnRecyclerViewItemClickListener(this);
        recyclerViewListSong.setAdapter(adapter);
        Constants.SONG_LIST.addAll(arrayList);
        Constants.SONG_NUMBER = Constants.SONG_LIST.size();
        text_song_name = findViewById(R.id.text_song_name);
        btnPause = findViewById(R.id.button_pause);
        btnPlay = findViewById(R.id.button_play);
        btnPre = findViewById(R.id.button_pre);
        btnNext = findViewById(R.id.button_next);

        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);

        if (SongManager.isServiceRunning(MusicService.class.getName(), getApplicationContext())) {
            changeButton(Constants.CURRENT_SONG);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setContentView(R.layout.activity_main);
                    SongViewModel model = ViewModelProviders.of(this).get(SongViewModel.class);
                    model.loadListSongs().observe(this, new Observer<List<Song>>() {
                        @Override
                        public void onChanged(@Nullable List<Song> songObjects) {
                            initControls((ArrayList<Song>)songObjects);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void displaySongs(ArrayList<Song> arrayList) {
        initControls(arrayList);
    }

    @Override
    public void controlPlayMusic(Song songObject) {


    }

    @Override
    public void onItemClick(Song song) {
        Constants.SONG_PAUSED = false;
        Constants.CURRENT_SONG = song;
        Constants.SONG_NUMBER = Constants.SONG_LIST.indexOf(song);
        if (SongManager.isServiceRunning(MusicService.class.getName(), getApplicationContext())) {
            Constants.SONG_CHANGE_HANDLER.sendMessage(Constants.SONG_CHANGE_HANDLER.obtainMessage());
        } else {
            Intent intentStartService = new Intent(getApplicationContext(), MusicService.class);
            intentStartService.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            startService(intentStartService);
        }
        changeButton(Constants.CURRENT_SONG );
    }


    public static void changeButton(Song s) {
        text_song_name.setText(s.getName());
        if(Constants.SONG_PAUSED){
            btnPause.setVisibility(View.INVISIBLE);
            btnPlay.setVisibility(View.VISIBLE);
        }else{
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_pre:
                SongManager.previousControl(getApplicationContext());
                break;
            case R.id.button_next:
                SongManager.nextControl(getApplicationContext());
                break;
            case R.id.button_play:
                SongManager.playControl(getApplicationContext());
                break;
            case R.id.button_pause:
                SongManager.pauseControl(getApplicationContext());
                break;
        }
    }
}
