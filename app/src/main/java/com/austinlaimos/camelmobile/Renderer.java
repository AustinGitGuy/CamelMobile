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

    List<UIObject> ui;

    int width, height;

    DragShadowBuilder shadow;

    Renderer(Context context, int x, int y){
        super(context);

        width = x;
        height = y;

        instance = this;

        pieces = new ArrayList<>();
        dispPieces = new ArrayList<>();
        ui = new ArrayList<>();
        tiles = new ArrayList<>();
        enemies = new ArrayList<>();

        shadow = new DragShadowBuilder(this);

        //Create the UI
        ui.add(new UIObject(new Rect(x - 150, 0, x, y), Color.rgb(100, 100, 100)));

        //Add a dummy pieces offscreen
        pieces.add(new Piece(new Rect(0, 0, 100, 100), new Point(-100, -100), Color.rgb(255, 0, 0)));

        //Add the display pieces
        dispPieces.add(new DisplayPiece(pieces.get(0), new Point(x - 75 , 75)));

        //Create the level
        tiles.add(new Tile(new Rect(0, 0, 150, y - 100), new Point(x - 400, (y - 100) / 2), Color.rgb(0, 0, 255)));

        tiles.add(new Tile(new Rect(0, 0, 150, y - 100), new Point(x - 900, (y - 100) / 2), Color.rgb(0, 0, 255)));

        tiles.add(new Tile(new Rect(0, 0, 350, 150), new Point(x - 650, y - 175), Color.rgb(0, 0, 255)));

        //Add the dummy enemies offscreen
        pieces.add(new Piece(new Rect(0, 0, 100, 100), new Point(-100, -100), Color.rgb(0, 255, 0)));

        setFocusable(true);
        setWillNotDraw(false);

        setOnTouchListener(this);
        setOnDragListener(this);
    }

    public void update(){
        for(int i = 0; i < pieces.size(); i++){
            pieces.get(i).update();
        }
        for(int i = 0; i < dispPieces.size(); i++){
            dispPieces.get(i).update();
        }
        for(int i = 0; i < tiles.size(); i++){
            tiles.get(i).update();
        }
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        for(int i = 0; i < ui.size(); i++){
            canvas.drawRect(ui.get(i).getRect(), ui.get(i).getPaint());
        }

        for(int i = 0; i < pieces.size(); i++){
            pieces.get(i).draw(canvas);
        }

        for(int i = 0; i < dispPieces.size(); i++){
            dispPieces.get(i).draw(canvas);
        }

        for(int i = 0; i < tiles.size(); i++){
            tiles.get(i).draw(canvas);
        }

        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(canvas);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View v, MotionEvent event){
        //startDragAndDrop(null, shadow, v, 0);

        switch(event.getAction()){
            case(MotionEvent.ACTION_DOWN):
                for(int i = 0; i < dispPieces.size(); i++){
                    dispPieces.get(i).onTap((int) event.getX(), (int) event.getY());
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
}