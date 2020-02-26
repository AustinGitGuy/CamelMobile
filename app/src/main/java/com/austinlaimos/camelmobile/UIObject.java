package com.austinlaimos.camelmobile;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UIObject  {

    Rect rect;
    Paint bgPaint;
    Paint txtPaint;
    float textSize;
    String text;
    int rotate;
    String funcName;
    Method method;

    boolean methodCalls = false;

    boolean visible = true;

    public UIObject(Rect rect, int bgColor){
        this.rect = rect;
        this.bgPaint = new Paint();
        this.txtPaint = new Paint();
        text = new String();
        bgPaint.setColor(bgColor);

    }

    public UIObject(Rect rect, int bgColor, String txt, int txtColor, float txtSize, int rotation, String funcName){
        this.rect = rect;
        this.bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        this.txtPaint = new Paint();
        this.text = txt;
        this.textSize = txtSize;
        this.rotate = rotation;
        this.funcName = funcName;
        txtPaint.setColor(txtColor);
        txtPaint.setTextSize(txtSize);
        txtPaint.setStyle(Paint.Style.FILL);
        methodCalls = true;
    }

    public UIObject(Rect rect, int bgColor, String txt, int txtColor, float txtSize, int rotation){
        this.rect = rect;
        this.bgPaint = new Paint();
        bgPaint.setColor(bgColor);
        this.txtPaint = new Paint();
        this.text = txt;
        this.textSize = txtSize;
        this.rotate = rotation;
        txtPaint.setColor(txtColor);
        txtPaint.setTextSize(txtSize);
        txtPaint.setStyle(Paint.Style.FILL);
    }

    public void draw(Canvas canvas){
        if(!visible) return;
        canvas.drawRect(rect, bgPaint);
        canvas.save();
        canvas.rotate(rotate, rect.left + rect.width() / 4, rect.top + rect.height() / 4);
        canvas.drawText(text, rect.left + rect.width() / 4, rect.top + rect.height() / 4, txtPaint);
        canvas.restore();
    }

    public void updateText(String newText){
        text = newText;
    }

    public void onTap(int x, int y){
        if(!visible) return;
        if(rect.contains(x, y)){
            if(methodCalls){
                Method[] methods = Game.class.getMethods();
                for(Method m : methods){
                    if(funcName.equals(m.getName())){
                        try {
                            m.invoke(null);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }
}
