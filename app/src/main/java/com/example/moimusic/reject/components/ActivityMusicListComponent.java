package com.example.moimusic.reject.components;

import com.example.moimusic.reject.ActivityScope;
import com.example.moimusic.reject.models.ActivityMusicListModule;
import com.example.moimusic.reject.models.MainActivityModule;
import com.example.moimusic.ui.activity.ActivityMusicList;
import com.example.moimusic.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by qqq34 on 2016/2/23.
 */
@ActivityScope
@Component(modules = ActivityMusicListModule.class,dependencies = AppComponent.class)
public interface ActivityMusicListComponent {
    ActivityMusicList inject(ActivityMusicList activityMusicList);
}
