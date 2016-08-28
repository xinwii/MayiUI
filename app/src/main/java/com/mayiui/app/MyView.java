package com.mayiui.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ddsc on 8/28/2016.
 */
public class MyView extends View {
    Paint paint, behindPaint;
    double mMove = 0;
    int yPosition = 0;
    ArrayList<Integer> showList, allList;
    int y[];

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        behindPaint = new Paint();
        behindPaint.setStyle(Paint.Style.FILL);
        behindPaint.setAntiAlias(true);
        behindPaint.setColor(getResources().getColor(R.color.behind_wave));
        allList = new ArrayList<>();
        showList = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int viewPosition[] = new int[2];
        getLocationInWindow(viewPosition);
        for (int x = 0; x <= (int) (4d * getWidth() / 3d); x++) {
            double w = 3d * Math.PI / (2d * getWidth());
            double y = (getHeight() / 2d) * Math.sin(w * x + Math.PI + mMove) + getHeight() / 2;
            allList.add((int) y);
        }
        for(int i=(int) (2d * getWidth() / 3d);i<allList.size();i++){
            showList.add(allList.get(i));
        }
        for(int i=0;i<(int) (2d * getWidth() / 3d);i++){
            showList.add(allList.get(i));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       drawBehindWave(canvas);
        drawWave(canvas);
    }

    private void drawWave(Canvas canvas) {
        Path path = new Path(); // 定义一条路径
        int width = getWidth();
        int height = getHeight();
        path.reset();
        path.moveTo(0, yPosition + height / 2);
        for(int ii=0;ii<40;ii++){
            int size0 = allList.get(0);
            allList.remove(0);
            allList.add(size0);
        }
        for(int x=0;x<=width;x++) {
            path.lineTo(x,allList.get(x));
        }
        path.lineTo(getWidth(), (float) (getHeight() / 2d) + height / 2);
        path.lineTo(0, (float) (getHeight() / 2d) + height / 2);
        path.lineTo(0, height / 2 + yPosition);
        path.close();
        canvas.drawPath(path, paint);
       // mMove += (Math.PI / 4);
        postInvalidateDelayed(20);
    }

    private void drawBehindWave(Canvas canvas) {
        Path path = new Path(); // 定义一条路径
        int width = getWidth();
        int height = getHeight();
        path.moveTo(0, yPosition + height / 2);
        for(int ii=0;ii<40;ii++){
            int size0 = showList.get(0);
            showList.remove(0);
            showList.add(size0);
        }
        for(int x=0;x<=width;x++) {
            path.lineTo(x,showList.get(x));
        }
        path.lineTo(getWidth(), (float) (getHeight() / 2d) + height / 2);
        path.lineTo(0, (float) (getHeight() / 2d) + height / 2);
        path.lineTo(0, height / 2 + yPosition);
        path.close();
        canvas.drawPath(path, behindPaint);
        // mMove += (Math.PI / 4);
       // postInvalidateDelayed(20);
    }
}
