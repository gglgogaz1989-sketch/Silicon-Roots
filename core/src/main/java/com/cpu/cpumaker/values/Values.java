package com.cpu.cpumaker.values;

public class Values {
    public static double dollars = 0;
    public static long transistorCount = 0;
    public static boolean inShop = false;
    
    // Расчет прогрессивной прибыли
    public static double getCurrentProfit() {
        if (transistorCount <= 0) return 0.2;
        return 0.2 * Math.pow(1.85, transistorCount - 1);
    }
}
