package com.example.Bizz_Game_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Prateek Kumar on 9/6/2015.
 */
public class GameScreen extends Activity {

    private OpenGLView mOpenGLView;
    public int clicks;
    public CountDownTimer myTimer;
    private int count=2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mOpenGLView = new OpenGLView(this);
        setContentView(R.layout.game_screen);

        myTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {
                long y = (60000-l)/1000;
                TextView tv =(TextView)findViewById(R.id.score);
                tv.setText("Time:"+y);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();


        LinearLayout linearLayout =(LinearLayout) findViewById(R.id.Layout);
        linearLayout.addView(mOpenGLView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout layoutTop = (RelativeLayout)findViewById(R.id.layoutTop);
        layoutTop.bringToFront();

        final Button pause = (Button)findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                count = ++clicks;

                if (count%2==1) {
                    mOpenGLView.onPause();
                    pause.setText("Resume");

                    TextView tv = (TextView) findViewById(R.id.VIEW);
                    tv.setText("PAUSED");
                }

                if (count%2==0) {
                    mOpenGLView.onResume();
                    pause.setText("Pause");
                    TextView tv = (TextView) findViewById(R.id.VIEW);
                    tv.setText("");
                }

            }
        });

    }

   @Override
    protected void onDestroy() {
        super.onDestroy();
        moveTaskToBack(true);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOpenGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOpenGLView.onResume();

    }
}
