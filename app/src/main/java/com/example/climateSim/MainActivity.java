package com.example.climateSim;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    MediaPlayer main_msc;


    public int totalPollution = 5000;     // 现在污染程度 工厂污染和CHC空间售卖 (total pollution)
    public int totalMoney = 1000;         //现在总金额 (total money)
    public float temperature = 26.6f;      // 当前温度


    public int inTurn = 1;
    public boolean turnEnd = false;
    public boolean turnStart = false;

    Factory factory = new Factory();
    Market market = new Market();
    Government government = new Government();

    TextView tv_money;
    TextView tv_temperature;
    TextView tv_inTurn;

    // new changing from th
    
    TextView tv_pollution;
    TextView tv_satisfaction;
    TextView tv_travelIncome;
    
    // Text display
    String ttMoney;
    String temp;
    String turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        main_msc = MediaPlayer.create(this,R.raw.main_bg_msc);
        main_msc.setLooping(true);
        main_msc.start();


        Intent splash = new Intent(this,Splash.class);

        startActivityForResult(splash,4);

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

        // new changing
        government.calculateHappiness();
        tv_pollution = (TextView)findViewById(R.id.i_g_pollution);
        tv_satisfaction = (TextView)findViewById(R.id.i_satisfaction);
        tv_travelIncome = (TextView)findViewById(R.id.i_TravelInome);

        tv_pollution.setText("Pollution: " + government.totalPollution + " ug/m^3");
        tv_satisfaction.setText("Satisfaction: " + government.getHappiness() + "%");
        tv_travelIncome.setText("Travel Income: " + government.calculateTravelIncome(factory.totalInstitutionLevel()) + " k$");

        // Text display
        UpdateText();

    }

    //@Override
    //protected void onPause(){
    //   super.onPause();
    //    main_msc.release();
    //    finish();
    //}

    //@Override
    //protected void onResume() {
    //    super.onResume();
    //}

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

            float temp = (float)(Math.log10(totalPollution));

            temperature = 23.00f + (float)(Math.round(temp*100))/100;

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

        i.putExtra("money",totalMoney);
        i.putExtra("temp",temperature);
        i.putExtra("turn",inTurn);

        startActivityForResult(i,1);
    }

    /*public void onGovernment(View v)
    {
        Intent i = new Intent(this,A_Government.class);
        //i.putExtra("TotalPollution", totalPollution);

        startActivityForResult(i,2);
    }*/

    public void onMarket(View v)
    {
        Intent i = new Intent(this,A_Factory.class);
        i.putExtra("toMarket", 33);

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
        if(resultCode == 3)
        {
            totalMoney = factory.totalMoney;
            totalPollution = factory.totalPollution;

            government.inputValue(totalMoney,totalPollution);

            UpdateText();
        }
        if(resultCode == 4)
        {
            //add earned money from quiz

            UpdateText();
        }

        if(resultCode == 5)
        {
            totalMoney += data.getIntExtra("earnMoney",0);
            UpdateText();
        }
    }

    public void UpdateText()
    {
        ttMoney = totalMoney + " k$";
        temp = temperature + "°C";
        turn = 2017 + 5 * inTurn +" ";

        tv_money = (TextView)findViewById(R.id.i_TotalMoney);
        tv_temperature = (TextView)findViewById(R.id.i_Temperatur);
        tv_inTurn = (TextView)findViewById(R.id.i_Satisfaction);

        tv_money.setText(ttMoney);
        tv_temperature.setText(temp);
        tv_inTurn.setText(turn);
    }

    public void Quiz(View v)
    {
        Intent i = new Intent(this,QuestionActivity.class);

        startActivityForResult(i,5);
    }
}

