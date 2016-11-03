package com.notrace.library.views.bubbleview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created by notrace on 2016/11/2.
 */

public class RoundRectDrawable extends Drawable{
    private float mRadius;
    private Paint mPaint;
    private RectF mBoundsF;
    private Rect mBoundsI;
    private float mPadding;
    private boolean isInsetForPadding=false;
    private boolean isInsetForRadius = true;

   public RoundRectDrawable(int backgroundcolor,float radius){
       this.mRadius= radius;
       mPaint=new Paint(Paint.ANTI_ALIAS_FLAG |Paint.DITHER_FLAG);
       mPaint.setColor(backgroundcolor);
       mBoundsF =new RectF();
       mBoundsI =new Rect();

   }

    private void setPadding(float padding,boolean insetForPadding,boolean insetForRadius){
        if(padding ==mPadding && isInsetForPadding ==insetForPadding && isInsetForRadius ==insetForRadius){
            return;
        }
        mPadding =padding;
        this.isInsetForPadding =insetForPadding;
        this.isInsetForRadius= insetForRadius;
        updateBounds(null);
        invalidateSelf();
    }

    private float getPadding(){
        return  mPadding;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(mBoundsF,mRadius,mRadius,mPaint);

    }

    @Override
    public void setAlpha(int alpha) {

    }

    private void updateBounds(Rect rect){
        if (null ==rect)
        {
            rect =getBounds();
        }
        mBoundsF.set(rect.left,rect.top,rect.right,rect.bottom);
        mBoundsI.set(rect);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        updateBounds(bounds);
    }

    @Override
    public void getOutline(Outline outline) {
        outline.setRoundRect(mBoundsI,mRadius);
    }
    private void setRadius(float radius){
        if (mRadius ==radius)
            return;
        mRadius=radius;
        updateBounds(null);
        invalidateSelf();
    }



    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    public float getRadius()
    {
        return mRadius;
    }

    public void setColor(int color)
    {
        mPaint.setColor(color);
        invalidateSelf();
    }

    public Paint getPaint()
    {
        return  mPaint;
    }
}
