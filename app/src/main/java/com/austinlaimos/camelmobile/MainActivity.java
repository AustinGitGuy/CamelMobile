package com.austinlaimos.camelmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.*;

public class MainActivity extends AppCompatActivity {

    Game game;
    Looper updateLooper;

    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        instance = this;

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point size = new Point(0, 0);

        getWindowManager().getDefaultDisplay().getSize(size);

        game = new Game(this, size.x, size.y);
        setContentView(game);

        Init();
    }

    Runnable updateLoop = new Runnable(){
        @Override
        public void run(){
            game.update();
            setContentView(game);
        }
    };

    private void Init(){
        updateLooper = new Looper(updateLoop, 16);
    }
}