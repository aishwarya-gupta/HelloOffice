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
        TextView displayOutTime = (TextView) view.findViewById(R.id.displayOutTime);
        TextView displayTotalTime = (TextView) view.findViewById(R.id.displayTotalTime);
        CalculateVariousTimings timings = new CalculateVariousTimings();
        String formattedDateTime = timings.timeNow();
        String[] splittedDateTime = formattedDateTime.split(" ");
        String formattedDate = splittedDateTime[0];
        String formattedTime = splittedDateTime[1];
        if (in) {
            displayTotalTime.setText("");
            displayOutTime.setText("");
            String inTime = formattedTime;
            displayInTime.setText("    IN TIME - " + inTime + " Hrs");
            db = new MonitorMeDatabaseHelper(handlerContext);
            boolean inTimeAvailableResult = db.checkInTimeAvailable(formattedDate);
            if (!(inTimeAvailableResult)) {
                boolean inTimeInsertionResult = db.inTimeInsertReport(formattedDate, inTime);
                if (!(inTimeInsertionResult)) {
                    Toast.makeText(handlerContext, "DATABASE ERROR : Please restart application", Toast.LENGTH_LONG).show();
                }
            } else {
                boolean latestInTimeInsertionResult = db.latestInTimeInsertReport(formattedDate, inTime);
                if (!(latestInTimeInsertionResult)) {
                    Toast.makeText(handlerContext, "DATABASE ERROR : Please restart application", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            String outTime = formattedTime;
            displayOutTime.setText("OUT TIME - " + outTime + " Hrs");
            boolean outTimeUpdationResult = db.outTimeUpdateReport(formattedDate, outTime);
            if (outTimeUpdationResult) {
                String fetchedLatestInTime = db.fetchLatestInTime(formattedDate);
                String fetchedOutTime = db.fetchOutTime(formattedDate);
                if ((fetchedLatestInTime != null) && (fetchedOutTime != null)) {
                    String totalTimeLatestInterval = timings.timeIntervalCalculator(fetchedLatestInTime, fetchedOutTime);
                    String fetchedTotalTimeToday = db.fetchTotalTimeToday(formattedDate);
                    String totalTimeToday = timings.totalTimeCalculator(totalTimeLatestInterval, fetchedTotalTimeToday);
                    displayTotalTime.setText("TOTAL TIME TODAY - " + totalTimeToday + " Hrs");
                    boolean totalTimeTodayUpdationResult = db.totalTimeTodayUpdateReport(formattedDate, totalTimeToday);
                    if (!(totalTimeTodayUpdationResult)) {
                        Toast.makeText(handlerContext, "DATABASE ERROR : Please restart application", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(handlerContext, "DATABASE ERROR : Please restart application", Toast.LENGTH_LONG).show();
            }
        }
    }
}
