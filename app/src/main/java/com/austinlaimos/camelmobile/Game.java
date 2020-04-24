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

    final int LEVEL_NUM = 5;

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
        scenes.add(new LevelSelectScene(x, y));

        //Scene 1
        {
            LevelScene tmpScene = new LevelScene(x, y);

            tmpScene.pieces = new ArrayList<>();
            tmpScene.dispPieces = new ArrayList<>();
            tmpScene.uiObjects = new ArrayList<>();
            tmpScene.tiles = new ArrayList<>();
            tmpScene.enemies = new ArrayList<>();

            tmpScene.enemyNum = new ArrayList[LEVEL_NUM];
            tmpScene.levelLives = new int[LEVEL_NUM];
            tmpScene.spawnTime = new int[LEVEL_NUM];

            for(int i = 0; i < LEVEL_NUM; i++){
                tmpScene.levelLives[i] = 3;
                tmpScene.enemyNum[i] = new ArrayList<>();

                for(int c = 0; c < 5 * i + 5; c++){
                    if(i == 2 || i == 3){
                        tmpScene.enemyNum[i].add(1);
                    }
                    else if(i == 4){
                        tmpScene.enemyNum[i].add(c % 2);
                    }
                    else {
                        tmpScene.enemyNum[i].add(0);
                    }
                }

                if(i == 0 || i == 2){
                    tmpScene.spawnTime[i] = 3000;
                }
                else tmpScene.spawnTime[i] = 1500;
            }

            //Create the UI
            tmpScene.uiObjects.add(new UIObject(new Rect(x - 150, 0, x, y), Color.rgb(100, 100, 100)));

            //Add the set button
            tmpScene.uiObjects.add(new UIObject(new Rect(0, y - 250, 150, y), Color.rgb(0, 0, 0), "Set", Color.rgb(255, 255, 255), 100, 90, "AdvanceToEnemy"));
            tmpScene.uiObjects.add(new UIObject(new Rect(x - 150, y - 600, x, y), Color.rgb(100, 100, 100), "Lives: ", Color.rgb(255, 255, 255), 100, 90));

            //Add the display pieces
            tmpScene.dispPieces.add(new DisplayPiece(Game.instance.pieceTemplates.get(0), new Point(x - 75 , 75)));

            //Create the level
            tmpScene.tiles.add(new Tile(new Rect(0, 0, 150, y - 150), new Point(x - 350, (y - 150) / 2), Color.rgb(0, 0, 255), Tile.Direction.down));
            tmpScene.tiles.add(new Tile(new Rect(0, 0, 500, 150), new Point(x - 675, y - 225), Color.rgb(0, 0, 255), Tile.Direction.left));
            tmpScene.tiles.add(new Tile(new Rect(0, 0, 150, y - 300), new Point(x - 850, (y - 300) / 2), Color.rgb(0, 0, 255), Tile.Direction.up));

            scenes.add(tmpScene);
        }

        //Scene 2

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

    public static void ToLevelSelect(){
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

    public static void SceneOne(){
        instance.curScene = 3;
    }
}