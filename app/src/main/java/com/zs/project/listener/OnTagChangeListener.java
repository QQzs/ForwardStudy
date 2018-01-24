package com.zs.project.listener;


import android.support.v7.widget.RecyclerView;

public interface OnTagChangeListener {
    void onStarDrag(RecyclerView.ViewHolder baseViewHolder);
    void onItemMove(int starPos, int endPos);
    void onMoveToMyChannel(int starPos, int endPos);
    void onMoveToOtherChannel(int starPos, int endPos);
}
