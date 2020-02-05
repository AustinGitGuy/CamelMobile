package com.austinlaimos.camelmobile;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
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

    public List<Piece> piece;
    public List<Piece> dispPiece;

    List<UIObject> ui;

    int width, height;

    DragShadowBuilder shadow;

    Renderer(Context context, int x, int y){
        super(context);

        width = x;
        height = y;

        instance = this;

        piece = new ArrayList<Piece>();
        dispPiece = new ArrayList<Piece>();
        ui = new ArrayList<UIObject>();

        shadow = new DragShadowBuilder(this);

        ui.add(new UIObject(new Rect(x - 150, 0, x, y), Color.rgb(100, 100, 100)));
        piece.add(new Piece(new Rect(100, 100, 200, 200), new Point(-100, -100), Color.rgb(255, 0, 0)));
        dispPiece.add(new DisplayPiece(piece.get(0), new Point(x - 75 , 75)));

        setFocusable(true);
        setWillNotDraw(false);

        setOnTouchListener(this);
        setOnDragListener(this);
    }

    public void update(){
        for(int i = 0; i < piece.size(); i++){
            piece.get(i).update();
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(Color.GREEN);

        for(int i = 0; i < ui.size(); i++){
            canvas.drawRect(ui.get(i).getRect(), ui.get(i).getPaint());
        }

        for(int i = 0; i < piece.size(); i++){
            piece.get(i).draw(canvas);
        }

        for(int i = 0; i < dispPiece.size(); i++){
            dispPiece.get(i).draw(canvas);
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

    public boolean getPlaceableArea(Point point){

        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event){
        if(event.getAction() == DragEvent.ACTION_DRAG_ENTERED){
            for(int i = 0; i < dispPiece.size(); i++){
                dispPiece.get(i).onDrag((int)event.getX(), (int)event.getY());
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View v, MotionEvent event){
        startDragAndDrop(null, shadow, v, 0);

        switch(event.getAction()){
            case(MotionEvent.ACTION_DOWN):
                for(int i = 0; i < dispPiece.size(); i++){
                    dispPiece.get(i).onTap((int) event.getX(), (int) event.getY());
                }
                return true;
            case(MotionEvent.ACTION_MOVE):
                for(int i = 0; i < dispPiece.size(); i++){
                    dispPiece.get(i).onDrag((int) event.getX(), (int) event.getY());
                }
                return true;
        }

        return false;
    }
}