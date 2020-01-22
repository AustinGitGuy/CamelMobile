
package com.AndroidTest;

import android.graphics.drawable.*;
import android.graphics.*;

public class Renderer extends Drawable {

	private final Paint blue;

	public Renderer(){
		blue = new Paint();
		blue.setARGB(255, 0, 0, 255);
	}

	@Override
	public void draw(Canvas canvas){
		int width = getBounds().width();
		int height = getBounds().height();

		canvas.drawRect(5, 5, 10, 10, blue);
	}

	@Override
	public void setAlpha(int alpha){

	}

	@Override
	public void setColorFilter(ColorFilter filter){

	}

	@Override
	public int getOpacity(){
		return PixelFormat.OPAQUE;
	}
}
