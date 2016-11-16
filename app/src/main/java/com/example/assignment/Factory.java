package com.example.assignment;

import java.io.Serializable;

/**
 * Created by 黄波铖 on 2016/11/7.
 */
public class Factory{

        public  Factory () {  // Init the data

        }

    // Init
    public void Init()
    {
        insiCost[0] = 0;
        insiCost[1] = 0;
        insiCost[2] = 0;

        currentUnit = 0;
        turnProfit = 0;
        turnPollution = 0;
    }

    // public variable can not write in
   static public int totalMoney;
   static public int totalPollution;
   static public int spendingPerUnit;

    public void inputValue(int ttMoney, int ttPollu, int spendPU)
    {
        totalMoney = ttMoney;
        totalPollution  = ttPollu;
        spendingPerUnit = spendPU;
    }

    public int getUnit()
    {
        return unit;
    }

        public void levelUp_Type1() // increase price and pollution
        {
            if(institutionLevel[0] < 5 && institutionSpending[0] <= totalMoney)
            {
                profitPerUnit += 20 * institution[institutionLevel[0]];
                pollutionPerUnit += 10 * institution[institutionLevel[0]];
                institutionLevel[0] += 1;
                insiCost[0] +=institutionSpending[0];
                institutionSpending[0] += 1000;
            }

        }

        public void levelUp_Type2() // increase amount per Turn can buy
        {
            if(institutionLevel[1] < 5 && institutionSpending[1] <= totalMoney)
            {
                unit += 20 * institution[institutionLevel[1]];
                spendingPerUnit -= 2 * institution[institutionLevel[1]];
                institutionLevel[1] += 1;
                insiCost[1] =institutionSpending[1];
                institutionSpending[1] += 1000;
            }
        }

        public void levelUp_Type3() // increase little price and reduce pollution
        {
            if(institutionLevel[2] < 5 && institutionSpending[2] <= totalMoney)
            {
                pollutionPerUnit -= 10 * institution[institutionLevel[2]];
                profitPerUnit += 3 *  institution[institutionLevel[2]];
                institutionLevel[2] += 1;
                insiCost[2] =institutionSpending[2];
                institutionSpending[2] += 1000;
            }
        }

        public int totalInstitutionLevel()
        {
            int level = 0;
            level = institutionLevel[0] + institutionLevel[1] + institutionLevel[2];
            return level;
        }
        // after one turn
        public void CalculateTurn()
        {
            turnProfit = currentUnit * (profitPerUnit - spendingPerUnit);// unitP 是单位原料价格
            turnPollution = currentUnit * pollutionPerUnit;
        }

    static      int  profitPerUnit = 50;   //单位商品利润 (price)
    static   int pollutionPerUnit = 50; // 单位商品污染 (pollution)
    static   int unit = 10;               // 最高生产数量 (max unit)
    static    int currentUnit = 0;        // 当前生产数量 (unit)

    static    public int turnProfit = 0;        // 本轮工厂收益 ((price - unitPrice) * currentUnit)
    static    public int turnPollution = 0;       // 本轮工厂排污 (currentUnit * pollution )

    static  public   int[] institutionLevel = {1,1,1}; // Three level of the institution
    static  public   int[] institutionSpending={1000,1000,1000};  // Spending money of level up institution
    static    public int[] insiCost = {0,0,0};
    static   float[] institution = {1.1f,1.2f,1.3f,1.4f,1.5f};    // 每级升级参数的比例

    


};
