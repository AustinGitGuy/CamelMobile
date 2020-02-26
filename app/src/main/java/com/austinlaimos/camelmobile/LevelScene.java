package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LevelScene extends Scene {
    public List<Piece> pieces;
    public List<Piece> dispPieces;
    public List<Tile> tiles;
    public List<Enemy> enemies;

    List<UIObject> uiObjects;

    int width, height;

    boolean inGame = false;

    int enemiesLeft;
    int enemyCount;

    long enemyTimer;

    int lives, curLevel = -1;

    int[] levelLives;
    ArrayList[] enemyNum;
    int[] spawnTime;

    final int LEVEL_NUM = 5;

    public LevelScene(int x, int y){
        width = x;
        height = y;

        enemyTimer = 0;

        pieces = new ArrayList<>();
        dispPieces = new ArrayList<>();
        uiObjects = new ArrayList<>();
        tiles = new ArrayList<>();
        enemies = new ArrayList<>();

        enemyNum = new ArrayList[LEVEL_NUM];
        levelLives = new int[LEVEL_NUM];
        spawnTime = new int[LEVEL_NUM];

        for(int i = 0; i < LEVEL_NUM; i++){
            levelLives[i] = 3;
            enemyNum[i] = new ArrayList<>();

            for(int c = 0; c < 5 * i + 5; c++){
                if(i == 2 || i == 3){
                    enemyNum[i].add(1);
                }
                else if(i == 4){
                    enemyNum[i].add(c % 2);
                }
                else {
                    enemyNum[i].add(0);
                }
            }

            if(i == 0 || i == 2){
                spawnTime[i] = 3000;
            }
            else spawnTime[i] = 1500;
        }

        //Create the UI
        uiObjects.add(new UIObject(new Rect(x - 150, 0, x, y), Color.rgb(100, 100, 100)));

        //Add the set button
        uiObjects.add(new UIObject(new Rect(0, y - 250, 150, y), Color.rgb(0, 0, 0), "Set", Color.rgb(255, 255, 255), 100, 90, "AdvanceToEnemy"));
        uiObjects.add(new UIObject(new Rect(x - 150, y - 600, x, y), Color.rgb(100, 100, 100), "Lives: ", Color.rgb(255, 255, 255), 100, 90));

        //Add the display pieces
        dispPieces.add(new DisplayPiece(Game.instance.pieceTemplates.get(0), new Point(x - 75 , 75)));

        //Create the level
        tiles.add(new Tile(new Rect(0, 0, 150, y - 150), new Point(x - 350, (y - 150) / 2), Color.rgb(0, 0, 255), Tile.Direction.down));
        tiles.add(new Tile(new Rect(0, 0, 500, 150), new Point(x - 675, y - 225), Color.rgb(0, 0, 255), Tile.Direction.left));
        tiles.add(new Tile(new Rect(0, 0, 150, y - 300), new Point(x - 850, (y - 300) / 2), Color.rgb(0, 0, 255), Tile.Direction.up));
    }

    @Override
    public void update(long deltaTime){
        enemyTimer += deltaTime;

        if(enemiesLeft > 0){
            if(enemyTimer >= spawnTime[curLevel]){
                SpawnEnemy();
                enemyTimer = 0;
            }
        }

        for(int i = 0; i < pieces.size(); i++){
            pieces.get(i).update(deltaTime);
        }
        for(int i = 0; i < dispPieces.size(); i++){
            dispPieces.get(i).update(deltaTime);
        }
        for(int i = 0; i < tiles.size(); i++){
            tiles.get(i).update(deltaTime);
        }
        if(inGame){
            for(int i = 0; i < enemies.size(); i++){
                enemies.get(i).update(deltaTime);
            }
            if(enemies.size() == 0 && enemiesLeft <= 0){
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

    public void SpawnEnemy(){
        Enemy tmpPiece = new Enemy(Game.instance.enemyTemplates.get((int)enemyNum[curLevel].get(enemyCount)));
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
        ((LevelScene) Game.instance.scenes.get(Game.instance.curScene)).enemies.add(tmpPiece);
        enemiesLeft--;
        enemyCount++;
    }

    public void SpawnEnemy(int number, int tileIndex, float timer, Point point){
        Enemy tmpPiece = new Enemy(Game.instance.enemyTemplates.get(number));
        Tile tile = tiles.get(tileIndex);
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
        tmpPiece.timer = timer;
        tmpPiece.translate(point.x, point.y);
        ((LevelScene) Game.instance.scenes.get(Game.instance.curScene)).enemies.add(tmpPiece);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);

        for(int i = 0; i < tiles.size(); i++){
            tiles.get(i).draw(canvas);
        }

        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(canvas);
        }

        for(int i = 0; i < uiObjects.size(); i++){
            uiObjects.get(i).draw(canvas);
        }

        for(int i = 0; i < pieces.size(); i++){
            pieces.get(i).draw(canvas);
        }

        for(int i = 0; i < dispPieces.size(); i++){
            dispPieces.get(i).draw(canvas);
        }
    }

    public void enemyEnd(Enemy enemy){
        enemies.remove(enemy);
        lives--;
        uiObjects.get(1).updateText("Lives: " + lives);
        if(lives <= 0){
            //YOU LOSE
        }
    }

    public void AdvanceToEnemy(){
        inGame = true;
        enemyCount = 0;
        curLevel++;
        enemiesLeft = enemyNum[curLevel].size();
        SpawnEnemy();
        lives += levelLives[curLevel];
        if(curLevel == 0){
            uiObjects.remove(1);
        }
        else {
            uiObjects.remove(2);
        }
        uiObjects.get(1).updateText("Lives: " + lives);
        enemyTimer = 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        switch(event.getAction()){
            case(MotionEvent.ACTION_DOWN):
                for(int i = 0; i < dispPieces.size(); i++){
                    dispPieces.get(i).onTap((int) event.getX(), (int) event.getY());
                }
                for(int i = 0; i < uiObjects.size(); i++){
                    uiObjects.get(i).onTap((int) event.getX(), (int) event.getY());
                }
                for(int i = 0; i < pieces.size(); i++){
                    pieces.get(i).onTap((int) event.getX(), (int) event.getY());
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

    @Override
    public boolean onDrag(View v, DragEvent event){
        if(event.getAction() == DragEvent.ACTION_DRAG_ENTERED){
            for(int i = 0; i < dispPieces.size(); i++){
                dispPieces.get(i).onDrag((int)event.getX(), (int)event.getY());
            }
        }
        return false;
    }

    public boolean getPlaceableArea(int x, int y){
        for(int i = 0; i < tiles.size(); i++){
            if(tiles.get(i).rect.contains(x, y)) return false;
        }
        return true;
    }
}
