package com.example.moimusic.mvp.views;

import android.content.Intent;

/**
 * Created by qqq34 on 2016/2/29.
 */
public interface IActivityPlayNowView {
    void setPlayButton(boolean ispause);
    void updataPlayView();
    void setProgressbar(int current,int all );
    void startActivity(Intent intent);
}
