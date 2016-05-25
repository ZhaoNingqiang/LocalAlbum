package com.example.localalbum.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @description: author:zhaoningqiang
 * @time 16/5/25/下午6:50
 */
public class ChoosePicAdapter extends RecyclerView.Adapter<ChoosePicAdapter.ChoosePicViewHolder>{


    @Override
    public ChoosePicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChoosePicViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ChoosePicViewHolder extends RecyclerView.ViewHolder{

        public ChoosePicViewHolder(View itemView) {
            super(itemView);
        }
    }
}
