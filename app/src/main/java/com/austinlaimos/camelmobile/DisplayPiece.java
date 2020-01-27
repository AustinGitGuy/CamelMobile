package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class DisplayPiece<T> extends Piece {

    Class<T> piece;

    public DisplayPiece(Class<T> piece, Point point){
        //TODO: Figure out how to use member variables when using templates
        this.piece = piece;
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
}
