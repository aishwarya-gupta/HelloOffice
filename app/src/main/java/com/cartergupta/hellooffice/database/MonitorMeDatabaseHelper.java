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
        Log.i("CLASS : ","MonitorMeDatabaseHelper");
        Log.i("METHOD : ","inTimeInsertReport");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.ON_DATE, onDate);
        contentValues.put(FeedReaderContract.FeedEntry.IN_TIME, inTime);
        long inTimeInsertionResult = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, contentValues);
        return inTimeInsertionResult != -1;
    }

    public boolean outTimeInsertReport(String onDate, String outTime) {
        Log.i("CLASS : ","MonitorMeDatabaseHelper");
        Log.i("METHOD : ","outTimeInsertReport");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.OUT_TIME, outTime);
        String selection = FeedReaderContract.FeedEntry.ON_DATE + " LIKE ?";
        String[] selectionArgs = {onDate};
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
        Log.i("CLASS : ","MonitorMeDatabaseHelper");
        Log.i("METHOD : ","checkInTimeAvailable");
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
        Log.i("CLASS : ","MonitorMeDatabaseHelper");
        Log.i("METHOD : ","fetchInTime");
        SQLiteDatabase db = this.getReadableDatabase();
        String inTime = FeedReaderContract.FeedEntry.IN_TIME;
        Cursor dbData = db.rawQuery("SELECT " + inTime + " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE " + FeedReaderContract.FeedEntry.ON_DATE + " = " + "\"" + onDate + "\"", null);
        String fetchedInTime = "";
        if (dbData.moveToFirst()) {
//            do{
            fetchedInTime = dbData.getString(dbData.getColumnIndex("inTime"));
//            }while(fetchedTime.moveToNext());
        }
        return fetchedInTime;
    }

    public String fetchOutTime(String onDate) {
        Log.i("CLASS : ","MonitorMeDatabaseHelper");
        Log.i("METHOD : ","fetchOutTime");
        SQLiteDatabase db = this.getReadableDatabase();
        String outTime = FeedReaderContract.FeedEntry.OUT_TIME;
        Cursor dbData = db.rawQuery("SELECT " + outTime + " FROM " + FeedReaderContract.FeedEntry.TABLE_NAME + " WHERE " + FeedReaderContract.FeedEntry.ON_DATE + " = " + "\"" + onDate + "\"", null);
        String fetchedOutTime = "";
        if (dbData.moveToFirst()) {
//            do{
            fetchedOutTime = dbData.getString(dbData.getColumnIndex("outTime"));
//            }while(fetchedTime.moveToNext());
        }
        return fetchedOutTime;
    }
}
