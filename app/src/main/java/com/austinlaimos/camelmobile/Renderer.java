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
public class Renderer extends SurfaceView implements SurfaceHolder.Callback, View.OnDragListener, View.OnTouchListener {

    public static Renderer instance;

    public List<Piece> pieces;
    public List<Piece> dispPieces;
    public List<Tile> tiles;
    public List<Enemy> enemies;

    List<UIObject> uiObjects;

    int width, height;

    DragShadowBuilder shadow;

    boolean inGame = false;

    long lastTime;

    int enemiesLeft;

    long enemyTimer;

    int lives, curLevel = -1;

    int[] levelLives;
    int[] enemyNum;
    int[] spawnTime;

    final int LEVEL_NUM = 10;

    Renderer(Context context, int x, int y){
        super(context);

        lastTime = System.nanoTime();

        width = x;
        height = y;

        instance = this;

        enemyTimer = 0;

        pieces = new ArrayList<>();
        dispPieces = new ArrayList<>();
        uiObjects = new ArrayList<>();
        tiles = new ArrayList<>();
        enemies = new ArrayList<>();

        enemyNum = new int[LEVEL_NUM];
        levelLives = new int[LEVEL_NUM];
        spawnTime = new int[LEVEL_NUM];

        for(int i = 0; i < LEVEL_NUM; i++){
            levelLives[i] = 5 * i + 5;
            enemyNum[i] = 5 * i + 5;
            spawnTime[i] = 5000 - (i * 300);
            if(spawnTime[i] <= 100){
                spawnTime[i] = 100;
            }
        }

        shadow = new DragShadowBuilder(this);

        //Create the UI
        uiObjects.add(new UIObject(new Rect(x - 150, 0, x, y), Color.rgb(100, 100, 100)));

        //Add the set button
        uiObjects.add(new UIObject(new Rect(0, y - 250, 150, y), Color.rgb(0, 0, 0), "Set", Color.rgb(255, 255, 255), 100, 90, "AdvanceToEnemy"));

        uiObjects.add(new UIObject(new Rect(x - 150, y - 500, x, y), Color.rgb(100, 100, 100), "Lives: ", Color.rgb(255, 255, 255), 100, 90));

        //Add a dummy pieces offscreen
        pieces.add(new Piece(new Rect(0, 0, 100, 100), new Point(-100, -100), Color.rgb(255, 0, 0), 175, .5f));

        //Add the display pieces
        dispPieces.add(new DisplayPiece(pieces.get(0), new Point(x - 75 , 75)));

        //Create the level
        tiles.add(new Tile(new Rect(0, 0, 150, y - 150), new Point(x - 350, (y - 150) / 2), Color.rgb(0, 0, 255), Tile.Direction.down));

        tiles.add(new Tile(new Rect(0, 0, 500, 150), new Point(x - 675, y - 225), Color.rgb(0, 0, 255), Tile.Direction.left));

        tiles.add(new Tile(new Rect(0, 0, 150, y - 300), new Point(x - 850, (y - 300) / 2), Color.rgb(0, 0, 255), Tile.Direction.up));

        //Add the dummy enemies offscreen
        enemies.add(new Enemy(new Rect(0, 0, 100, 100), new Point(-100, -100), Color.rgb(0, 255, 0), 10));

        setFocusable(true);
        setWillNotDraw(false);

        setOnTouchListener(this);
        setOnDragListener(this);
    }

