package com.example.climateSim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class A_Market extends AppCompatActivity {

    Market market;
    Factory factory;
    SeekBar sb_buy;
    SeekBar sb_sold;

    TextView tv_cost;
    TextView tv_sold;

    TextView tv_unitPrice;

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
        setContentView(R.layout.activity_market);

        Intent i = getIntent();

        temperature = i.getFloatExtra("temp_MainToMar",0);
        inTurn = i.getIntExtra("turn_MainToMar",0);

        market = new Market();
        factory = new Factory();

        totalMoney = factory.totalMoney;

        tv_money = (TextView)findViewById(R.id.m_TotalMoney);
        tv_temperature = (TextView)findViewById(R.id.i_Temperatur);
        tv_inTurn = (TextView)findViewById(R.id.m_inTurn);

        UpdateText();

        tv_cost = (TextView)findViewById(R.id.i_m_ghgCost);
        tv_sold = (TextView)findViewById(R.id.i_m_pollutionAdd);

        tv_unitPrice = (TextView)findViewById(R.id.i_m_unitPrice);
        tv_unitPrice.setText("Production Price: \n" + market.getUnitPrice() + " k$/Ton");

        if(market.currUnitGHG >= 50)
            tv_cost.setText("Cost(90% off): " + market.currUnitGHG * market.ghgBuyPrice * 0.9 + " k$" + "\nUsable GHG place: " + market.currUnitGHG * 10 + " ug/m^3");
        if(market.currUnitGHG <= 50)
            tv_cost.setText("Cost: " + market.currUnitGHG * market.ghgBuyPrice+ " k$" + "\nUsable GHG place: " + market.currUnitGHG * 10 + " ug/m^3");

        tv_sold.setText("Add Pollution: " + market.currUnitSoldGHG * 30 + " ug/m^3" + "\nMoney income: " + market.currUnitSoldGHG * market.ghgSoldPrice +" k$");

        sb_buy = (SeekBar)findViewById(R.id.seekBar_buyGHG);
        sb_buy.setMax(factory.totalMoney/market.ghgBuyPrice);
        sb_buy.setProgress(market.currUnitGHG);

        sb_sold = (SeekBar)findViewById(R.id.seekBar_SellGHG);
        sb_sold.setMax(80);
        sb_sold.setProgress(market.currUnitSoldGHG);

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
                  if(!market.bought && !market.sold){
                    market.currUnitGHG = progress;
                      if(market.currUnitGHG >= 50)
                          tv_cost.setText("Cost(90% off): " + market.currUnitGHG * market.ghgBuyPrice * 0.9 + " k$" + "\nUsable GHG place: " + market.currUnitGHG * 10 + " ug/m^3");
                      if(market.currUnitGHG <= 50)
                          tv_cost.setText("Cost: " + market.currUnitGHG * market.ghgBuyPrice+ " k$" + "\nUsable GHG place: " + market.currUnitGHG * 10 + " ug/m^3");
                  }

            }
        });

        sb_sold.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                if(!market.bought && !market.sold){
                    market.currUnitSoldGHG = progress;
                    tv_sold.setText("Add Pollution: " + market.currUnitSoldGHG * 30 + " ug/m^3" + "\nMoney income: " + market.currUnitSoldGHG * market.ghgSoldPrice +" k$");

                }

            }
        });
    }

    public void onBuy(View v)
    {

        if(!market.bought && market.currUnitGHG != 0)
        {
            market.bought = true;
            market.sold =true;
            market.currUnitSoldGHG = 0;


            factory.totalMoney -= market.buyGHG();

            UpdateText();
        }
    }

    public void onSell(View v)
    {
        if(!market.sold && market.currUnitSoldGHG != 0)
        {
            market.sold = true;
            market.bought = true;
            market.currUnitGHG = 0;

            factory.totalMoney += market.soldGHG()[0];
            factory.totalPollution += market.soldGHG()[1];

            UpdateText();
        }
    }

    public void onBack(View v)
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

                    // go back to main
                        Intent intent = new Intent();

                    intent.putExtra("temp_MarToMain",temperature);
                    intent.putExtra("turn_MarToMain",inTurn);
                        setResult(3,intent);

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
            i.putExtra("temp_MarToMain",temperature);
            i.putExtra("turn_MarToMain",inTurn);
            setResult(3,i);
            finish();
        }


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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5) {
            factory.totalMoney += data.getIntExtra("earnMoney", 0);
            totalMoney += data.getIntExtra("earnMoney",0);
            UpdateText();
        }
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

    public void Quiz(View v)
    {
        Intent i = new Intent(this,QuestionActivity.class);

        startActivityForResult(i,5);
    }

    public void onNextTurn(View v)
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

                    // go back to main
                    Intent intent = new Intent();

                    intent.putExtra("temp_MarToMain",temperature);
                    intent.putExtra("turn_MarToMain",inTurn);
                    setResult(4,intent);

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
            i.putExtra("temp_MarToMain",temperature);
            i.putExtra("turn_MarToMain",inTurn);
            setResult(4,i);
            finish();
        }


    }
}
