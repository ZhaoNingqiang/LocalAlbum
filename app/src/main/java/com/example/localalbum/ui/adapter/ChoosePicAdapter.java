package com.example.localalbum.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localalbum.AppContext;
import com.example.localalbum.R;
import com.example.localalbum.been.Picture;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.ArrayList;

/**
 * @description: author:zhaoningqiang
 * @time 16/5/25/下午6:50
 */
public class ChoosePicAdapter extends RecyclerView.Adapter<ChoosePicAdapter.ChoosePicViewHolder>{
    public static final int TYPE_FOLDER = 1;
    public static final int TYPE_PICTURE = 2;
    ArrayList<Picture> pictures;
    DisplayImageOptions options;
    public ChoosePicAdapter(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageForEmptyUri(R.drawable.dangkr_no_picture_small)
                .showImageOnFail(R.drawable.dangkr_no_picture_small)
                .showImageOnLoading(R.drawable.dangkr_no_picture_small)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .setImageSize(new ImageSize(((AppContext) context.getApplicationContext()).getQuarterWidth(), 0))
                .displayer(new SimpleBitmapDisplayer()).build();

    }

    public void setPictures(ArrayList<Picture> pictures) {
        this.pictures = pictures;
        notifyDataSetChanged();
    }

    @Override
    public ChoosePicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == TYPE_FOLDER){
            itemView = View.inflate(parent.getContext(), R.layout.ui_grid_folder,null);
        }else {
            itemView = View.inflate(parent.getContext(), R.layout.ui_grid_picture,null);
        }
        return new ChoosePicViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChoosePicViewHolder holder, int position) {
        Picture item = pictures.get(position);
        if (item.isFoloer){
            holder.tvFolderName.setText(item.folderName);
        }else {
            holder.ivPicture.setImageResource(R.mipmap.ic_launcher);
            ImageLoader.getInstance().displayImage(item.thumbnailUri, new ImageViewAware(holder.ivPicture), options,
                    null, null, item.orientation);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Picture item = pictures.get(position);
        if (item.isFoloer){
            return TYPE_FOLDER;
        }else {
            return TYPE_PICTURE;
        }
    }

    @Override
    public int getItemCount() {
        return pictures == null ? 0 : pictures.size();
    }

    static class ChoosePicViewHolder extends RecyclerView.ViewHolder{
        public TextView tvFolderName;
        public ImageView ivPicture;

        public ChoosePicViewHolder(View itemView) {
            super(itemView);
            tvFolderName = (TextView) itemView.findViewById(R.id.tv_grid_folder);
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_grid_picture);
        }
    }
}
