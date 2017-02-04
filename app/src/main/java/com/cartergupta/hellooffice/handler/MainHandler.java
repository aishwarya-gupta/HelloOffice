package com.cartergupta.hellooffice.handler;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cartergupta.hellooffice.R;
import com.cartergupta.hellooffice.commons.CalculateVariousTimings;
import com.cartergupta.hellooffice.database.MonitorMeDatabaseHelper;

/**
 * Created by agupta on 31-Jan-17.
 */

public class MainHandler {
    Context handlerContext;
    MonitorMeDatabaseHelper db;

    public MainHandler(Context context) {
        handlerContext = context;
    }

    public void inOutToggleButtonMainHandler(boolean in, View view) {
        Log.i("CLASS : ", "MainHandler");
        Log.i("METHOD : ", "inOutToggleButtonMainHandler");
        TextView displayInTime = (TextView) view.findViewById(R.id.displayInTime);
        CalculateVariousTimings timings = new CalculateVariousTimings();
        String formattedDateTime = timings.timeNow();
        String[] splittedDateTime = formattedDateTime.split(" ");
        String formattedDate = splittedDateTime[0];
        String formattedTime = splittedDateTime[1];
        if (in) {
            String inTime = formattedTime;
            db = new MonitorMeDatabaseHelper(handlerContext);
            boolean inTimeAvailableResult = db.checkInTimeAvailable(formattedDate);
            if (!(inTimeAvailableResult)) { // insert inTime into database
                boolean inTimeInsertionResult = db.inTimeInsertReport(formattedDate, inTime);
                if (!(inTimeInsertionResult)) {
                    Toast.makeText(handlerContext, "DATABASE ERROR : Please restart application", Toast.LENGTH_LONG).show();
                }
            }
            String fetchedInTime = db.fetchInTime(formattedDate);
            if (fetchedInTime == null) {
                Toast.makeText(handlerContext, "ERROR : Unable to connect to Database", Toast.LENGTH_LONG).show();
            } else {
                displayInTime.setText("    IN TIME - " + fetchedInTime + " Hrs");
            }
        } else {
            TextView displayOutTime = (TextView) view.findViewById(R.id.displayOutTime);
            TextView displayTotalTime = (TextView) view.findViewById(R.id.displayTotalTime);
            String outTime = formattedTime;
            boolean outTimeInsertionResult = db.outTimeInsertReport(formattedDate, outTime);
            if (outTimeInsertionResult) {
                String fetchedInTime = db.fetchInTime(formattedDate);
                String fetchedOutTime = db.fetchOutTime(formattedDate);
                if ((fetchedInTime == null) || (fetchedOutTime == null)) {
                    Toast.makeText(handlerContext, "ERROR : Unable to connect to Database", Toast.LENGTH_LONG).show();
                } else {
                    String totalTimeToday = timings.totalTimetoday(fetchedInTime, fetchedOutTime);
                    displayOutTime.setText("OUT TIME - " + fetchedOutTime + " Hrs");
                    displayTotalTime.setText("TOTAL TIME TODAY - " + totalTimeToday + " Hrs");
                }
            } else {
                Toast.makeText(handlerContext, "ERROR : Please restart application", Toast.LENGTH_LONG).show();
            }
        }
    }
}
