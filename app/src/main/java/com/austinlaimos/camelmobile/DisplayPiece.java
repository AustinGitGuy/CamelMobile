package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class DisplayPiece extends Piece {

    Piece piece;

    boolean tapped = false;

    public DisplayPiece(Piece piece, Point point){
        this.piece = piece;
        this.point = point;
        this.rect = new Rect(this.piece.rect.left, this.piece.rect.top, this.piece.rect.right, this.piece.rect.bottom);
        this.paint = new Paint(this.piece.paint);
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
    public void onTap(int x, int y){
        if(tapped){
            if(rect.contains(x, y)){
                tapped = false;
            }
            else {
                Piece tmpPiece = new Piece(piece);
                tmpPiece.translate(x, y);
                Renderer.instance.piece.add(tmpPiece);
                tapped = false;
            }
        }
        else {
            if(rect.contains(x, y)){
                tapped = true;
            }
        }
    }
}
