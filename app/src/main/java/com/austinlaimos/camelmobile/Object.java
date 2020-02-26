package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Point;

public class Object {

    //An object just has a point in space
    protected Point point;

    public void draw(Canvas canvas){

    }

    public void update(long deltaTime){

    }

    public void onTap(int x, int y){

    }

    public void translate(int x, int y){

    }

    public Point getPoint(){
        return point;
    }
}
