package com.siloamusa.utils.generics;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatUtils {
   
    public static final DateTimeFormatter ZDT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

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
    /*
     * Formats a string representing an amount into a US locale format with commas and decimal points.
     */
    public static String formatAmount(BigDecimal amount) {
        String fmtString = "";
        DecimalFormat dfUS = new DecimalFormat("##,###,##0.00", DecimalFormatSymbols.getInstance(Locale.US));  
        try{
            fmtString = dfUS.format(amount);
        }catch(Exception e){
            fmtString = ""+amount; // Fallback to original amount if parsing fails
            e.printStackTrace();
        }
        return fmtString;
    }
    /*
     * Get current date and time in dd/MM/yyyy HH:mm:ss format.
     */
    public static String getCurDateTimeFmt1() {
        String date_time = ZDT_FORMATTER.format( ZonedDateTime.now() );
        return date_time;
    }

}