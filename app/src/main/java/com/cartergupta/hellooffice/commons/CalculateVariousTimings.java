package com.cartergupta.hellooffice.commons;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by agupta on 31-Jan-17.
 */

public class CalculateVariousTimings {
    CustomUtility utils = new CustomUtility();
    public int inHour;
    public int outHour;
    public int inMinute;
    public int outMinute;
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

    public String totalTimetoday(String fetchedInTime, String fetchedOutTime) {
        Log.i("CLASS : ", "CalculateVariousTimings");
        Log.i("METHOD : ", "totalTimetoday");
        int[] splittedInTime = timeSplitAndConvertToInt(fetchedInTime);
        int[] splittedOutTime = timeSplitAndConvertToInt(fetchedOutTime);
        inHour = splittedInTime[0];
        inMinute = splittedInTime[1];
        outHour = splittedOutTime[0];
        outMinute = splittedOutTime[1];
        if (inHour == outHour) {
            differenceMinutes = outMinute - inMinute;
            totalHours = 00;
        } else {
            differenceHours = outHour - inHour;
            differenceMinutes = outMinute - inMinute;
            totalMinuteDifference = (differenceHours * 60) + differenceMinutes;
            totalHours = (totalMinuteDifference / 60);
            totalMinutes = (totalMinuteDifference % 60);
        }
        totalTime = utils.prefixWithZero(totalHours) + ":" + utils.prefixWithZero(totalMinutes);
        return totalTime;
    }
}
