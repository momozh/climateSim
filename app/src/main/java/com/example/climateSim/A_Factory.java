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

    //ProgressBar progressBar1;
    TextView tv_up1Cost;

    //ProgressBar progressBar2;
    TextView tv_up2Cost;

    //ProgressBar progressBar3;
    TextView tv_up3Cost;

    SeekBar sb_buy;
    TextView tv_curUnit;
    TextView tv_max;
    TextView tv_cost;

    // new top
    TextView tv_money;
    TextView tv_temperature;
    TextView tv_inTurn;

    String ttMoney;
    String temp;
    String turn;

    int totalMoney;
    float temperature;
    int inTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);
        Intent i = getIntent();

        // go to market, from main
        if(i.getIntExtra("toMarket",0) == 33)
        {
            Intent mar = new Intent(this,A_Market.class);

            mar.putExtra("temp_MainToMar",i.getFloatExtra("temp_MainToMar",0));
            mar.putExtra("turn_MainToMar",i.getIntExtra("turn_MainToMar",0));

            startActivityForResult(mar,3);
        }

        factory = new Factory();
        market = new Market();

        totalMoney = factory.totalMoney;
        temperature = i.getFloatExtra("temp_MainToFac",26.6f);
        inTurn = i.getIntExtra("turn_MainToFac",2022);


        /*progressBar1 = (ProgressBar)findViewById(R.id.progressBar1) ;
        progressBar1.setMax(5);
        progressBar1.setProgress(factory.institutionLevel[0]);


        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2) ;
        progressBar2.setMax(5);
        progressBar2.setProgress(factory.institutionLevel[1]);


        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3) ;
        progressBar3.setMax(5);
        progressBar3.setProgress(factory.institutionLevel[2]);*/

        tv_up2Cost = (TextView)findViewById(R.id.i_up1_cost2);
        tv_up2Cost.setText("Level" +factory.institutionLevel[1] +" Cost: " + factory.institutionSpending[1]+ "k$");

        tv_up1Cost = (TextView)findViewById(R.id.i_up1_cost);
        tv_up1Cost.setText("Level" +factory.institutionLevel[0] +" Cost: " + factory.institutionSpending[0] + "k$");

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

        tv_money = (TextView)findViewById(R.id.f_TotalMoney);
        tv_temperature = (TextView)findViewById(R.id.f_Temperature);
        tv_inTurn = (TextView)findViewById(R.id.f_turn);

        UpdateText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 3)
        {   // go back from market
            Intent i = new Intent();


            i.putExtra("temp_MarToMain",data.getFloatExtra("temp_MarToMain",0));
            i.putExtra("turn_MarToMain",data.getIntExtra("turn_MarToMain",0));
            setResult(3,i);
            finish();
        }

        if(resultCode == 4) // market next turn
        {
            Intent i = new Intent();


            i.putExtra("temp_MarToMain",data.getFloatExtra("temp_MarToMain",0));
            i.putExtra("turn_MarToMain",data.getIntExtra("turn_MarToMain",0));
            setResult(6,i);
            finish();
        }
        if(resultCode == 2)
        {
            totalMoney = factory.totalMoney;

            temperature = data.getFloatExtra("temp_FacToMar",0);
            inTurn = data.getIntExtra("turn_FacToMar",0);
            UpdateText();
            // go on on factory
        }

        if(resultCode == 5)
        {
            factory.totalMoney += data.getIntExtra("earnMoney",0);
            totalMoney = factory.totalMoney;
            UpdateText();
        }
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

    public void onFactory(View v)
    {
        if(!market.bought && !market.sold)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Attention");
            builder.setMessage("You haven't bought or sold, sure to leave?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                    Intent intent = new Intent();
                    // i.putExtra("current Unit",factory.currentUnit);
                    intent.putExtra("temp_FacToMar",temperature);
                    intent.putExtra("turn_FacToMar",inTurn);
                    setResult(2,intent);

                    finish();

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
        else {
            Intent i = new Intent();
            // i.putExtra("current Unit",factory.currentUnit);
            i.putExtra("temp_FacToMar",temperature);
            i.putExtra("turn_FacToMar",inTurn);
            setResult(2,i);
            finish();
        }
    }

    public void onBack(View v)
    {
        Intent i = new Intent();


        i.putExtra("temp_FacToMain",temperature);
        i.putExtra("turn_FacToMain",inTurn);
        setResult(1,i);
        finish();
    }

    public void onMarket(View v)
    {
        Intent i = new Intent(this,A_Market.class);

        startActivityForResult(i,2);
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
                //progressBar1.setProgress(factory.institutionLevel[0]);

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
                //progressBar2.setProgress(factory.institutionLevel[1]);

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
                //progressBar3.setProgress(factory.institutionLevel[2]);

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

    public void onNextTurn(View v)
    {
        Intent i = new Intent();


        i.putExtra("temp_FacToMain",temperature);
        i.putExtra("turn_FacToMain",inTurn);
        setResult(7,i);
        finish();
    }

    public void Quiz(View v)
    {
        Intent i = new Intent(this,QuestionActivity.class);

        startActivityForResult(i,5);
    }



    public void UpdateText()
    {
        totalMoney = factory.totalMoney;

        ttMoney = totalMoney + " k$";
        temp = temperature + "°C";
        turn = 2017 + 5 * inTurn +" ";



        tv_money.setText(ttMoney);
        tv_temperature.setText(temp);
        tv_inTurn.setText(turn);
    }
}
