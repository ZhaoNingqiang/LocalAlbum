package com.example.localalbum.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.localalbum.R;
import com.example.localalbum.ui.adapter.ChoosePicAdapter;

/**
 * @description: author:zhaoningqiang
 * @time 16/5/25/下午6:28
 */
public class ChoosePicActivity extends AppCompatActivity {
    RecyclerView rv_pics;
    ChoosePicAdapter choosePicAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pic);
        rv_pics = (RecyclerView) findViewById(R.id.rv_pics);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 0;
            }
        });
        rv_pics.setLayoutManager(manager);

        choosePicAdapter = new ChoosePicAdapter();
        rv_pics.setAdapter(choosePicAdapter);


    }
}
