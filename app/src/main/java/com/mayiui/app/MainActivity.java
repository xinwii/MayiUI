package com.mayiui.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    ImageView img;
    ViewGroup.LayoutParams params;
    int oldPoint, newPoint, padding, paddingTop, paddingBottom;
float scale = 1f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        params = img.getLayoutParams();
        paddingTop = img.getPaddingTop();
        paddingBottom = img.getPaddingBottom();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldPoint = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                newPoint = (int) event.getY();
                padding = newPoint - oldPoint;
                if(padding>0){
                scale = (padding/800f)+1f;
                img.setScaleX(scale);
                img.setScaleY(scale);
                img.setPadding(img.getPaddingLeft(), padding, img.getPaddingRight(),img.getPaddingBottom());}
                break;
            case MotionEvent.ACTION_UP:
                img.setScaleX(1);
                img.setScaleY(1);
                img.setPadding(0, paddingTop, 0, paddingBottom);
                break;
        }
        return super.onTouchEvent(event);
    }
}