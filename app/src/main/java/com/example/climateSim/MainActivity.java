package com.example.climateSim;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public int totalPollution = 5000;     // 现在污染程度 工厂污染和CHC空间售卖 (total pollution)
    public int totalMoney = 1000;         //现在总金额 (total money)
    public float temperature = 26.0f;      // 当前温度
    public float satisfaction = 50;

    public int inTurn = 1;
    public boolean turnEnd = false;
    public boolean turnStart = false;

    Factory factory = new Factory();
    Market market = new Market();
    Government government = new Government();

    TextView tv_money;
    TextView tv_temperature;
    TextView tv_satisfaction;

    // Text display
    String ttMoney;
    String temp;
    String satis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.

        }
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        /*//set font
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/avenir.ttf");
        TextView tv = (TextView) findViewById(R.id.splash_title);
        tv.setTypeface(tf);
        */

        factory.Init();
        factory.inputValue(totalMoney,totalPollution,market.getUnitPrice());

        // market
        market.Init();
        market.inputValue(totalMoney,totalPollution);

        // government
        government.Init();
        government.inputValue(totalMoney,totalPollution);

        // Text display
        UpdateText();

    }

    @Override
    protected void onResume() {
        super.onResume();




    }

    public void onNextTurn(View v)
    {

       turnEnd = true;
        if(turnEnd)
        {
            // factory
            factory.CalculateTurn();   // 结算赚钱和污染

            // market
            //market.ghgPriceChange();
            market.unitPriceChange(inTurn);
            market.setUnitPrice(factory.spendingPerUnit);
            market.ghgPriceChange();

            // 工厂赚的钱，买GHG的钱，卖GHG的钱，升级花费的钱, 旅游收入
            totalMoney += factory.turnProfit  + government.calculateTravelIncome(factory.totalInstitutionLevel()) - (factory.insiCost[0]+factory.insiCost[1]+factory.insiCost[2]);



            if(factory.turnPollution >= market.notUsedPlace){
                totalPollution += factory.turnPollution - market.notUsedPlace + market.soldGHG()[1];
                market.notUsedPlace = 0;
            }
            else
            {
                market.notUsedPlace -= factory.turnPollution;
                totalPollution += market.soldGHG()[1];
            }
            temperature = 23.00f + (float)Math.log10(totalPollution);

            totalPollution *= 0.95;

            government.calculateHappiness();
            turnEnd = false;
            inTurn ++;
            turnStart = true;
        }
        if(turnStart)
        {
            turnStart = false;
            // factory
            factory.Init();
            factory.inputValue(totalMoney,totalPollution,market.getUnitPrice());

            // market
            market.Init();
            market.inputValue(totalMoney,totalPollution);

            // government
            government.Init();
            government.inputValue(totalMoney,totalPollution);


            // Text display
            UpdateText();
        }
    }

    public void onFactory(View v)
    {
        Intent i = new Intent(this,A_Factory.class);

        startActivityForResult(i,1);
    }

    public void onGovernment(View v)
    {
        Intent i = new Intent(this,A_Government.class);
        //i.putExtra("TotalPollution", totalPollution);

        startActivityForResult(i,2);
    }

    public void onMarket(View v)
    {
        Intent i = new Intent(this,A_Market.class);
        //i.putExtra("TotalPollution", totalPollution);

        startActivityForResult(i,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1)
        {
            //factory.currentUnit = data.getIntExtra("current Unit",factory.currentUnit);
            UpdateText();
        }
        if(resultCode == 2)
        {
            UpdateText();
        }
        if(resultCode == 3)
        {
            totalMoney = factory.totalMoney;
            totalPollution = factory.totalPollution;

            government.inputValue(totalMoney,totalPollution);

            UpdateText();
        }
    }

    public void UpdateText()
    {
        ttMoney = "Total Money:" + totalMoney + " k$";
        temp = temperature + "°C";
        satis = "In Turn: " + inTurn;

        tv_money = (TextView)findViewById(R.id.i_TotalMoney);
        tv_temperature = (TextView)findViewById(R.id.i_Temperatur);
        tv_satisfaction = (TextView)findViewById(R.id.i_Satisfaction);

        tv_money.setText(ttMoney);
        tv_temperature.setText(temp);
        tv_satisfaction.setText(satis);
    }
}

