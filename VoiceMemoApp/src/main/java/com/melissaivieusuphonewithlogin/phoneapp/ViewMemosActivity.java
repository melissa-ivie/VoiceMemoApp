package com.melissaivieusuphonewithlogin.phoneapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ViewMemosActivity extends ActivityWithUser {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memos);
        ImageView speak = findViewById(R.id.speak);
        Button viewHomeActivity = findViewById(R.id.viewHomeActivity);

        findViewById(R.id.logout_button).setOnClickListener((view) -> {
            viewModel.signOut();
        });

        EditText memoView = findViewById(R.id.memoListBox);
        MultiText memoView = findViewById(R.id.memoListBox);

        //List<String> memos = viewModel.getMemos();
        List<String> memos = new ArrayList<String>(Arrays.asList("apples","bananas","grapes"));
        for (String memo : memos) {
            TextView tv = new TextView(memoView.getContext());
            tv.setText(memo);
            memoView.addView(tv);
        }

        viewHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}