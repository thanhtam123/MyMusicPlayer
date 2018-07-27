package com.example.admin.mymusicplayer.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.mymusicplayer.R;
import com.example.admin.mymusicplayer.model.entity.Song;
import com.example.admin.mymusicplayer.view.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

/**
 * Created by TamTT on 7/18/2018.
 */

public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.MyViewHolder> {

    private Activity activity;
    private ArrayList<Song> arrayList;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public ListSongAdapter(Activity activity, ArrayList<Song> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    public void setOnRecyclerViewItemClickListener(
            OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new MyViewHolder(item, mOnRecyclerViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MyViewHolder itemNewFeedViewHolder = holder;
        itemNewFeedViewHolder.fillData(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
        private TextView txtTenBaiHat;
        private Song songObject;

        public MyViewHolder(View itemView, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
            super(itemView);
            mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
            txtTenBaiHat = itemView.findViewById(R.id.text_song_name_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnRecyclerViewItemClickListener.onItemClick(songObject);
                }
            });
        }
        public void fillData(Song songObject) {
            this.songObject = songObject;
            txtTenBaiHat.setText(songObject.getName().toString());
        }

    }

}
