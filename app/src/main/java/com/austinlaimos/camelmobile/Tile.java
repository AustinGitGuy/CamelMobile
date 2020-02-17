package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Tile extends Object {
    enum Direction {up, down, left, right};

    Rect rect;
    Paint paint;

    Direction dir;

    public Tile(Rect rectangle, Point point, int color, Direction direction){
        rect = rectangle;
        paint = new Paint();
        paint.setColor(color);
        this.point = point;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
        dir = direction;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(rect, paint);
    }
}
