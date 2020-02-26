package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class TitleScene extends Scene {

    List<UIObject> uiObjects;

    int width, height;

    public TitleScene(int x, int y){
        width = x;
        height = y;
        uiObjects = new ArrayList<>();
        uiObjects.add(new UIObject(new Rect(x - 300, 100, x - 150, y - 100), Color.rgb(100, 100, 100), "Start Game", Color.rgb(255, 255, 255), 100, 90, "ToGame"));
        uiObjects.add(new UIObject(new Rect(x - 600, 100, x - 450, y - 100), Color.rgb(100, 100, 100), "Options Menu", Color.rgb(255, 255, 255), 100, 90, "ToOptions"));
    }

    @Override
    public void update(long deltaTime){

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);

        for(int i = 0; i < uiObjects.size(); i++){
            uiObjects.get(i).draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        switch(event.getAction()) {
            case (MotionEvent.ACTION_DOWN):
                for(int i = 0 ; i < uiObjects.size(); i++){
                    uiObjects.get(i).onTap((int) event.getX(), (int) event.getY());
                }
                return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event){
        return false;
    }
}
