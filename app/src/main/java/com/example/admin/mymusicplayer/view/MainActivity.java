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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.mymusicplayer.utility.Constants;
import com.example.admin.mymusicplayer.R;
import com.example.admin.mymusicplayer.model.SongViewModel;
import com.example.admin.mymusicplayer.model.entity.SongObject;
import com.example.admin.mymusicplayer.service.MusicService;
import com.example.admin.mymusicplayer.view.adapter.ListSongAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, OnRecyclerViewItemClickListener, View.OnClickListener{


    private RecyclerView recyclerViewListSong;
    private LinearLayoutManager layout;
    private ListSongAdapter adapter;
    private TextView text_msg_not_found_song;
    private LinearLayout linea_control;
    private ImageButton btnPlay, btnPre, btnNext;
    private String[] permissionList = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
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
            model.loadListSongs().observe(this, new Observer<List<SongObject>>() {
                @Override
                public void onChanged(@Nullable List<SongObject> songObjects) {
                    initControls((ArrayList<SongObject>)songObjects);
                }
            });
        }
    }

    public void initControls(ArrayList<SongObject> arrayList){
        recyclerViewListSong = findViewById(R.id.rv_list_song);
        layout = new LinearLayoutManager(this);
        recyclerViewListSong.setLayoutManager(layout);
        adapter = new ListSongAdapter(MainActivity.this, arrayList);
        adapter.setOnRecyclerViewItemClickListener(this);
        recyclerViewListSong.setAdapter(adapter);
    }

    public void loadListSongsFail(){
        recyclerViewListSong = findViewById(R.id.rv_list_song);
        recyclerViewListSong.setVisibility(View.GONE);
        text_msg_not_found_song = findViewById(R.id.text_msg_not_found_song);
        text_msg_not_found_song.setVisibility(View.VISIBLE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setContentView(R.layout.activity_main);
                    SongViewModel model = ViewModelProviders.of(this).get(SongViewModel.class);
                    model.loadListSongs().observe(this, new Observer<List<SongObject>>() {
                        @Override
                        public void onChanged(@Nullable List<SongObject> songObjects) {
                            initControls((ArrayList<SongObject>)songObjects);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void displaySongs(ArrayList<SongObject> arrayList) {
        initControls(arrayList);
    }

    @Override
    public void controlPlayMusic(SongObject songObject) {
        btnNext = findViewById(R.id.button_next);
        btnPlay = findViewById(R.id.button_control);
        btnPre = findViewById(R.id.button_pre);

        btnNext.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

    }

    @Override
    public void onItemClick(SongObject song) {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        intent.putExtra(Constants.KEY_PASS_SONG_PLAYED, song);
        startService(intent);
        controlPlayMusic(song);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_pre:

                break;
            case R.id.button_next:
                break;
            case R.id.button_control:

                break;
        }
    }
}
