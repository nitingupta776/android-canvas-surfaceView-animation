package com.example.android_canvas_surfaceview_animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {
    private AnimatedCanvas animatedCanvas;
    private AnimatedSurfaceView mAnimatedSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

//        this.animatedCanvas = new AnimatedCanvas(this);
//        setContentView(this.animatedCanvas);

        this.mAnimatedSurfaceView = new AnimatedSurfaceView(this);
        setContentView(this.mAnimatedSurfaceView);

    }
}