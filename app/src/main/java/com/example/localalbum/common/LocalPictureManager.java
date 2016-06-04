package com.example.localalbum.common;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.example.localalbum.AppContext;
import com.example.localalbum.been.Picture;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * author:zhaoningqiang
 * @time 16/5/26/下午3:20
 */
public class LocalPictureManager {
    private final Context context;
    private static LocalPictureManager instance;
    public ArrayList<Picture> checkedPics;
    final ArrayList<Picture> picturePaths = new ArrayList<>();
    final Map<String, List<Picture>> folders = new HashMap<>();

    //大图遍历字段
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.ORIENTATION
    };
    //小图遍历字段
    private static final String[] THUMBNAIL_STORE_IMAGE = {
            MediaStore.Images.Thumbnails._ID,
            MediaStore.Images.Thumbnails.DATA
    };

    private LocalPictureManager(Context context) {
        this.context = context;
    }

    public static LocalPictureManager getInstance(Context appContext) {
        if (instance == null){
            instance = new LocalPictureManager(appContext);
        }
        return instance;
    }



    public synchronized void initPictures() {
        picturePaths.clear();
        folders.clear();
        Picture picture;
        Picture pictureFolder;
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);//大图id
                String path = cursor.getString(1);//大图路径
                File file = new File(path);
                if (file.exists()){
                    //获取大图URI
                    String uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().
                            appendPath(Integer.toString(id)).build().toString();
                    if(StringUtils.isEmpty(uri)){
                        continue;
                    }
                    String thumbUri = getThumbnail(id, path);
                    if (StringUtils.isEmpty(thumbUri)){
                        thumbUri = uri;
                    }
                    //获取目录名
                    String folder = file.getParentFile().getName();

                    picture = new Picture();
                    picture.isFoloer = false;
                    picture.originalUri= uri;
                    picture.thumbnailUri = thumbUri;
                    int degree = cursor.getInt(2);
                    if (degree != 0) {
                        degree = degree + 180;
                    }
                    picture.orientation = 360-degree;

                    //判断文件夹是否已经存在
                    if (folders.containsKey(folder)) {
                        folders.get(folder).add(picture);
                    } else {
                        List<Picture> files = new ArrayList<>();
                        files.add(picture);
                        folders.put(folder, files);
                    }

                }
            }
            cursor.close();

            for (Map.Entry<String,List<Picture>> entry : folders.entrySet()){
                pictureFolder = new Picture();
                pictureFolder.isFoloer = true;
                pictureFolder.folderName = entry.getKey();
                picturePaths.add(pictureFolder);
                picturePaths.addAll(entry.getValue());
                Log.d("","  entry.getValue() = "+entry.getValue());
            }
        }

    }

    private String getThumbnail(int id, String path) {
        //获取大图的缩略图
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                THUMBNAIL_STORE_IMAGE,
                MediaStore.Images.Thumbnails.IMAGE_ID + " = ?",
                new String[]{id + ""},
                null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int thumId = cursor.getInt(0);
            String uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI.buildUpon().
                    appendPath(Integer.toString(thumId)).build().toString();
            cursor.close();
            return uri;
        }
        cursor.close();
        return null;
    }

    public ArrayList<Picture> getPicturePaths() {
        return picturePaths;
    }
}
