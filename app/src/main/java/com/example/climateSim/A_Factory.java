package com.example.climateSim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class A_Factory extends AppCompatActivity {

    Factory factory;
    Market market;

    ProgressBar progressBar1;
    TextView tv_up1Cost;

    ProgressBar progressBar2;
    TextView tv_up2Cost;

    ProgressBar progressBar3;
    TextView tv_up3Cost;

    SeekBar sb_buy;
    TextView tv_curUnit;
    TextView tv_max;
    TextView tv_cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        Intent i = getIntent();

        factory = new Factory();
        market = new Market();

        progressBar1 = (ProgressBar)findViewById(R.id.progressBar1) ;
        progressBar1.setMax(5);
        progressBar1.setProgress(factory.institutionLevel[0]);
        tv_up1Cost = (TextView)findViewById(R.id.i_up1_cost);
        tv_up1Cost.setText("Level" +factory.institutionLevel[0] +" Cost: " + factory.institutionSpending[0] + "k$");

        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2) ;
        progressBar2.setMax(5);
        progressBar2.setProgress(factory.institutionLevel[1]);
        tv_up2Cost = (TextView)findViewById(R.id.i_up1_cost2);
        tv_up2Cost.setText("Level" +factory.institutionLevel[1] +" Cost: " + factory.institutionSpending[1]+ "k$");

        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3) ;
        progressBar3.setMax(5);
        progressBar3.setProgress(factory.institutionLevel[2]);
        tv_up3Cost = (TextView)findViewById(R.id.i_up1_cost3);
        tv_up3Cost.setText("Level" +factory.institutionLevel[2] +" Cost: " + factory.institutionSpending[2]+ "k$" );

        sb_buy = (SeekBar)this.findViewById(R.id.i_unitBar);
        sb_buy.setMax(factory.getUnit());
        sb_buy.setProgress(factory.currentUnit);
        tv_curUnit = (TextView)this.findViewById(R.id.i_curUnit);
        tv_max = (TextView)findViewById(R.id.i_maxUnit);
        tv_cost = (TextView)findViewById(R.id.i_f_cost);

        tv_curUnit.setText("Current: " + factory.currentUnit +   " Ton");
        tv_max.setText("Max: " + factory.getUnit() +   " Ton");
        tv_cost.setText("Profit: " + factory.currentUnit * (factory.profitPerUnit - factory.spendingPerUnit)+ "k$" +"\nPollution: " + factory.currentUnit * factory.pollutionPerUnit + " ug/m^3"
            + "\nNot used GHG place: " + market.notUsedPlace + " ug/m^3"
        );



    }

    @Override
    protected void onResume() {
        super.onResume();

        sb_buy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

                factory.currentUnit = progress;
                tv_curUnit.setText("Current: " + factory.currentUnit +   " Ton");
                tv_max.setText("Max: " + factory.getUnit() +   " Ton");
                tv_cost.setText("Profit: " + factory.currentUnit * (factory.profitPerUnit - factory.spendingPerUnit)+ "k$" +"\nPollution: " + factory.currentUnit * factory.pollutionPerUnit + " ug/m^3"
                        + "\nNot used GHG place: " + market.notUsedPlace + " ug/m^3");
            }
        });
    }

    public void onBack(View v)
    {
        Intent i = new Intent();
        i.putExtra("current Unit",factory.currentUnit);
        setResult(1,i);
        finish();
    }

    public void onLevelUp1(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Institution1");
        builder.setMessage("Increase profit and pollution in each production");
        builder.setPositiveButton("Upgrade", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                factory.levelUp_Type1();
                tv_up1Cost.setText("Level" +factory.institutionLevel[0] +" Cost: " + factory.institutionSpending[0] + " k$");
                progressBar1.setProgress(factory.institutionLevel[0]);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        builder.create().show();
    }

    public void onLevelUp2(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Institution2");
        builder.setMessage("Increase maximum production \nLightly deduct spending in each production");
        builder.setPositiveButton("Upgrade", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                factory.levelUp_Type2();
                tv_up2Cost.setText("Level" +factory.institutionLevel[1] +" Cost: " + factory.institutionSpending[1] + " k$");
                progressBar2.setProgress(factory.institutionLevel[1]);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        builder.create().show();


    }
    public void onLevelUp3(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Institution23");
        builder.setMessage("Deduct pollution in each production\nLightly increase profit in each production");
        builder.setPositiveButton("Upgrade", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                factory.levelUp_Type3();
                tv_up3Cost.setText("Level" +factory.institutionLevel[2] +" Cost: " + factory.institutionSpending[2] + " k$");
                progressBar3.setProgress(factory.institutionLevel[2]);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });

        builder.create().show();


    }
}
