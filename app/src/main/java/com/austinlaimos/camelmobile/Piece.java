package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Piece extends Object {

    //A pieces has a rectangle (to display) and a Paint (to color code it)
    Rect rect;
    Paint paint;

    int range;

    public Piece(){

    }

    public Piece(Piece piece){
        point = new Point(piece.point);
        rect = new Rect(piece.rect);
        paint = new Paint(piece.paint);
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
    }

    public Piece(Rect rectangle, Point point, int color, int range){
        rect = rectangle;
        paint = new Paint();
        paint.setColor(color);
        this.point = point;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
        this.range = range;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(rect, paint);
    }

    @Override
    public void update(long deltaTime){
        for(int i = 1; i < Renderer.instance.enemies.size(); i++){
            if(getDistance(point, Renderer.instance.enemies.get(i).point) <= range){
                paint.setColor(Color.rgb(50, 50, 50));
            }
        }
    }

    @Override
    public void onTap(int x, int y){

    }

    public void onDrag(int x, int y){

    }

    public void translate(int x, int y){
        this.point.x = x;
        this.point.y = y;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
    }

    double getDistance(Point a, Point b){
        return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
    }

}
