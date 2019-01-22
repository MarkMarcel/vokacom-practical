package com.dummies.xyzloansplatform;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mark on 21-Jan-19.
 */

public final class Config {
    public static XYZDb dbHelper;
    public static SQLiteDatabase db;

    public static void runConfig(Context context){
        // Code to connect db
        dbHelper = new XYZDb(context);
        db = dbHelper.getWritableDatabase();
    }
}
