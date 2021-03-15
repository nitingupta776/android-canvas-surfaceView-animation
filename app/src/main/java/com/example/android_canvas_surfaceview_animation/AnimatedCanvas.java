package com.example.android_canvas_surfaceview_animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class AnimatedCanvas extends View{
    private static Bitmap mAndroidLogoBMP;
    private int mImageAngle;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageX;
    private int mImageY;

    public AnimatedCanvas(Context context)  {
        super(context);
        initialize(context);
    }
    private void initialize(Context context) {
        BitmapDrawable mAndroidLogo = (BitmapDrawable) getResources().getDrawable(R.drawable.car);
        mAndroidLogoBMP = mAndroidLogo.getBitmap();
        mImageAngle = 0;
        mImageWidth = mAndroidLogo.getBitmap().getWidth();
        mImageHeight = mAndroidLogo.getBitmap().getHeight();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mImageX = point.x / 2 - mImageWidth / 2;
        mImageY = 100;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) { return; }
        if ((mImageAngle += 4) > 360) {
            mImageAngle = 0;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(mImageAngle);
        Bitmap rotatedLogo = Bitmap.createBitmap(mAndroidLogoBMP, 0, 0, mAndroidLogoBMP.getWidth(), mAndroidLogoBMP.getHeight(), matrix, true);
        canvas.drawBitmap(rotatedLogo, mImageX, mImageY, null);
        invalidate();
    }
}

