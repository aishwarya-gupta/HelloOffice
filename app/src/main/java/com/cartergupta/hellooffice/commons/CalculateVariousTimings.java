package com.cartergupta.hellooffice.commons;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by agupta on 31-Jan-17.
 */

public class CalculateVariousTimings {
    CustomUtility utils = new CustomUtility();
    public int timeOneHour;
    public int timeTwoHour;
    public int timeOneMinute;
    public int timeTwoMinute;
    public int differenceHours;
    public int differenceMinutes;
    public int totalMinuteDifference;
    public int totalHours;
    public int totalMinutes;
    public String totalTime;

    public String timeNow() {
        Log.i("CLASS : ", "CalculateVariousTimings");
        Log.i("METHOD : ", "timeNow");
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return currentTime.format(new Date());
    }

    public int[] timeSplitAndConvertToInt(String timeToSplit) {
        Log.i("CLASS : ", "CalculateVariousTimings");
        Log.i("METHOD : ", "timeSplitAndConvertToInt");
        String[] splittedTime = timeToSplit.split(":");
        int hourIntValue = Integer.parseInt(splittedTime[0]);
        int minuteIntValue = Integer.parseInt(splittedTime[1]);
        int[] timeIntValues = new int[2];
        timeIntValues[0] = hourIntValue;
        timeIntValues[1] = minuteIntValue;
        return timeIntValues;
    }

    public String totalTimeCalculator(String timeOne, String timeTwo) {
        Log.i("CLASS : ", "CalculateVariousTimings");
        Log.i("METHOD : ", "totalTimeCalculator");
        int[] splittedTimeOne = timeSplitAndConvertToInt(timeOne);
        int[] splittedTimeTwo = timeSplitAndConvertToInt(timeTwo);
        timeOneHour = splittedTimeOne[0];
        timeOneMinute = splittedTimeOne[1];
        timeTwoHour = splittedTimeTwo[0];
        timeTwoMinute = splittedTimeTwo[1];
        if (timeOneHour == timeTwoHour) {
            differenceMinutes = timeTwoMinute - timeOneMinute;
            totalHours = 0;
        } else {
            differenceHours = timeTwoHour - timeOneHour;
            differenceMinutes = timeTwoMinute - timeOneMinute;
            totalMinuteDifference = (differenceHours * 60) + differenceMinutes;
            totalHours = (totalMinuteDifference / 60);
            totalMinutes = (totalMinuteDifference % 60);
        }
        totalTime = utils.prefixWithZero(totalHours) + ":" + utils.prefixWithZero(totalMinutes);
        return totalTime;
    }
}
