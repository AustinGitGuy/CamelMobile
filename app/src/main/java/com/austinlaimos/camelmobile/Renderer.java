package com.austinlaimos.camelmobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class Renderer extends ImageView {

    RenderCanvas renderCanvas;

    Renderer(Context context){
        super(context);
        renderCanvas = new RenderCanvas();
    }

    public void Update(){
        renderCanvas.Update();
        setImageDrawable(renderCanvas);
    }
}