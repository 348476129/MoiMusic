package com.example.moimusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.moimusic.AppApplication;
import com.example.moimusic.R;
import com.example.moimusic.mvp.model.entity.EvenActivityMusicListCall;
import com.example.moimusic.mvp.model.entity.EvenCall;
import com.example.moimusic.mvp.model.entity.EvenMusicListContentAdapterCall;
import com.example.moimusic.mvp.model.entity.MoiUser;
import com.example.moimusic.mvp.model.entity.Music;
import com.example.moimusic.mvp.model.entity.MusicList;
import com.example.moimusic.play.PlayListSingleton;
import com.example.moimusic.ui.activity.ActivityPlayNow;
import com.example.moimusic.ui.activity.LogActivity;
import com.example.moimusic.ui.dialog.ListCollectBuild;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rey.material.app.DialogFragment;

import java.util.List;

import cn.bmob.v3.BmobUser;
import de.greenrobot.event.EventBus;


/**
 * Created by qqq34 on 2016/2/24.
 */
public class MusicListContentViewAdapter extends RecyclerView.Adapter<MusicListContentViewAdapter.MyViewHolder> {

    List<Music> musicList;
FragmentActivity activity;
    Context context;
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public MusicListContentViewAdapter(List<Music> musicList,FragmentActivity activity) {
        this.musicList = musicList;
        this.activity = activity;
        EventBus.getDefault().register(this);
    }

    @Override
    public MusicListContentViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                AppApplication.context).inflate(R.layout.fragment_music_list_content_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicListContentViewAdapter.MyViewHolder holder, int position) {
        if (musicList.get(position).getMusicName() != null) {
            holder.tv.setTextColor(AppApplication.context.getResources().getColor(R.color.black));
            holder.tv.setText(musicList.get(position).getMusicName());
        }
        if (PlayListSingleton.INSTANCE.getCurrentMusicId() != null) {
            if (musicList.get(position).getMusicName() != null && PlayListSingleton.INSTANCE.getCurrentMusicId().equals(musicList.get(position).getObjectId())) {
                holder.tv.setTextColor(AppApplication.context.getResources().getColor(R.color.colorPrimary));
            }
        }

        if (musicList.get(position).getSinger() != null) {
            holder.tvsub.setText(musicList.get(position).getSinger());
        }
        if (mOnItemClickLitener != null) {
            holder.materialRippleLayout.setOnClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            });
            holder.materialRippleLayout.setOnLongClickListener(v -> {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
                return false;
            });
        }
        holder.simpleDraweeView.setOnClickListener(v -> {
            if (BmobUser.getCurrentUser(context,MoiUser.class)==null){
                context.startActivity(new Intent(context, LogActivity.class));
            }else {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                DialogFragment dialogFragment = DialogFragment.newInstance(new ListCollectBuild(musicList.get(position).getObjectId()));
                dialogFragment.show(fragmentManager,"dididi");
            }

        });
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialRippleLayout materialRippleLayout;
        TextView tv, tvsub;
        SimpleDraweeView simpleDraweeView;

        public MyViewHolder(View view) {
            super(view);
            materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple);
            tv = (TextView) view.findViewById(R.id.title);
            tvsub = (TextView) view.findViewById(R.id.subtitle);
            simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.more);
        }
    }

    public void onEventMainThread(EvenMusicListContentAdapterCall even) {
        notifyDataSetChanged();
    }
}
