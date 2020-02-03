package com.austinlaimos.camelmobile;

import android.graphics.Paint;
import android.graphics.Rect;

public class UIObject  {
    Rect rect;
    Paint paint;

    public UIObject(Rect rect, int color){
        this.rect = rect;
        this.paint = new Paint();
        paint.setColor(color);
    }

    public Rect getRect(){ return rect; }

    public Paint getPaint() { return paint; }
}
