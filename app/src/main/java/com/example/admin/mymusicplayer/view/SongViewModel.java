package com.example.admin.mymusicplayer.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.example.admin.mymusicplayer.model.entity.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by TamTT on 7/18/2018.
 */

public class SongViewModel extends AndroidViewModel{
    private MutableLiveData<List<Song>> songs;
    private Application application;
    public SongViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public LiveData<List<Song>> loadListSongs(){
        if(songs == null){
            songs = new MutableLiveData<>();
            loadListSongFromDevice();
        }
        return songs;
    }

    public LiveData<List<Song>> loadListSongFromDevice() {
        ArrayList<Song> arrayList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] m_data = {MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST};
        Cursor c = application.getContentResolver().query(uri, m_data, MediaStore.Audio.Media.IS_MUSIC + " != 0", null, null);
        if (c == null) return null;
        if (!c.moveToFirst()) return null;
        int idColumn = c.getColumnIndex(MediaStore.Audio.Media.DATA);
        int titleColumn = c.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int artistColumn = c.getColumnIndex(MediaStore.Audio.Media.ARTIST);

        do {
            Song songObject = new Song(c.getString(titleColumn), c.getString(artistColumn), c.getString(idColumn));
            arrayList.add(songObject);
        } while (c.moveToNext());

        arrayList.addAll(getPlayList(getAllSong(Environment.getExternalStorageDirectory())));

        HashSet h = new HashSet(arrayList);
        arrayList.clear();
        arrayList.addAll(h);
        Arrays.asList(arrayList);
        songs.setValue(arrayList); // call from main thread ?? both run?
        //songs.postValue(arrayList); // call from background threda
        return songs;
    }

    public ArrayList<File> getAllSong(File directory) {

        File[] allFile = directory.listFiles();
        ArrayList<File> al = new ArrayList<File>();
        for (File singleFile : allFile) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(this.getAllSong(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".m4a") || singleFile.getName().endsWith(".wav") || singleFile.getName().endsWith(".flac")) {
                    al.add(singleFile);
                }
            }
        }
        return al;
    }

    public static ArrayList<Song> getPlayList(ArrayList<File> arr) {
        ArrayList<Song> arrayList1 = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            String nameFile = arr.get(i).getName().toString();
            String[] a = nameFile.split("_");
            if (a.length != 0) {
                String id = arr.get(i).getPath();
                if (a[0].compareTo("com.zing.mp3") != 0) {
                    arrayList1.add(new Song(a[0], "Zingmp3", id));
                }

            }

        }
        return arrayList1;
    }
}
