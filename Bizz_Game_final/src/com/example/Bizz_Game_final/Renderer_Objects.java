package com.example.Bizz_Game_final;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Prateek Kumar on 9/7/2015.
 */
public class Renderer_Objects implements GLSurfaceView.Renderer {

    Context context;
    // Variables for Rendering Circles
    public int points = 360;
    public float vertices[] = {0.0f, 0.0f, 0.0f};
    public FloatBuffer vertBuff;

    // variables for boundaries
    public float bX=4.5f,bY=8f;

    // Direction vectors for enemies
    public int a=1,b=1;
    public int c=-2,d=2;
    public int e=-3,f=-3;
    public int g=4,h=-4;

    // Radius of Player and Enemies
    public float radius_player=0.4f;
    public float radius_enemy=0.6f;
    public float radius_life=0.7f;

    public float speed=0.1f;                         // Speed of enemies

    //Initial Positions of the Player and Enemies
    public float prX=0,prY=0;                       // Center of the Player
    public float prX1=1,prY1=1;                     // Enemy 1
    public float prX2=-3,prY2=-2;                   // Enemy 2
    public float prX3=-4,prY3=7;                    // Enemy 3
    public float prX4=3,prY4=-5;                    // Enemy 4

    public GameScreen mGame;
    public CountDownTimer countDownTimer;
    public long countDown=60000;

    public Long initTime;           // Variable for storing the initial time
    public String TAG="test";       // For printing Log

    public Boolean set_life;        // Boolean for Rendering Life object
    public long diff;

    public Boolean touch_life=false;
    public Boolean touch_enemy=false;
    public Boolean out_of_bound=false;


    public Renderer_Objects(Context context) {
        this.context=context;
        mGame=new GameScreen();

        countDownTimer=new CountDownTimer(countDown,1000) {
            @Override
            public void onTick(long l) {
                diff = countDown-l;
                // setting life for every 6 seconds and duration of life is 5 seconds
                if((diff>=6000&&diff<=11000) || (diff>=170000&&diff<=22000) || (diff>=28000&&diff<=33000) || (diff>=39000&&diff<=44000)){
                    set_life=true;
                }
                else{set_life=false;touch_life=false;}
            }
            @Override
            public void onFinish() {
                set_life=false;
            }
        }.start();

        countDownTimer=new CountDownTimer(countDown,5000) {
            @Override
            public void onTick(long l) {
               radius_enemy+=0.2*radius_enemy;
                if(bX>0){bX-=(float)0.02*bX;}
                if(bY>0){bY-=(float)0.02*bY;}
                if(bX<0){bX+=(float)0.02*bX;}
                if(bY<0){bY+=(float)0.02*bY;}

            }
            @Override
            public void onFinish() {
                set_life=false;
            }
            }.start();


    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        SystemClock.setCurrentTimeMillis(0);
        initTime = SystemClock.uptimeMillis();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

    }

