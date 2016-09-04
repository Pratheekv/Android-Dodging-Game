package com.example.Bizz_Game_final;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Prateek Kumar on 9/7/2015.
 */
public class OpenGLView extends GLSurfaceView {
    private Renderer_Objects mRenderer;
    private float mPreviousX;
    private float mPreviousY;
    private float speed_player=0.3f;


    public OpenGLView(Context context) {
        super(context);
        mRenderer = new Renderer_Objects(context);
        this.setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {

            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                if (Math.abs(dx) > Math.abs(dy) && dx > 0) {
                    //RIGHT BOUNDARY
                    if(mRenderer.prX>mRenderer.bX){
                        mRenderer.out_of_bound=true;
                    }
                    mRenderer.prX += speed_player;
                }else if (Math.abs(dx) > Math.abs(dy) && dx < 0) {
                    //LEFT BOUNDARY
                    if (mRenderer.prX < -mRenderer.bX) {
                        mRenderer.out_of_bound = true;
                    }
                    mRenderer.prX -= speed_player;
                } else if (Math.abs(dx) < Math.abs(dy) && dy > 0) {
                    // BOTTOM BOUNDARY
                            if(mRenderer.prY<-mRenderer.bY){
                                 mRenderer.out_of_bound=true;
                            }
                             mRenderer.prY -= speed_player;
                } else if (Math.abs(dx) < Math.abs(dy) && dy < 0) {
                    // TOP BOUNDARY
                            if(mRenderer.prY>mRenderer.bY){
                                mRenderer.out_of_bound=true;
                            }
                            mRenderer.prY += speed_player;
                }
                requestRender();
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
