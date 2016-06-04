package com.example.localalbum.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.localalbum.R;
import com.example.localalbum.common.LocalPictureManager;
import com.example.localalbum.ui.adapter.ChoosePicAdapter;

/**
 * @description: author:zhaoningqiang
 * @time 16/5/25/下午6:28
 */
public class ChoosePicActivity extends Activity {
    RecyclerView rv_pics;
    ChoosePicAdapter choosePicAdapter;
    LocalPictureManager localPictureManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pic);
        rv_pics = (RecyclerView) findViewById(R.id.rv_pics);
        choosePicAdapter = new ChoosePicAdapter(this);
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (choosePicAdapter.getItemViewType(position)) {
                    case ChoosePicAdapter.TYPE_FOLDER:
                        return manager.getSpanCount();
                    default:
                        return 1;
                }
            }
        });
        rv_pics.setLayoutManager(manager);
        rv_pics.setAdapter(choosePicAdapter);

        localPictureManager = LocalPictureManager.getInstance(getApplicationContext());
        initPicture();

    }

    private void initPicture() {
        new InitPicturesTask().execute();
    }

    class InitPicturesTask extends AsyncTask<Void, Void, Void> {
        long startTime;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            startTime = System.currentTimeMillis();
            Log.d("","InitPicturesTask onPreExecute ");
        }

        @Override
        protected Void doInBackground(Void... params) {
            localPictureManager.initPictures();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("", "InitPicturesTask onPostExecute time = "+(System.currentTimeMillis() - startTime));
            choosePicAdapter.setPictures(localPictureManager.getPicturePaths());
        }
    }
}
