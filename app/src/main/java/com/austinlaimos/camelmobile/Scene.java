package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class Scene {

    public void update(long deltaTime){

    }

    public void draw(Canvas canvas){

    }

    public boolean onTouch(View v, MotionEvent event){
        return false;
    }

    public boolean onDrag(View v, DragEvent event){
        return false;
    }
}
