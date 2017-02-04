package com.cartergupta.hellooffice.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by agupta on 29-Jan-17.
 */

public class MonitorMeDatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FeedReaderContract.FeedEntry.ON_DATE + " TEXT," +
                    FeedReaderContract.FeedEntry.IN_TIME + " TEXT," +
                    FeedReaderContract.FeedEntry.LATEST_IN_TIME + " TEXT," +
                    FeedReaderContract.FeedEntry.OUT_TIME + " TEXT," +
                    FeedReaderContract.FeedEntry.TOTAL_TIME + " TEXT)";

    private static final String SQL_DELETE_TABLE_IF_EXISTS =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public MonitorMeDatabaseHelper(Context context) {
        super(context, FeedReaderContract.FeedEntry.DB_NAME, null, FeedReaderContract.FeedEntry.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_IF_EXISTS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean inTimeInsertReport(String onDate, String inTime) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "inTimeInsertReport");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.ON_DATE, onDate);
        contentValues.put(FeedReaderContract.FeedEntry.IN_TIME, inTime);
        contentValues.put(FeedReaderContract.FeedEntry.LATEST_IN_TIME, inTime);
        contentValues.put(FeedReaderContract.FeedEntry.TOTAL_TIME, "00:00");
        long inTimeInsertionResult = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, contentValues);
        if (inTimeInsertionResult != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean outTimeUpdateReport(String onDate, String outTime) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "outTimeUpdateReport");
        String maxId = fetchMaxId();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.OUT_TIME, outTime);
        String selection = FeedReaderContract.FeedEntry.ON_DATE + " LIKE ? AND _id LIKE ?";
        String[] selectionArgs = {onDate, maxId};
        int outTimeInsertionResult = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
        if (outTimeInsertionResult > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkInTimeAvailable(String onDate) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "checkInTimeAvailable");
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FeedReaderContract.FeedEntry.ON_DATE + " LIKE ?";
        String[] selectionArgs = {onDate};
        long count = DatabaseUtils.queryNumEntries(db, FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    public String fetchInTime(String onDate) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "fetchInTime");
        SQLiteDatabase db = this.getReadableDatabase();
        String inTime = FeedReaderContract.FeedEntry.IN_TIME;
        Cursor dbData = db.rawQuery("SELECT " + inTime + " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE " + FeedReaderContract.FeedEntry.ON_DATE + " = " + "\"" + onDate + "\"", null);
        String fetchedInTime = "";
        if (dbData.moveToFirst()) {
//            do{
            fetchedInTime = dbData.getString(dbData.getColumnIndex("IN_TIME"));
//            }while(fetchedTime.moveToNext());
        }
        return fetchedInTime;
    }

    public String fetchOutTime(String onDate) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "fetchOutTime");
        SQLiteDatabase db = this.getReadableDatabase();
        String outTime = FeedReaderContract.FeedEntry.OUT_TIME;
        String maxId = fetchMaxId();
        Cursor dbData = db.rawQuery("SELECT " + outTime + " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE " + FeedReaderContract.FeedEntry.ON_DATE + " = " + "\"" + onDate + "\" AND _id = " + maxId, null);
        String fetchedOutTime = "";
        if (dbData.moveToFirst()) {
            fetchedOutTime = dbData.getString(dbData.getColumnIndex("OUT_TIME"));
        }
        return fetchedOutTime;
    }

    public boolean latestInTimeUpdateReport(String onDate, String latestInTime) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "latestInTimeInsertReport");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.ON_DATE, onDate);
        contentValues.put(FeedReaderContract.FeedEntry.LATEST_IN_TIME, latestInTime);
        long latestInTimeInsertionResult = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, contentValues);
        if (latestInTimeInsertionResult != -1) {
            return true;
        } else {
            return false;
        }
    }

    public String fetchMaxId() {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "fetchMaxId");
        SQLiteDatabase db = this.getReadableDatabase();
        String id = FeedReaderContract.FeedEntry._ID;
        Cursor dbData = db.rawQuery("SELECT MAX(" + id + ") FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(dbData));
        String fetchedMaxId = "";
        if (dbData.moveToFirst()) {
//            do{
            fetchedMaxId = dbData.getString(0);
//            }while(fetchedTime.moveToNext());
        }
        return fetchedMaxId;
    }

    public String fetchSecondMaxId() {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "fetchSecondMaxId");
        SQLiteDatabase db = this.getReadableDatabase();
        String id = FeedReaderContract.FeedEntry._ID;
        Cursor dbData = db.rawQuery("SELECT MAX(" + id + ") -1 FROM " + FeedReaderContract.FeedEntry.TABLE_NAME, null);
        String fetchedSecondMaxId = "";
        if (dbData.moveToFirst()) {
//            do{
            fetchedSecondMaxId = dbData.getString(0);
//            }while(fetchedTime.moveToNext());
        }
        return fetchedSecondMaxId;
    }

    public String fetchLatestInTime(String onDate) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "fetchLatestInTime");
        SQLiteDatabase db = this.getReadableDatabase();
        String latestInTime = FeedReaderContract.FeedEntry.LATEST_IN_TIME;
        String maxId = fetchMaxId();
        Cursor dbData = db.rawQuery("SELECT " + latestInTime + " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE " + FeedReaderContract.FeedEntry.ON_DATE + " = " + "\"" + onDate + "\" AND _id = " + maxId, null);
        String fetchedLatestInTime = "";
        if (dbData.moveToFirst()) {
//            do{
            fetchedLatestInTime = dbData.getString(dbData.getColumnIndex("LATEST_IN_TIME"));
//            }while(fetchedTime.moveToNext());
        }
        return fetchedLatestInTime;
    }

    public String fetchTotalTimeToday(String onDate) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "fetchTotalTimeToday");
        Cursor dbData;
        SQLiteDatabase db = this.getReadableDatabase();
        String totalTimeToday = FeedReaderContract.FeedEntry.TOTAL_TIME;
        String fetchedTotalTimeToday = "";
        String maxId = fetchMaxId();
        String secondMaxId = fetchSecondMaxId();
        if (secondMaxId.equals("0")) {
            dbData = db.rawQuery("SELECT " + totalTimeToday + " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE " + FeedReaderContract.FeedEntry.ON_DATE + " = " + "\"" + onDate + "\" AND _id = " + maxId, null);
        } else {
            dbData = db.rawQuery("SELECT " + totalTimeToday + " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE " + FeedReaderContract.FeedEntry.ON_DATE + " = " + "\"" + onDate + "\" AND _id = " + secondMaxId, null);
        }
        if (dbData.moveToFirst()) {
//            do{
            fetchedTotalTimeToday = dbData.getString(dbData.getColumnIndex("TOTAL_TIME"));
//            }while(fetchedTime.moveToNext());
        }
        return fetchedTotalTimeToday;
    }

    public boolean totalTimeTodayUpdateReport(String onDate, String totalTimeToday) {
        Log.i("CLASS : ", "MonitorMeDatabaseHelper");
        Log.i("METHOD : ", "totalTimeTodayUpdateReport");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String maxId = fetchMaxId();
        contentValues.put(FeedReaderContract.FeedEntry.TOTAL_TIME, totalTimeToday);
        String selection = FeedReaderContract.FeedEntry.ON_DATE + " LIKE ? AND _id LIKE ?";
        String[] selectionArgs = {onDate, maxId};
        int outTimeInsertionResult = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
        if (outTimeInsertionResult > 0) {
            return true;
        } else {
            return false;
        }
    }
}