    public void onDrawFrame(final GL10 gl) {


        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glLineWidth(3.0f);
        gl.glTranslatef(0, 0, -4);
        gl.glColor4f(0, 0, 0, 1);
        gl.glScalef(0.2f,0.2f, 0.2f);

        setMember(prX, prY,radius_player);
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0);
        gl.glColor4f(0.0f, 0.5f, 1.0f, 0.5f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuff);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, points / 2);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glPopMatrix();

        if(set_life){setMember(3, 3,radius_life);
            gl.glPushMatrix();
            gl.glTranslatef(0, 0, 0);
            gl.glColor4f(0.6f, 0.8f, 0.196078f, 0.0f);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuff);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, points / 2);
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glPopMatrix();

            if(distance_check(3,3,prX,prY)){touch_life=true;}

        }

        if(!touch_life && !touch_enemy) {
            // Drawing Enemies and Setting Random Movements
            //Enemy 1
            if (prX1 < bX && prX1 > -bX && prY1 < bY && prY1 > -bY) {
                prX1 = prX1 + a * speed;
                prY1 = prY1 + b * speed;

            } else if (prX1 + radius_enemy > bX) {
                a = -1;
                prX1 = prX1 + a * speed;
                prY1 = prY1 + b * speed;
            } //right bound 1st hit
            else if (prY1 + radius_enemy > bY) {
                b = -1;
                prX1 = prX1 + a * speed;
                prY1 = prY1 + b * speed;
            }//top bound
            else if (prX1 - radius_enemy < -bX) {
                a = 1;
                prX1 = prX1 + a * speed;
                prY1 = prY1 + b * speed;
            }//left bound
            else if (prY1 - radius_enemy < -bY) {
                b = 1;
                prX1 = prX1 + a * speed;
                prY1 = prY1 + b * speed;
            }//bottom bound
        }
        setMember(prX1, prY1, radius_enemy);
        draw(gl);

        if(!touch_life && !touch_enemy ) {
            //Enemy 2
            if (prX2 < bX && prX2 > -bX && prY2 < bY && prY2 > -bY) {
                prX2 = prX2 + c * speed;
                prY2 = prY2 + d * speed;
            } else if (prX2 + radius_enemy > bX) {
                c = -1;
                prX2 = prX2 + c * speed;
                prY2 = prY2 + d * speed;
            } //right bound 1st hit
            else if (prY2 + radius_enemy > bY) {
                d = -1;
                prX2 = prX2 + c * speed;
                prY2 = prY2 + d * speed;
            }//top bound
            else if (prX2 - radius_enemy < -bX) {
                c = 1;
                prX2 = prX2 + c * speed;
                prY2 = prY2 + d * speed;
            }//left bound
            else if (prY2 -radius_enemy < -bY) {
                d = 1;
                prX2 = prX2 + c * speed;
                prY2 = prY2 + d * speed;
            }//bottom bound
        }
        setMember(prX2, prY2, radius_enemy);
        draw(gl);
        if(!touch_life && !touch_enemy) {
            //Enemy 3
            if (prX3 < bX && prX3 > -bX && prY3 < bY && prY3 > -bY) {
                prX3 = prX3 + e * speed;
                prY3 = prY3 + f * speed;
            } else if (prX3 + radius_enemy > bX) {
                e = -1;
                prX3 = prX3 + e * speed;
                prY3 = prY3 + f * speed;
            } //right bound 1st hit
            else if (prY3 + radius_enemy> bY) {
                f = -1;
                prX3 = prX3 + e * speed;
                prY3 = prY3 + f * speed;
            }//top bound
            else if (prX3 - radius_enemy< -bX) {
                e = 1;
                prX3 = prX3 + e * speed;
                prY3 = prY3 + f * speed;
            }//left bound
            else if (prY3 - radius_enemy < -bY) {
                f = 1;
                prX3 = prX3 + e * speed;
                prY3 = prY3 + f * speed;
            }//bottom bound
        }
        setMember(prX3, prY3, radius_enemy);
        draw(gl);
        if(!touch_life &&
                !touch_enemy) {
            //Enemy 4
            if (prX4 < bX && prX4 > -bX && prY4 < bY && prY4 > -bY) {
                prX4 = prX4 + g * speed;
                prY4 = prY4 + h * speed;
            } else if (prX4 + radius_enemy > bX) {
                g = -1;
                prX4 = prX4 + g * speed;
                prY4 = prY4 + h * speed;
            } //right bound 1st hit
            else if (prY4 + radius_enemy> bY) {
                h = -1;
                prX4 = prX4 + g * speed;
                prY4 = prY4 + h * speed;
            }//top bound
            else if (prX4 - radius_enemy< -bX) {
                g = 1;
                prX4 = prX4 + g * speed;
                prY4 = prY4 + h * speed;
            }//left bound
            else if (prY4 - radius_enemy < -bY) {
                h = 1;
                prX4 = prX4 + g * speed;
                prY4 = prY4 + h * speed;
            }//bottom bound
        }
        setMember(prX4, prY4, radius_enemy);
        draw(gl);

        if (distance_check(prX, prY, prX1, prY1) || distance_check(prX, prY, prX2, prY2) ||
                distance_check(prX, prY, prX3, prY3) || distance_check(prX, prY, prX4, prY4) || out_of_bound) {
           Log.d(TAG, "" + initTime);
          Intent i = new Intent(context, Game_Over.class);
            i.putExtra("key", initTime);
            context.startActivity(i);
            touch_enemy=true;
        }
    }
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    public void setMember(float x,float y,float radius) {

        vertices = new float[(points + 1) * 3];
        for (int i = 0; i < (points + 1) * 3; i += 3) {
            double rad = (i * 360/ points * 3) * (3.14 / 180);
            vertices[i] = radius*(float) Math.cos(rad) + x;
            vertices[i + 1] =  radius*(float) Math.sin(rad) + y;
            vertices[i + 2] = 0;
        }
        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        bBuff.order(ByteOrder.nativeOrder());
        vertBuff = bBuff.asFloatBuffer();
        vertBuff.put(vertices);
        vertBuff.position(0);



    }
    public void draw(GL10 gl){
        gl.glPushMatrix();
        gl.glTranslatef(0, 0, 0);
        gl.glColor4f(1,0.43f, 0.78f, 0.0f);
        //  gl.glScalef(0.5f,0.5f, 0.5f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuff);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, points / 2);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glPopMatrix();
    }

    public boolean distance_check(float x1,float y1,float x2,float y2){
        float dist;
        dist=(float)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        if(dist<=radius_enemy+radius_player){return true;}
        else return false;

    }
}
