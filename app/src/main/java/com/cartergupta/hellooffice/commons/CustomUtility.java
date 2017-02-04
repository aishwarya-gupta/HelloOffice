package com.cartergupta.hellooffice.commons;

/**
 * Created by agupta on 04-Feb-17.
 */

public class CustomUtility {
    public String prefixWithZero(int valueInt) {
        String valueString  = Integer.toString(valueInt);
        int length = valueString.length();
        if(length == 1) {
            valueString = "0" + valueString;
        }
        return valueString;
    }
}
