package com.austinlaimos.camelmobile;

import android.graphics.*;
import android.graphics.drawable.*;

public class RenderCanvas extends Drawable {

    private final Paint paint;

    public RenderCanvas(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void draw(Canvas canvas){
        int width = getBounds().width();
        int height = getBounds().height();

        canvas.drawRect(0, 0, width / 2, height / 2, paint);
    }

    @Override
    public void setAlpha(int alpha){

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter){

    }

    @Override
    public int getOpacity(){
        return PixelFormat.OPAQUE;
    }

    public void Update(){

    }
}
