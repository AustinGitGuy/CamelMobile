package com.austinlaimos.camelmobile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class Renderer extends SurfaceView implements SurfaceHolder.Callback {

    public static Renderer instance;

    public List<Piece> piece;
    public List<Piece> dispPiece;

    List<UIObject> ui;

    int width, height;

    Renderer(Context context, int x, int y){
        super(context);

        width = x;
        height = y;

        instance = this;

        piece = new ArrayList<Piece>();
        dispPiece = new ArrayList<Piece>();
        ui = new ArrayList<UIObject>();

        ui.add(new UIObject(new Rect(0, 0, x, 200), Color.rgb(100, 100, 100)));
        piece.add(new Piece(new Rect(100, 100, 200, 200), new Point(-100, -100), Color.rgb(255, 0, 0)));
        dispPiece.add(new DisplayPiece(piece.get(0), new Point(100, 100)));

        setFocusable(true);
        setWillNotDraw(false);
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
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                for(int i = 0; i < dispPiece.size(); i++){
                    dispPiece.get(i).onTap((int)event.getX(), (int)event.getY());
                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }

        return true;
    }
}