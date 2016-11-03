package com.notrace.library.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by notrace on 2016/11/1.
 */

public class SearchView extends View{

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public static enum  State{
        NONE,
        STARTING,
        SEARCHING,
        ENDING
    }
    private State mCurrrentState = State.NONE;

    private Path path_search;
    private Path path_circle;

    private PathMeasure mPathMeasure;

    private int defaultDuration = 2000;
    private ValueAnimator mStartAnimator;
    private ValueAnimator mSearchAnimator;
    private ValueAnimator mEndingAnimator;

    private float mAnimatorValue = 0;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    private Handler mAnimatorHandler;
    private boolean isOver = false;

    private int count = 0 ;



    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {

        initPaint();
        initPath();
        initListener();
        initHandler();
        initAnimator();


    }

    public void start()
    {
        mCurrrentState =State.STARTING;
        mStartAnimator.start();
    }
    public void search(){
        mCurrrentState =State.SEARCHING;
        mSearchAnimator.start();
    }
    public void end(){
        mCurrrentState =State.ENDING;
        mEndingAnimator.start();
    }
    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);

    }
    private void initPath()
    {
        path_search = new Path();
        path_circle=new Path();

        mPathMeasure =new PathMeasure();

        RectF oval1 =new RectF(-50,-50,50,50);
        path_search.addArc(oval1,45,359.9f);

        RectF oval2 = new RectF(-100,-100,100,100);
        path_circle.addArc(oval2,45,-359.9f);

        float []pos=new float[2];
        mPathMeasure.setPath(path_circle,false);
        mPathMeasure.getPosTan(0,pos,null);


        path_search.lineTo(pos[0],pos[1]);

    }

    private void initListener()
    {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }
    private void initHandler()
    {
        mAnimatorHandler =new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrrentState)
                {
                    case STARTING:
                        isOver =false;
                        mCurrrentState =State.STARTING;
                        mStartAnimator.removeAllListeners();
                        mSearchAnimator.start();
                        break;

                    case SEARCHING:

                        if(!isOver)
                        {
                            mSearchAnimator.start();
                            count++;
                            if (count>2)
                            {
                                isOver =true;
                            }
                        }else
                        {
                            mCurrrentState = State.ENDING;
                            mEndingAnimator.start();
                        }
                        break;
                    case  ENDING:
                        mCurrrentState= State.ENDING;
                        break;
                }
            }
        };
    }

    private void initAnimator(){
        mStartAnimator =ValueAnimator.ofFloat(0,1).setDuration(defaultDuration);
        mSearchAnimator =ValueAnimator.ofFloat(0 ,1).setDuration(defaultDuration);
        mEndingAnimator =ValueAnimator.ofFloat(1,0).setDuration(defaultDuration);

        mStartAnimator.addUpdateListener(mUpdateListener);
        mSearchAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartAnimator.addListener(mAnimatorListener);
        mSearchAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight= h;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSearch(canvas);

    }

    private void drawSearch(Canvas canvas){
        mPaint.setColor(Color.WHITE);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawColor(Color.BLUE);

        switch (mCurrrentState){
            case NONE:
                canvas.drawPath(path_search,mPaint);
                break;

            case STARTING:
                mPathMeasure.setPath(path_search,false);
                Path dst = new Path();
                mPathMeasure.getSegment(mPathMeasure.getLength() * mAnimatorValue,mPathMeasure.getLength(),dst,true);
                canvas.drawPath(dst,mPaint);

                break;
            case SEARCHING:
                mPathMeasure.setPath(path_circle,false);
                Path dst2 =new Path();
                float stop = mPathMeasure.getLength()*mAnimatorValue;
                float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue -0.5))*200f));
                mPathMeasure.getSegment(start,stop,dst2,true);
                canvas.drawPath(dst2,mPaint);
                break;
            case ENDING:

                mPathMeasure.setPath(path_search,false);
                Path dst3 = new Path();
                mPathMeasure.getSegment(mPathMeasure.getLength()*mAnimatorValue,mPathMeasure.getLength(), dst3,true);
                canvas.drawPath(dst3,mPaint);
                break;
        }

    }
}
