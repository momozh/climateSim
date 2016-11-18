package com.example.climateSim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textResult = (TextView) findViewById(R.id.textResult);

        Bundle b = getIntent().getExtras();

        int score = b.getInt("score");

        textResult.setText("You have earned " + " " + score + " dollars. ");

    }

    public void onBack(View v) {

        //Intent intent = new Intent(this, QuestionActivity.class);

        //startActivity(intent);

        Intent i = new Intent();
        setResult(4,i);
        finish();
    }
}