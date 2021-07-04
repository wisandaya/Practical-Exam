package com.example.practicalexam.globals;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.Locale;

public class GlobalString {

    public static String DATA_URL = "https://restcountries.eu/rest/v2/all";
    public static String CountryName = "name";
    public static String Region = "region";
    public static String CallingCode = "callingCodes";
    public static String Population = "population";
    public static String Currencies = "currencies";
    public static String LngLat = "latlng";
    public static String Languages = "languages";
    public static String Borders = "borders";
    public static String Flag = "flag";




    public static String generateRandomHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); //hex encoding
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static String getFormatedAmount(String amount) {
        if(amount.contentEquals("null") || amount.contentEquals("") || amount.contentEquals("No record found")){
            amount = "0.0";
        }
        else{
            String Cleansed = amount.replaceAll(",", "");
            amount = NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(Cleansed));
        }
        return amount;
    }

    public static String nullcheckInt(String value) {
        if (value.contentEquals("  ") || value.contentEquals("") || value.contentEquals("null") || value.contentEquals(" ") || value.contentEquals("NaN") || value.contentEquals("∞")||value.isEmpty()||value.length() ==0) {
            value = "0";
        }
        return value;
    }
}
