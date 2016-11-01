package com.notrace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.notrace.library.SearchView;

public class SearchViewActivity extends AppCompatActivity implements View.OnClickListener{

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_view);
        searchView= (SearchView) findViewById(R.id.searchview);
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.end).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start:

                searchView.start();

                break;
            case R.id.search:
                searchView.search();
                break;
            case  R.id.end:
                searchView.end();
                break;
        }
    }
}
