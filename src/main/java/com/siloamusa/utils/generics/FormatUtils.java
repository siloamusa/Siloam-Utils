package com.siloamusa.utils.generics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatUtils {
    
   public static String clearNull(String sourceString) {
        if (sourceString == null) {
            return "";
        }
        if("null".equals(sourceString)) {
            return "";
        }
        return sourceString;
    }

    /*
     * Formats a string representing an amount into a US locale format with commas and decimal points.
     */
    public static String formatAmount(String amount) {
        String fmtString = "";
        DecimalFormat dfUS = new DecimalFormat("##,###,##0.00", DecimalFormatSymbols.getInstance(Locale.US));  
        try{
            BigDecimal amountBD = new BigDecimal(amount);
            fmtString = dfUS.format(amountBD);
        }catch(Exception e){
            fmtString = amount; // Fallback to original amount if parsing fails
            e.printStackTrace();
        }
        return fmtString;
    }
}
