package com.notrace.library.views.bubbleview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by notrace on 2016/11/2.
 */

public class BubbleTextView extends BubbleView implements Runnable{

    private TextView textView;
    public BubbleTextView(Context context) {
        super(context);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initChildView(float radius, int backgroundColor, int textColor, float textSize, String content) {
        super.initChildView(radius, backgroundColor, textColor, textSize, content);
        textView = new TextView(mContext);
        textView.setId(View.generateViewId());
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setText(content);
        int px22 = dip2px(21);
        int px16 = dip2px(15);
        textView.setPaddingRelative(px22, px16, px22, px16);
        conRl.addView(textView);
    }

    public TextView getContentTextView(){
        return textView;
    }
}
