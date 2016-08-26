package com.mayiui.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private ImageView img;
    private int startY, padding, paddingTop;
    private float scale = 1f;
    private WaveHelper mWaveHelper;
    private WaveView mWaveView;
    private MyRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        paddingTop = img.getPaddingTop();
        mWaveView = (WaveView) findViewById(R.id.wave);
        mWaveHelper = new WaveHelper(mWaveView);
        mRecyclerView = (MyRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                padding = tempY - startY;
                if (padding > 0 && padding < 300) {
                    if (mWaveHelper.getIsShow()) {
                        mWaveView.setVisibility(View.VISIBLE);
                    }
                    scale = (padding / 800f) + 1f;
                    img.setScaleX(scale);
                    img.setPadding(img.getPaddingLeft(), padding, img.getPaddingRight(), img.getPaddingBottom());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mWaveView.isShowWave()) {
                    mWaveView.setVisibility(View.INVISIBLE);
                }
                img.setScaleX(1);
                img.setPadding(img.getPaddingLeft(), paddingTop, img.getPaddingRight(), img.getPaddingBottom());
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWaveHelper.getIsShow()) {
            mWaveHelper.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mWaveHelper.getIsShow()) {
            mWaveHelper.start();
        }
    }
}