package com.austinlaimos.camelmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.*;

public class MainActivity extends AppCompatActivity {

    Renderer renderer;
    Looper updateLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point size = new Point(0, 0);

        getWindowManager().getDefaultDisplay().getSize(size);

        renderer = new Renderer(this, size.x, size.y);
        setContentView(renderer);

        Init();
    }

    Runnable updateLoop = new Runnable(){
        @Override
        public void run(){
            renderer.update();
            setContentView(renderer);
        }
    };

    private void Init(){
        updateLooper = new Looper(updateLoop, 16);
    }
}