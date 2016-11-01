package com.notrace.library;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by notrace on 2016/11/1.
 *
 * used in bullet screen
 */

public class ScrollTextView extends TextView {

    private Scroller mScroller;
    private int mRandDuration = 100;
    private int mXPaused = 0;
    private boolean mPaused = true;



    public ScrollTextView(Context context) {
        this(context,null);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.textViewStyle);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);

    }

    /**
     * begin to scroll
     */
    public void startScroll()
    {
        mXPaused = -1 * getWidth();
        mPaused = true;
        resumeScroll();
    }

    /**
     * resume scroll from the pausing point
     */
    public void resumeScroll() {
        if(!mPaused)
            return;

        setHorizontallyScrolling(true);

        mScroller =new Scroller(this.getContext(),new LinearInterpolator());
        setScroller(mScroller);

        int scrolllingLenth=calculateScrollingLen();
        int distance = scrolllingLenth -(getWidth() + mXPaused);
        int duration =(new Double(mRandDuration * distance *1.00f /scrolllingLenth)).intValue();
        setVisibility(VISIBLE);
        mScroller.startScroll(mXPaused,0,distance,0,duration);
        mPaused = false;


    }

    private int calculateScrollingLen() {
        TextPaint tp = getPaint();
        Rect rect=new Rect();
        String text = getText().toString();
        tp.getTextBounds(text , 0 ,text.length(),rect);
        int length = rect.width()+ getWidth();
        rect =null;
        return length;
    }


    public void pauseScroll()
    {
        if(null == mScroller)
            return;
        if(mPaused)
            return;
        mPaused = true;
        mXPaused = mScroller.getCurrX();
        mScroller.abortAnimation();

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(null == mScroller)
            return;
        if(mScroller.isFinished()&& !mPaused)
        {
            this.startScroll();
        }
    }

    public int getRndDuration() {
        return mRandDuration;
    }

    public void setRndDuration(int duration) {
        this.mRandDuration = duration;
    }

    public boolean isPaused() {
        return mPaused;
    }
}
