package com.example.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class A_Government extends AppCompatActivity {

    Factory factory;
    Government government;
    TextView tv_pollution;
    TextView tv_satisfaction;
    TextView tv_travelIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__government);

        factory = new Factory();

        government = new Government();

        government.calculateHappiness();



        tv_pollution = (TextView)findViewById(R.id.i_g_pollution);
        tv_satisfaction = (TextView)findViewById(R.id.i_satisfaction);
        tv_travelIncome = (TextView)findViewById(R.id.i_TravelInome);

        Intent i = new Intent();

        tv_pollution.setText("Pollution: " + government.totalPollution + " ug/m^3");
        tv_satisfaction.setText("Satisfaction: " + government.getHappiness() + "%");
        tv_travelIncome.setText("Travel Income: " + government.calculateTravelIncome(factory.totalInstitutionLevel()) + " k$");

    }

    public void onBack(View v)
    {
        Intent i = new Intent();
       // i.putExtra("current Unit",factory.currentUnit);
        setResult(2,i);
        finish();
    }
}
