package com.example.assignment;

/**
 * Created by 黄波铖 on 2016/11/8.
 */
public class Market {
    public Market () {

    }

    // init when each turn start
    public void Init()
    {
        currUnitSoldGHG=0;
        currUnitGHG = 0;
        saleRate = 1;

        bought = false;
        sold = false;
    }

    // everytime



    // buy ghg
    public  int buyGHG()
    {


        if(currUnitGHG > 50)
        {
            saleRate = 0.9f;

        }
        else
        {
            saleRate = 1;
        }


        notUsedPlace += currUnitGHG * 20;
           return (int)(ghgBuyPrice * saleRate * currUnitGHG);
    }
    public int[] soldGHG()
    {
        int[] moneyAndPollution = {0,0};
        moneyAndPollution[0] = ghgSoldPrice*currUnitSoldGHG;
        moneyAndPollution[1] = currUnitSoldGHG * 30;

        return moneyAndPollution;
    }

    // after buy and sold
    public void ghgPriceChange() // run after one turn
    {
        ghgBuyPrice = ghgValue + 30;
        ghgSoldPrice = ghgValue;
        ghgValue += currUnitGHG/10;
        ghgValue -= currUnitSoldGHG/8;
    }
    public void unitPriceChange(int currentTurn)
    {
        if((currentTurn+1)%2 == 0)
        {
            unitPrice += 2;
        }
    }

    public void setUnitPrice(int unitP)
    {
        unitPrice = unitP;
    }
    public int getUnitPrice()
    {
        return unitPrice;
    }
    private static int unitPrice = 20;  // 单位原料所用价格

    public static int ghgSoldPrice = 20;   // 卖出价格
    public static int currUnitSoldGHG = 0;    // 当前卖出ghg量

    public static int ghgBuyPrice = 30;    // 买入价格
    public static int unitGHGMax = 2000;        // 能买入总量
    public static int currUnitGHG = 0;        // 当前买入ghg量

    public static float saleRate = 1;       // 一次性买多过多少 给折扣
    public static int ghgValue = 35;    // ghg空间的价值，影响买入及卖出价格

    public static int notUsedPlace = 0;   // 还没使用的抵消空间

   public static boolean bought = false;
    public static boolean sold = false;

    // before each turn start
    public void inputValue(int ttMoney, int ttPollu)
    {
        totalMoney = ttMoney;
        totalPollution  = ttPollu;
    }

    // public can not in
    public static int totalMoney;
    public static int totalPollution;
}
