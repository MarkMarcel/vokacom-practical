package com.dummies.xyzloansplatform;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mark on 21-Jan-19.
 */

public class XYZDb extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NAME = "xyzdb";

    private String CREATE_CUSTOMER_TABLE = "CREATE TABLE IF NOT EXISTS " + XYCDbContract.CustomerTable.TABLE_NAME  + " ( "+
            XYCDbContract.CustomerTable.rowId +" INTEGER PRIMARY KEY," +
            XYCDbContract.CustomerTable.firstName +" VARCHAR(100) NOT NULL," +
            XYCDbContract.CustomerTable.lastName +" VARCHAR(100) NOT NULL," + XYCDbContract.CustomerTable.maritalStatus +" VARCHAR(20) NOT NULL DEFAULT Single," +
            XYCDbContract.CustomerTable.employmentStatus +" VARCHAR(20) NOT NULL DEFAULT Unemployed," +
            XYCDbContract.CustomerTable.employer +" VARCHAR(100)," +
            XYCDbContract.CustomerTable.digitalAddress +" VARCHAR(20) NOT NULL," +
            XYCDbContract.CustomerTable.customerId +" VARCHAR(20) NOT NULL," +
            XYCDbContract.CustomerTable.customerIdNo +" VARCHAR(50) NOT NULL," +
            XYCDbContract.CustomerTable.userName +" VARCHAR(100) NOT NULL," +
            XYCDbContract.CustomerTable.password +" VARCHAR(100) NOT NULL" +")";

    private String  CREATE_LOAN_TABLE = "CREATE TABLE IF NOT EXISTS " + XYCDbContract.LoanTable.TABLE_NAME +"(" +
            XYCDbContract.LoanTable.row_Id +" INTEGER PRIMARY KEY,"+
            XYCDbContract.LoanTable.customerId + " INTEGER," +
            XYCDbContract.LoanTable.principal + " DOUBLE(10,2) NOT NULL DEFAULT 0.0," +
            XYCDbContract.LoanTable.approvedDate + " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            XYCDbContract.LoanTable.amountPaid + " DOUBLE(10,2) NOT NULL DEFAULT 0.00," +
            "FOREIGN KEY(" + XYCDbContract.LoanTable.customerId +") REFERENCES " + XYCDbContract.CustomerTable.TABLE_NAME + "(" + XYCDbContract.CustomerTable.rowId + ")" + ")";

    public XYZDb(Context context){
        super(context,NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_LOAN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }
}
