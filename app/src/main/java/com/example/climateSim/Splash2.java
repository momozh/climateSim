package com.example.climateSim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Splash2 extends AppCompatActivity {
    EditText et_place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        et_place = (EditText)findViewById(R.id.splash_cityname);

        et_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_place.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        Toast.makeText(Splash2.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
            }
        });
    }

    public void Back(View v)
    {
        Intent i = new Intent();
        // i.putExtra("current Unit",factory.currentUnit);
        setResult(4,i);
        finish();
    }
}
