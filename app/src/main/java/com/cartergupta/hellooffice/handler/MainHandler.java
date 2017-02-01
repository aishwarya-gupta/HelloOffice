package com.cartergupta.hellooffice.handler;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cartergupta.hellooffice.R;
import com.cartergupta.hellooffice.commons.CalculateVariousTimings;
import com.cartergupta.hellooffice.database.MonitorMeDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by agupta on 31-Jan-17.
 */

public class MainHandler {
    Context handlerContext;
    MonitorMeDatabaseHelper db;

    public MainHandler(Context context) {
        handlerContext = context;
    }

    public void mainToggleButton(boolean in, View view) {
        CalculateVariousTimings timings = new CalculateVariousTimings();
        String formattedDateTime = timings.timeNow();
        String[] splittedDateTime = formattedDateTime.split(" ");
        String formattedDate = splittedDateTime[0];
        String formattedTime = splittedDateTime[1];

        if (in) {   // Enters into Office
            TextView displayInTime = (TextView) view.findViewById(R.id.displayInTime);
            String inTime = formattedTime;
            db = new MonitorMeDatabaseHelper(handlerContext);
            Log.i("Check 0","Reached here!");
            boolean inTimeAvailableResult = db.checkInTimeAvailable(formattedDate);
            Log.i("Check 1","Reached here!");
            if (!(inTimeAvailableResult)) { // insert inTime into database
                boolean inTimeInsertionResult = db.inTimeInsertReport(formattedDate, inTime);
                if (!(inTimeInsertionResult)) {
                    Toast.makeText(handlerContext, "DATABASE ERROR : Please restart application", Toast.LENGTH_LONG).show();
                }
            }
            String fetchedInTime = db.fetchInTime(formattedDate);
            displayInTime.setText("    IN TIME - " + fetchedInTime + " Hrs");
        } else {    // Exits from Office
            TextView displayOutTime = (TextView) view.findViewById(R.id.displayOutTime);
            TextView displayTotalTime = (TextView) view.findViewById(R.id.displayTotalTime);
            String outTime = formattedTime;
            boolean outTimeInsertionResult = db.outTimeInsertReport(formattedDate, outTime);
            if (outTimeInsertionResult) {
                // TODO : fetch both "inTime" and "outTime" for "onDate" - 1st time only
                // create 1 more column in table - "latestInTime"
                // fetch both "latestInTime" and "outTime" for "onDate" - after 1st time
                // timings.totalTimetoday(inTime/latestInTime,outTime);
                // after 1st time, also add previous totalTime to newTotalTime
                String totalTimeToday = timings.totalTimetoday();
                // TODO : update totalTimeToday into database against "onDate"
                displayOutTime.setText("OUT TIME - " + outTime + " Hrs");
                displayTotalTime.setText("TOTAL TIME TODAY - " + totalTimeToday + " Hrs");
            } else {
                Toast.makeText(handlerContext, "ERROR : Please restart application", Toast.LENGTH_LONG).show();
            }
        }
    }
}
