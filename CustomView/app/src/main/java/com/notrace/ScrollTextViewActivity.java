package com.notrace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.notrace.library.views.ScrollTextView;

public class ScrollTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollTextView textView=new ScrollTextView(this);
        textView.setText("23333");
        textView.setRndDuration(10000);
        textView.startScroll();

        setContentView(textView);
    }
}
