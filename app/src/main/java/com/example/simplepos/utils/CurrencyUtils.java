package com.example.simplepos.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {

    public static String toRupiah(double price) {
        DecimalFormat currencyFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("id", "ID"));
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(new Locale("id", "ID"));
        
        formatSymbols.setCurrencySymbol("Rp ");
        formatSymbols.setMonetaryDecimalSeparator(',');
        formatSymbols.setGroupingSeparator('.');
        
        currencyFormat.setDecimalFormatSymbols(formatSymbols);
        currencyFormat.setMaximumFractionDigits(0);
        
        return currencyFormat.format(price);
    }
}
