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

        tv_pollution.setText( Government.totalPollution + " ug/m^3");
        tv_satisfaction.setText( government.getHappiness() + "%");
        tv_travelIncome.setText(government.calculateTravelIncome(factory.totalInstitutionLevel()) + " k$");

        tv_money = (TextView)findViewById(R.id.i_TotalMoney);
        tv_temperature = (TextView)findViewById(R.id.i_Temperature);
        tv_inTurn = (TextView)findViewById(R.id.m_inTurn);


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
        // factory
        factory.CalculateTurn();   // 结算赚钱和污染

        // market
        //market.ghgPriceChange();
        market.unitPriceChange(inTurn);
        market.setUnitPrice(Factory.spendingPerUnit);
        market.ghgPriceChange();

        // 工厂赚的钱，买GHG的钱，卖GHG的钱，升级花费的钱, 旅游收入
        totalMoney += Factory.turnProfit + government.calculateTravelIncome(factory.totalInstitutionLevel()) - (Factory.insiCost[0]+ Factory.insiCost[1]+ Factory.insiCost[2]);


        if(Factory.turnPollution >= Market.notUsedPlace){
            totalPollution += Factory.turnPollution - Market.notUsedPlace + market.soldGHG()[1];
            Market.notUsedPlace = 0;
        }
        else
        {
            Market.notUsedPlace -= Factory.turnPollution;
            totalPollution += market.soldGHG()[1];
        }

        float temp = (float)(Math.log10(totalPollution));

        if(temperature >= 26f)
        temperature = 23.00f + (float)(Math.round(temp*100))/100;

        totalPollution *= 0.95;

        government.calculateHappiness();
        turnEnd = false;
        inTurn ++;
        turnStart = true;
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

    public void onFactory(View v)
    {
        Intent i = new Intent(this,A_Factory.class);


        i.putExtra("temp_MainToFac",temperature);
        i.putExtra("turn_MainToFac",inTurn);

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

        i.putExtra("temp_MainToMar",temperature);
        i.putExtra("turn_MainToMar",inTurn);

        startActivityForResult(i,3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1)
        {// back from factory
            //factory.currentUnit = data.getIntExtra("current Unit",factory.currentUnit);

            totalMoney = Factory.totalMoney;
            temperature = data.getFloatExtra("temp_FacToMain",0);
            inTurn = data.getIntExtra("turn_FacToMain",0);
            UpdateText();
        }
        if(resultCode == 7)
        {
            totalMoney = Factory.totalMoney;
            temperature = data.getFloatExtra("temp_FacToMain",0);
            inTurn = data.getIntExtra("turn_FacToMain",0);
            UpdateText();
            onNextTurn(findViewById(R.id.i_nextTurn));
        }
        if(resultCode == 3)
        {
            totalMoney = Factory.totalMoney;
            totalPollution = Factory.totalPollution;

            government.inputValue(totalMoney,totalPollution);

            temperature = data.getFloatExtra("temp_MarToMain",0);
            inTurn = data.getIntExtra("turn_MarToMain",0);


            UpdateText();
        }
        if(resultCode == 6)
        {
            totalMoney = Factory.totalMoney;
            totalPollution = Factory.totalPollution;

            government.inputValue(totalMoney,totalPollution);

            temperature = data.getFloatExtra("temp_MarToMain",0);
            inTurn = data.getIntExtra("turn_MarToMain",0);


            UpdateText();
            onNextTurn(findViewById(R.id.i_nextTurn));
        }
        if(resultCode == 4)
        {
            //add earned money from quiz

            UpdateText();
        }

        if(resultCode == 5)
        {
            Factory.totalMoney += data.getIntExtra("earnMoney",0);
            totalMoney += data.getIntExtra("earnMoney",0);
            UpdateText();
        }
    }

    public void UpdateText()
    {
        totalMoney = Factory.totalMoney;

        ttMoney = "$"+totalMoney + "k";
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
}

