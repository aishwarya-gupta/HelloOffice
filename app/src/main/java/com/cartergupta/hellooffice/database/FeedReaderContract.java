package com.cartergupta.hellooffice.database;

import android.provider.BaseColumns;

/**
 * Created by agupta on 29-Jan-17.
 */

public class FeedReaderContract {
    private FeedReaderContract() {

    }

    public static class FeedEntry implements BaseColumns {
        public static final String DB_NAME = "MONITOR_ME.db";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "STATUS";
        public static final String ON_DATE = "onDate";
        public static final String IN_TIME = "inTime";
        public static final String OUT_TIME = "outTime";
        public static final String TOTAL_TIME = "totalTime";
    }
}
