package com.example.climateSim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends Activity {
    
    int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();

        int score = b.getInt("score");
        money = score;
        TextView textResult = (TextView) findViewById(R.id.textResult);


        textResult.setText("You have earned " + " " + score + " k dollars. ");

    }

    public void onBack(View v) {

        //Intent intent = new Intent(this, QuestionActivity.class);

        //startActivity(intent);
        Intent i = new Intent();
        i.putExtra("Money",money);
        setResult(1,i);
        finish();
    }
}
