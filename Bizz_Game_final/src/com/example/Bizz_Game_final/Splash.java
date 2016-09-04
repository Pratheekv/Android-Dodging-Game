package com.example.Bizz_Game_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        Thread timer = new Thread(){

            public void run(){
                try{
                    sleep(2500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    finish();
                    Intent i = new Intent(Splash.this,Start_Game.class);
                    startActivity(i);
                }
            }

        };timer.start();
    }
}

