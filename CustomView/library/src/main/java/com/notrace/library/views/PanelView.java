package com.notrace.library.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.notrace.library.utils.PxUtils;

/**
 * Created by notrace on 2016/11/2.
 */

public class PanelView extends View {
    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private Context mContext;
    private int mPercent=50;
    private int rulerColor= Color.RED;
    private int mStrokeWidth=8;
    private int lineColor=Color.WHITE;


    private int rulerCount=12;
    private int padding=50;
    private float fromDegree=145f;
    private float swepDegree=250f;
    
    private int sencondRulerStroke =50;

    public int getProgress() {
        return mPercent;
    }

    public void setProgress(int mPercent) {
        this.mPercent = mPercent;
        invalidate();
    }

    public PanelView(Context context) {

        super(context);
        init(context);
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else {
            mWidth = PxUtils.dpToPx(200,mContext);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else {
            mHeight = PxUtils.dpToPx(200,mContext);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init(Context context) {
        mContext = context;
        mPaint =new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.LTGRAY);
        drawCenterCircle(canvas);
        drawPanel(canvas);
        drawRuler(canvas);
        drawSecondPanel(canvas);
        drawProgressPanel(canvas);
        drawPoint(canvas);

    }


    private void drawCenterCircle(Canvas canvas)
    {

        mPaint.setColor(rulerColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(mWidth/2,mHeight/2,70,mPaint);

        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(20);
        canvas.drawCircle(mWidth/2,mHeight/2,40,mPaint);
    }
    private void drawPanel(Canvas canvas)
    {
        mPaint.setColor(rulerColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        RectF ruler=new RectF(padding,padding,mWidth -padding,mHeight -padding);
        canvas.drawArc(ruler,fromDegree,swepDegree,false,mPaint);
    }

    private void drawRuler(Canvas canvas)
    {
        mPaint.setColor(rulerColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.save();
        float degree=swepDegree/rulerCount;

        canvas.drawLine(mWidth/2,padding,mWidth/2,padding+mStrokeWidth*2,mPaint);
        for(int i=0;i<rulerCount/2;i++ ){
            canvas.rotate(degree,mWidth/2,mHeight/2);
            canvas.drawLine(mWidth/2,padding,mWidth/2,padding+mStrokeWidth*2,mPaint);
        }
        canvas.restore();
        canvas.save();
        for(int i=0;i<rulerCount/2;i++ ){
            canvas.rotate(-degree,mWidth/2,mHeight/2);
            canvas.drawLine(mWidth/2,padding,mWidth/2,padding+mStrokeWidth*2,mPaint);
        }
        canvas.restore();
    }
    private void drawSecondPanel(Canvas canvas)
    {
        mPaint.setColor(lineColor);
        mPaint.setStrokeWidth(sencondRulerStroke+mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        RectF ruler=new RectF(padding+sencondRulerStroke+mStrokeWidth,padding+sencondRulerStroke+mStrokeWidth,mWidth -padding-sencondRulerStroke-mStrokeWidth,mHeight -padding-sencondRulerStroke-mStrokeWidth);
        canvas.drawArc(ruler,fromDegree,swepDegree,false,mPaint);
    }

    private void drawPoint(Canvas canvas)
    {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(lineColor);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.save();
        canvas.rotate(-125+2.5f*mPercent,mWidth/2,mHeight/2);
        canvas.drawLine(mWidth/2,mHeight/2-40,mWidth/2,padding+mStrokeWidth+mStrokeWidth*2+sencondRulerStroke+10,mPaint);
        canvas.restore();
    }

    private void drawProgressPanel(Canvas canvas)
    {
        mPaint.setColor(rulerColor);
        mPaint.setStrokeWidth(sencondRulerStroke);
        mPaint.setStrokeCap(Paint.Cap.BUTT);

        RectF ruler=new RectF(padding+sencondRulerStroke+mStrokeWidth,padding+sencondRulerStroke+mStrokeWidth,mWidth -padding-sencondRulerStroke-mStrokeWidth,mHeight -padding-sencondRulerStroke-mStrokeWidth);
        canvas.drawArc(ruler,fromDegree,2.5f*mPercent,false,mPaint);
    }
}
