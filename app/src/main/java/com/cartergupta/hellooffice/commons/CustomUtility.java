package com.cartergupta.hellooffice.commons;

import android.util.Log;

/**
 * Created by agupta on 04-Feb-17.
 */

public class CustomUtility {
    public String prefixWithZero(int valueInt) {
        Log.i("CLASS : ", "CustomUtility");
        Log.i("METHOD : ", "prefixWithZero");
        String valueString = Integer.toString(valueInt);
        int length = valueString.length();
        if (length == 1) {
            valueString = "0" + valueString;
        }
        return valueString;
    }
}
