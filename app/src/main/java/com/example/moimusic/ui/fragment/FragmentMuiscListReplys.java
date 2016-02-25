package com.example.moimusic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moimusic.R;
import com.example.moimusic.adapter.MusicListContentViewAdapter;
import com.example.moimusic.adapter.MusicListReplyAdapter;
import com.example.moimusic.mvp.presenters.FragmentMuiscListReplysPresenter;
import com.example.moimusic.mvp.views.FragmentMuiscListReplysView;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.reject.components.AppComponent;
import com.example.moimusic.reject.components.DaggerFragmentMuiscListReplysComponent;
import com.example.moimusic.reject.components.DaggerFragmentMusicListComponent;
import com.example.moimusic.reject.models.FragmentMuiscListReplysModule;
import com.example.moimusic.reject.models.FragmentMusicListModule;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rey.material.widget.ProgressView;

import javax.inject.Inject;

/**
 * Created by qqq34 on 2016/2/22.
 */
public class FragmentMuiscListReplys extends BaseFragment implements FragmentMuiscListReplysView{
    public static final String EXTRA_LIST_ID ="com.example.moimusic.listid";
@Inject
    FragmentMuiscListReplysPresenter presenter;
    private RecyclerView recyclerView;
    private EditText editText;
    private SimpleDraweeView simpleDraweeView;
    private ProgressView progressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id = getArguments().getString(EXTRA_LIST_ID);
        presenter.attach(id,this,getActivity());
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerFragmentMuiscListReplysComponent.builder()
                .appComponent(appComponent)
                .fragmentMuiscListReplysModule(new FragmentMuiscListReplysModule(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music_list_replys, container, false);
        initView(v);
        initClick();
        presenter.onCreate();
        return v;
    }
private void initView(View v){
    recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    editText = (EditText) v.findViewById(R.id.saySomething);
    editText.setEnabled(false);
    simpleDraweeView = (SimpleDraweeView)v.findViewById(R.id.sendview);
    simpleDraweeView.setEnabled(false);
    progressView = (ProgressView)v.findViewById(R.id.progress);
    progressView.setVisibility(View.INVISIBLE);
}
    private void initClick(){
        simpleDraweeView.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(editText.getText()))
            presenter.sendComment(editText.getText().toString());
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount -1) && isSlidingToLast) {
                        //加载更多功能的代码
                        presenter.onCreate();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if(dy> 0){
                    //大于0表示，正在向右滚动
                    isSlidingToLast = true;
                }else{
                    //小于等于0 表示停止或向左滚动
                    isSlidingToLast = false;
                }

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.onDestroy();
        }
    }
    public static FragmentMuiscListReplys newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(EXTRA_LIST_ID, id);
        FragmentMuiscListReplys fragment = new FragmentMuiscListReplys();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void ShowList(MusicListReplyAdapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        editText.setEnabled(true);
        simpleDraweeView.setEnabled(true);
    }

    @Override
    public void ShowSnackBar(String s) {
        Snackbar.make(recyclerView,s,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setViewEnable(boolean isEnable) {
        if (isEnable){
            progressView.setVisibility(View.INVISIBLE);
        }else {
            progressView.setVisibility(View.VISIBLE);
        }
        recyclerView.setEnabled(isEnable);
        editText.setEnabled(isEnable);
        simpleDraweeView.setEnabled(isEnable);
    }

    @Override
    public void updataList() {
        editText.setText("");
        presenter.setPage(1);
        presenter.onCreate();
    }
}
