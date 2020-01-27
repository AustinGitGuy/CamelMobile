package com.austinlaimos.camelmobile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

@SuppressLint("AppCompatCustomView")
public class Renderer extends SurfaceView implements SurfaceHolder.Callback {

    List<ShooterPiece> piece;
    List<DisplayPiece> dispPiece;

    Renderer(Context context){
        super(context);

        piece.add(new ShooterPiece(new Rect(100, 100, 200, 200), new Point(150, 150), Color.rgb(255, 0, 0)));

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

        for(int i = 0; i < piece.size(); i++){
            piece.get(i).draw(canvas);
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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                break;
        }

        return true;
    }
}