package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ShooterPiece extends Piece {

    public ShooterPiece(Rect rectangle, Point point, int color){
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

    @Override
    public void update(){

    }

    @Override
    public void onTap(){

    }

    public void translate(int x, int y){
        this.point.x = x;
        this.point.y = y;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
    }
}
