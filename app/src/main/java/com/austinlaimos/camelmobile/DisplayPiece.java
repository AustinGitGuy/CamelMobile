package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class DisplayPiece extends Piece {

    Piece piece;

    boolean tapped = false;

    public DisplayPiece(Piece piece, Point point){
        this.piece = piece;
        this.point = point;
        this.radius = piece.radius;
        this.paint = new Paint(this.piece.paint);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawCircle(point.x, point.y, radius, paint);
    }

    @Override
    public void update(long deltaTime){

    }

    @Override
    public void onTap(int x, int y){
        if(tapped){
            if(getDistance(point, new Point(x, y)) <= radius){
                tapped = false;
            }
            else {
                if(((LevelScene)Renderer.instance.getCurScene()).getPlaceableArea(x, y)){
                    Piece tmpPiece = new Piece(piece);
                    tmpPiece.translate(x, y);
                    ((LevelScene)Renderer.instance.getCurScene()).pieces.add(tmpPiece);
                    tapped = false;
                }
            }
        }
        else {
            if(getDistance(point, new Point(x, y)) <= radius){
                tapped = true;
            }
        }
    }

    @Override
    public void onDrag(int x, int y){
        if(getDistance(point, new Point(x, y)) <= radius){
            translate(x, y);
        }
    }

    double getDistance(Point a, Point b){
        return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
    }
}
