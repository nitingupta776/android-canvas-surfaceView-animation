package com.example.android_canvas_surfaceview_animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class AnimatedSurfaceView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private AnimationThread myThread;
    private static BitmapDrawable mAndroidLogo;
    private int mImageAngle;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageX;
    private int mImageY;

    public AnimatedSurfaceView(Context context) {
        super(context);
        initialize();
    }

    private void initialize(){

        myThread = new AnimationThread(this);

        surfaceHolder = getHolder();
        mAndroidLogo = (BitmapDrawable) getResources().getDrawable(R.drawable.car);

        mImageAngle = 0;
        mImageWidth = mAndroidLogo.getBitmap().getWidth();
        mImageHeight = mAndroidLogo.getBitmap().getHeight();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mImageX = point.x / 2 - mImageWidth / 2;
        mImageY = 100;

        surfaceHolder.addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                myThread.setRunning(true);
                myThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                myThread.setRunning(false);
                while (retry) {
                    try {
                        myThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }});
    }

    protected void drawSomething(Canvas canvas) {
        if (canvas == null) { return; }
        canvas.drawColor(Color.WHITE);
        if ((mImageAngle += 4) > 360) {
            mImageAngle = 0;
        }
        canvas.save();
        canvas.rotate( mImageAngle, mImageX + mImageWidth / 2, mImageY + mImageHeight / 2);
        canvas.drawBitmap(mAndroidLogo.getBitmap(), mImageX, mImageY, null);
        canvas.restore();

    }

    public class AnimationThread extends Thread {

        AnimatedSurfaceView myView;
        private boolean running = false;

        public AnimationThread(AnimatedSurfaceView view) {
            myView = view;
        }

        public void setRunning(boolean run) {
            running = run;
        }

        @Override
        public void run() {
            while(running){

                Canvas canvas = myView.getHolder().lockCanvas();

                if(canvas != null){
                    synchronized (myView.getHolder()) {
                        myView.drawSomething(canvas);
                    }
                    myView.getHolder().unlockCanvasAndPost(canvas);
                }

                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }


}
