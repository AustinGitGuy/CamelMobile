package com.austinlaimos.camelmobile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class Game extends SurfaceView implements SurfaceHolder.Callback, View.OnDragListener, View.OnTouchListener {

    public static Game instance;

    long lastTime;
    int width, height;

    List<Scene> scenes;

    public List<Enemy> enemyTemplates;
    public List<Piece> pieceTemplates;

    int curScene = 0;

    Game(Context context, int x, int y){
        super(context);

        lastTime = System.nanoTime();

        width = x;
        height = y;

        instance = this;

        enemyTemplates = new ArrayList<>();
        pieceTemplates = new ArrayList<>();

        enemyTemplates.add(new Enemy(new Rect(0, 0, 100, 100), new Point(-100, -100), Color.rgb(0, 255, 0), 10, 0));
        enemyTemplates.add(new Enemy(new Rect(0, 0, 100, 100), new Point(-100, -100), Color.rgb(255, 255, 0), 15, 1));

        pieceTemplates.add(new Piece(50, new Point(-100, -100), Color.rgb(255, 0, 0), 175, .125f));

        scenes = new ArrayList<>();
        scenes.add(new TitleScene(x, y));
        scenes.add(new OptionsScene(x, y));
        scenes.add(new LevelScene(x, y));

        setFocusable(true);
        setWillNotDraw(false);

        setOnTouchListener(this);
        setOnDragListener(this);
    }

    public Scene getCurScene(){
        return scenes.get(curScene);
    }

    public void update(){

        long newTime = System.nanoTime();

        long deltaTime = (newTime - lastTime) / 1000000;

        lastTime = newTime;

        scenes.get(curScene).update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        scenes.get(curScene).draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public boolean onDrag(View v, DragEvent event){
        return scenes.get(curScene).onDrag(v, event);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View v, MotionEvent event){
        //startDragAndDrop(null, shadow, v, 0);
        return scenes.get(curScene).onTouch(v, event);
    }

    public static void AdvanceToEnemy(){
        ((LevelScene)instance.scenes.get(instance.curScene)).AdvanceToEnemy();
    }

    public static void ToGame(){
        instance.curScene = 2;
    }

    public static void ToOptions(){
        instance.curScene = 1;
        ((OptionsScene)instance.scenes.get(1)).resetOptions();
    }

    public static void ToTitle(){
        instance.curScene = 0;
    }

    public static void EnemyColors(){
        ((OptionsScene)instance.scenes.get(1)).toEnemyColors();
    }
}