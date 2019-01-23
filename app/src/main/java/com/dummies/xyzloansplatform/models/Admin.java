package com.dummies.xyzloansplatform.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.dummies.xyzloansplatform.XYCDbContract;

import java.util.HashMap;

import static com.dummies.xyzloansplatform.Config.db;

/**
 * Created by Mark on 23-Jan-19.
 */

public class Admin {
    public static boolean login(String email, String password){
        boolean success = false;
        String [] LOGIN_WHERE = {email,password};
        Cursor cursor = db.query(XYCDbContract.AdminTable.TABLE_NAME,null, XYCDbContract.CustomerTable.userName + " = ? " + "AND " + XYCDbContract.CustomerTable.password + " = ? ",LOGIN_WHERE,null,null,null);
        if(cursor.getCount() > 0){
            success = true;
        }else {
            Log.e("Retrieve","Retrieval didn't work");
        }

        return success;
    }

    public static long register(HashMap<String,String> registrationDetails){
        long message;
        if(registrationDetails == null || registrationDetails.size() <= 0){
            message = -2;

        }else {
            ContentValues values = new ContentValues();
            values.put(XYCDbContract.AdminTable.userName,registrationDetails.get(XYCDbContract.AdminTable.userName));
            values.put(XYCDbContract.AdminTable.password,registrationDetails.get(XYCDbContract.AdminTable.password));

            message = db.insert(XYCDbContract.AdminTable.TABLE_NAME,null,values);
            Log.e("Retrieve",Long.toString(message));
        }
        return message;
    }
}
