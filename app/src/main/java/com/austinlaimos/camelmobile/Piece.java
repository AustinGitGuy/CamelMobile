package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Piece extends Object {

    //A piece has a radius (to display the circle) and a Paint (to color code it)
    float radius;
    Paint paint;
    Paint attackPaint;

    Paint linePaint;

    Paint hollowPaint;

    Point attackPoint;

    int range;

    float damage;

    boolean attacking = false, tapped;

    public Piece(){

    }

    public Piece(Piece piece){
        point = new Point(piece.point);
        radius = piece.radius;
        paint = new Paint(piece.paint);
        this.range = piece.range;
        attackPaint = new Paint(piece.attackPaint);
        hollowPaint = new Paint(piece.hollowPaint);
        linePaint = new Paint(piece.linePaint);
        this.damage = piece.damage;
    }

    public Piece(float radius, Point point, int color, int range, float damage){
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
        this.point = point;
        this.range = range;
        attackPaint = new Paint();
        attackPaint.setColor(Color.rgb(50, 50, 50));
        linePaint = new Paint();
        linePaint.setColor(paint.getColor());
        linePaint.setStrokeWidth(10);
        hollowPaint = new Paint();
        hollowPaint.setColor(paint.getColor());
        hollowPaint.setStyle(Paint.Style.STROKE);
        hollowPaint.setStrokeWidth(5);
        this.damage = damage;
    }

    @Override
    public void draw(Canvas canvas){
        if(attacking){
            canvas.drawCircle(point.x, point.y, radius, attackPaint);
            canvas.drawLine(point.x, point.y, attackPoint.x, attackPoint.y, linePaint);
        }
        else {
            canvas.drawCircle(point.x, point.y, radius, paint);
        }

        if(tapped){
            canvas.drawCircle(point.x, point.y, range, hollowPaint);
        }
    }

    @Override
    public void update(long deltaTime){
        for(int i = 0; i < ((LevelScene) Game.instance.getCurScene()).enemies.size(); i++){
            Enemy enemy = ((LevelScene) Game.instance.getCurScene()).enemies.get(i);
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
        if(tapped){
            if(getDistance(point, new Point(x, y)) <= radius){

            }
            tapped = false;
        }
        else {
            if(getDistance(point, new Point(x, y)) <= radius){
                tapped = !tapped;
            }
        }
    }

    public void onDrag(int x, int y){

    }

    public void translate(int x, int y){
        this.point.x = x;
        this.point.y = y;
    }

    double getDistance(Point a, Point b){
        return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
    }

}
