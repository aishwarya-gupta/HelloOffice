package com.cartergupta.hellooffice.database;

import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by agupta on 29-Jan-17.
 */

public class FeedReaderContract {
    public static class FeedEntry implements BaseColumns {
        public static final String DB_NAME = "MONITOR_ME.db";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "STATUS";
        public static final String ON_DATE = "ON_DATE";
        public static final String IN_TIME = "IN_TIME";
        public static final String LATEST_IN_TIME = "LATEST_IN_TIME";
        public static final String OUT_TIME = "OUT_TIME";
        public static final String TOTAL_TIME = "TOTAL_TIME";
    }
}
