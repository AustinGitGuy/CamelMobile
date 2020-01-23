package com.austinlaimos.camelmobile;

import androidx.appcompat.app.AppCompatActivity;
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

        renderer = new Renderer(this);

        Init();
    }

    Runnable updateLoop = new Runnable(){
        @Override
        public void run(){
            renderer.Update();
            setContentView(renderer);
        }
    };

    private void Init(){


        updateLooper = new Looper(updateLoop, 16);
    }
}
