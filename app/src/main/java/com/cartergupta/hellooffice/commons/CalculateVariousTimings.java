package com.cartergupta.hellooffice.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by agupta on 31-Jan-17.
 */

public class CalculateVariousTimings {
    public int inHour;
    public int outHour;
    public int inMinute;
    public int outMinute;
    public int differenceHours;
    public int differenceMinutes;
    public int totalMinuteDifference;
    public int totalHours;
    public int totalMinutes;

    public String timeNow() {
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return currentTime.format(new Date());
    }

    public void timeSplitter() {
        // TODO : implement time splitter method for inTime and outTime
        // use ArrayList<int> to store all the splitted values
        // return ArrayList
        // change return type to ArrayList<int>
    }

    public String totalTimetoday() {    // TODO : Accept 2 String parameteres : inTime & outTime
        timeSplitter();
        return "01:50";
        // call timeSplitter() method and pass "inTime" and "outTime" as parameters
        // receive an ArrayList<int> of splitted values
        // assign each value to class variables
        // calculate total time today operation
        // convert integer values to String values
        // combine all values
        // return a String in the form "xx:yy"
        // for single x or single y, prefix with "0"
        /*if(inHr == outHr)
        {
            diffMin = outMin - inMin;	// 0 , 15
            totalHr = 00;
        }
        else	// inHr < outHr
        {
            diffHr 	= outHr  - inHr;
            diffMin = outMin - inMin;
            totalMinDiff = (diffHr*60) + diffMin;
            totalHr = (totalMinDiff / 60);
            totalMin = (totalMinDiff % 60);
        }
        totalTime = totalHr + totalMin;	// Total time worked today - xx:yy Hrs*/
    }
}
