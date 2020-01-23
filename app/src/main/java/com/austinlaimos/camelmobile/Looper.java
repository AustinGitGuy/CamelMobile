package com.austinlaimos.camelmobile;

import android.os.Handler;

//Credit to https://stackoverflow.com/questions/37357686/android-studio-java-update-text-in-the-loop for the looper

public class Looper {
    private Handler handler;
    private boolean paused;

    private int interval;

    private Runnable task = new Runnable(){
        @Override
        public void run(){
            if(!paused){
                runnable.run ();
                Looper.this.handler.postDelayed (this, interval);
            }
        }
    };

    private Runnable runnable;

    public void startTimer(){
        paused = false;
        handler.postDelayed (task, interval);
    }

    public Looper(Runnable runnable, int interval){
        handler = new Handler ();
        this.runnable = runnable;
        this.interval = interval;
        startTimer ();
    }
}