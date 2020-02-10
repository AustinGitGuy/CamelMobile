package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Tile extends Object {
    Rect rect;
    Paint paint;

    public Tile(Rect rectangle, Point point, int color){
        rect = rectangle;
        paint = new Paint();
        paint.setColor(color);
        this.point = point;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(rect, paint);
    }
}
