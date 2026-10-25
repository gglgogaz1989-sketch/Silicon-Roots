package com.cpu.cpumaker.values;

public class Coins {
    private static final String[] SUFFIXES = {
        "", "K", "M", "B", "T", "Qa", "Qi", "Sx", "Sp", "Oc", "No", 
        "Dc", "UDc", "DDc", "TDc", "QaDc", "QiDc", "SxDc", "SpDc", "ODc", "NDc", "Vg"
    };

    public static String format(double value) {
        if (value < 1000) {
            return String.format(java.util.Locale.US, "%.2f", value);
        }
        int exp = (int) (Math.log10(value) / 3);
        if (exp >= SUFFIXES.length) {
            exp = SUFFIXES.length - 1;
        }
        double formatted = value / Math.pow(10, exp * 3);
        return String.format(java.util.Locale.US, "%.2f%s", formatted, SUFFIXES[exp]);
    }
}
