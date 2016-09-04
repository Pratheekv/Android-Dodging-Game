package com.example.Bizz_Game_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

/**
 * Created by Prateek Kumar on 9/6/2015.
 */
public class Game_Over extends Activity {
    public String TAG="test2";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_over);

        Intent i = getIntent();
        Long initial_time = i.getLongExtra("key",0);

        Button replay=(Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Game_Over.this,Start_Game.class);
                startActivity(i);
            }
        });

        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Long final_Time=SystemClock.uptimeMillis();
        Log.d(TAG,""+final_Time);
        Long elapsed = (final_Time-initial_time)/1000;
        TextView time = (TextView)findViewById(R.id.score);
        time.setText("Score:"+elapsed);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
