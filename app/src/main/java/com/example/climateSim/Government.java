package com.example.climateSim;

/**
 * Created by 黄波铖 on 2016/11/8.
 */
public class Government {
    public Government()
    {

    }


    public void Init()
    {
    }

    // public variable can not write in
    static public int totalMoney;
    static public int totalPollution;


    public void inputValue(int ttMoney, int ttPollu)
    {
        totalMoney = ttMoney;
        totalPollution  = ttPollu;
    }

    private static float happiness = 50.00f;
    private static int travelIncome = 0;

    public int calculateTravelIncome(int institution)
    {

        travelIncome = institution * 15 - totalPollution/100;
        if (travelIncome>=0)
        return travelIncome;
        else
            return 0;
    }

    public void calculateHappiness()
    {
        happiness += (totalMoney - totalPollution)/totalMoney;
    }

    public float getHappiness()
    {
        return happiness;
    }


}