    public void update(){

        long newTime = System.nanoTime();

        long deltaTime = (newTime - lastTime) / 1000000;

        lastTime = newTime;

        enemyTimer += deltaTime;

        if(enemiesLeft > 0){
            if(enemyTimer >= spawnTime[curLevel]){
                SpawnEnemy();
                enemiesLeft--;
                enemyTimer = 0;
            }
        }

        for(int i = 1; i < pieces.size(); i++){
            pieces.get(i).update(deltaTime);
        }
        for(int i = 0; i < dispPieces.size(); i++){
            dispPieces.get(i).update(deltaTime);
        }
        for(int i = 0; i < tiles.size(); i++){
            tiles.get(i).update(deltaTime);
        }
        if(inGame){
            //Dont update the first enemy (the dummy)
            for(int i = 1; i < enemies.size(); i++){
                enemies.get(i).update(deltaTime);
            }
            if(enemies.size() == 1 && enemiesLeft <= 0){
                inGame = false;
                if(curLevel >= LEVEL_NUM - 1){
                    //YOU WIN
                }
                else {
                    uiObjects.add(new UIObject(new Rect(0, height - 250, 150, height), Color.rgb(0, 0, 0), "Set", Color.rgb(255, 255, 255), 100, 90, "AdvanceToEnemy"));
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        for(int i = 0; i < tiles.size(); i++){
            tiles.get(i).draw(canvas);
        }

        for(int i = 1; i < pieces.size(); i++){
            pieces.get(i).draw(canvas);
        }

        //Dont update the first enemy (the dummy)
        for(int i = 1; i < enemies.size(); i++){
            enemies.get(i).draw(canvas);
        }

        for(int i = 0; i < uiObjects.size(); i++){
            uiObjects.get(i).draw(canvas);
        }

        for(int i = 0; i < dispPieces.size(); i++){
            dispPieces.get(i).draw(canvas);
        }
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

    public boolean getPlaceableArea(int x, int y){
        for(int i = 0; i < tiles.size(); i++){
            if(tiles.get(i).rect.contains(x, y)) return false;
        }
        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event){
        if(event.getAction() == DragEvent.ACTION_DRAG_ENTERED){
            for(int i = 0; i < dispPieces.size(); i++){
                dispPieces.get(i).onDrag((int)event.getX(), (int)event.getY());
            }
        }
        return false;
    }

    public void SpawnEnemy(){
        Enemy tmpPiece = new Enemy(enemies.get(0));
        Tile tile = tiles.get(0);
        if(tile.dir == Tile.Direction.down){
            tmpPiece.translate(tile.rect.centerX(), tile.rect.top);
            tmpPiece.speed = tile.rect.height() / 100;
        }
        else if(tile.dir == Tile.Direction.up){
            tmpPiece.translate(tile.rect.centerX(), tile.rect.bottom);
            tmpPiece.speed = tile.rect.height() / 100;
        }
        else if(tile.dir == Tile.Direction.left){
            tmpPiece.translate(tile.rect.right, tile.rect.centerY());
            tmpPiece.speed = tile.rect.width() / 100;
        }
        else if(tile.dir == Tile.Direction.right){
            tmpPiece.translate(tile.rect.left, tile.rect.centerY());
            tmpPiece.speed = tile.rect.width() / 100;
        }
        Renderer.instance.enemies.add(tmpPiece);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View v, MotionEvent event){
        //startDragAndDrop(null, shadow, v, 0);

        switch(event.getAction()){
            case(MotionEvent.ACTION_DOWN):
                for(int i = 0; i < dispPieces.size(); i++){
                    dispPieces.get(i).onTap((int) event.getX(), (int) event.getY());
                }
                for(int i = 0; i < uiObjects.size(); i++){
                    uiObjects.get(i).onTap((int) event.getX(), (int) event.getY());
                }
                return true;
            case(MotionEvent.ACTION_MOVE):
                for(int i = 0; i < dispPieces.size(); i++){
                    dispPieces.get(i).onDrag((int) event.getX(), (int) event.getY());
                }
                return true;
        }

        return false;
    }

    public void enemyEnd(Enemy enemy){
        enemies.remove(enemy);
        lives--;
        uiObjects.get(2).updateText((lives));
        if(lives <= 0){
            //YOU LOSE
        }
    }

    public static void AdvanceToEnemy(){
        instance.inGame = true;
        instance.SpawnEnemy();
        instance.curLevel++;
        instance.enemiesLeft = instance.enemyNum[instance.curLevel] - 1;
        instance.lives = instance.levelLives[instance.curLevel];
        instance.uiObjects.remove(1);
        instance.uiObjects.get(2).updateText((instance.lives));
        instance.enemyTimer = 0;
    }
}