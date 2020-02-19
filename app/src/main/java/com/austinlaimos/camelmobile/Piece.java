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
    Paint attackPaint;

    Paint linePaint;

    Point attackPoint;

    int range;

    float damage;

    boolean attacking = false;

    public Piece(){

    }

    public Piece(Piece piece){
        point = new Point(piece.point);
        rect = new Rect(piece.rect);
        paint = new Paint(piece.paint);
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
        this.range = piece.range;
        attackPaint = new Paint(piece.attackPaint);
        linePaint = new Paint(piece.linePaint);
        this.damage = piece.damage;
    }

    public Piece(Rect rectangle, Point point, int color, int range, float damage){
        rect = rectangle;
        paint = new Paint();
        paint.setColor(color);
        this.point = point;
        rect.set(point.x - rect.width() / 2, point.y - rect.height() / 2, point.x + rect.width() / 2, point.y + rect.height() / 2);
        this.range = range;
        attackPaint = new Paint();
        attackPaint.setColor(Color.rgb(50, 50, 50));
        linePaint = new Paint();
        linePaint.setColor(paint.getColor());
        linePaint.setStrokeWidth(10);
        this.damage = damage;
    }

    @Override
    public void draw(Canvas canvas){

        if(attacking){
            canvas.drawRect(rect, attackPaint);
            canvas.drawLine(point.x, point.y, attackPoint.x, attackPoint.y, linePaint);
        }
        else {
            canvas.drawRect(rect, paint);
        }
    }

    @Override
    public void update(long deltaTime){
        for(int i = 1; i < Renderer.instance.enemies.size(); i++){
            Enemy enemy = Renderer.instance.enemies.get(i);
            double dist = getDistance(point, enemy.point);
            if(dist <= range){
                attackPoint = enemy.point;
                enemy.tickDamage(damage);
                attacking = true;
                return;
            }
        }
        attacking = false;
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
