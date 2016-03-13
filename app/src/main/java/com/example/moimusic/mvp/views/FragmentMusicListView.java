package com.example.moimusic.mvp.views;

import android.content.Intent;

import com.example.moimusic.adapter.MusicListViewAdapter;

/**
 * Created by qqq34 on 2016/2/21.
 */
public interface FragmentMusicListView {
    void setAdapter(MusicListViewAdapter adapter);
    void hideSwipe(boolean isHide);
    void showSnackBar(String s);
    void StartActivty(Intent intent);
    void updateList();
}
