package com.dummies.xyzloansplatform.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.dummies.xyzloansplatform.XYCDbContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.dummies.xyzloansplatform.Config.db;

/**
 * Created by Mark on 21-Jan-19.
 */

public class Customer {
    //Customer Data Variables
    public static long rowId;
    public static HashMap<String,String> customerInfo = new HashMap<>();
    private static ArrayList<Loan> customerLoans = new ArrayList<>();

    //Constants
    public static String SINGLE = "Single";
    public static String MARRIED = "Married";
    public static String DIVORCED = "Divorced";
    public static String EMPLOYED = "Employed";
    public static String UNEMPLOYED = "Unemployed";
    private static final String dateFormat = "dd-mm-yyyy";

    public static String[] FETCH_COLUMNS = {XYCDbContract.CustomerTable.firstName,XYCDbContract.CustomerTable.lastName,XYCDbContract.CustomerTable.maritalStatus,
            XYCDbContract.CustomerTable.employmentStatus,XYCDbContract.CustomerTable.employer,XYCDbContract.CustomerTable.digitalAddress,XYCDbContract.CustomerTable.customerIdNo,
            XYCDbContract.CustomerTable.customerIdNo,XYCDbContract.CustomerTable.userName,XYCDbContract.CustomerTable.password};
    public static String[] FETCH_WHERE ={Long.toString(rowId)};

    public static String[] LOGIN_DETAILS = {XYCDbContract.CustomerTable.rowId,XYCDbContract.CustomerTable.userName,XYCDbContract.CustomerTable.password};


    public static long register(HashMap<String,String> registrationDetails){
        long message;
        if(registrationDetails == null || registrationDetails.size() <= 0){
            message = -2;

        }else {
            ContentValues values = new ContentValues();
            values.put(XYCDbContract.CustomerTable.firstName,registrationDetails.get(XYCDbContract.CustomerTable.firstName));
            values.put(XYCDbContract.CustomerTable.lastName,registrationDetails.get(XYCDbContract.CustomerTable.lastName));
            values.put(XYCDbContract.CustomerTable.maritalStatus,registrationDetails.get(XYCDbContract.CustomerTable.maritalStatus));
            values.put(XYCDbContract.CustomerTable.employmentStatus,registrationDetails.get(XYCDbContract.CustomerTable.employmentStatus));
            values.put(XYCDbContract.CustomerTable.employer,registrationDetails.get(XYCDbContract.CustomerTable.employer));
            values.put(XYCDbContract.CustomerTable.digitalAddress,registrationDetails.get(XYCDbContract.CustomerTable.digitalAddress));
            values.put(XYCDbContract.CustomerTable.customerId,registrationDetails.get(XYCDbContract.CustomerTable.customerId));
            values.put(XYCDbContract.CustomerTable.customerIdNo,registrationDetails.get(XYCDbContract.CustomerTable.customerIdNo));
            values.put(XYCDbContract.CustomerTable.userName,registrationDetails.get(XYCDbContract.CustomerTable.userName));
            values.put(XYCDbContract.CustomerTable.password,registrationDetails.get(XYCDbContract.CustomerTable.password));

            message = db.insert(XYCDbContract.CustomerTable.TABLE_NAME,null,values);
            rowId = message;
            Log.e("Retrieve",Long.toString(message));
        }
        return message;
    }

    public static boolean login(String email, String password){
        boolean success = false;
        String [] LOGIN_WHERE = {email,password};
        Cursor cursor = db.query(XYCDbContract.CustomerTable.TABLE_NAME,null, XYCDbContract.CustomerTable.userName + " = ? " + "AND " + XYCDbContract.CustomerTable.userName + " = ? ",LOGIN_WHERE,null,null,null);
        if(cursor.getCount() > 0){


            if(cursor.moveToNext()){
                rowId = cursor.getInt(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.rowId));
                customerInfo.put(XYCDbContract.CustomerTable.firstName,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.firstName)));
                customerInfo.put(XYCDbContract.CustomerTable.lastName,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.firstName)));
                customerInfo.put(XYCDbContract.CustomerTable.maritalStatus,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.maritalStatus)));
                customerInfo.put(XYCDbContract.CustomerTable.employer,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.employer)));
                customerInfo.put(XYCDbContract.CustomerTable.employmentStatus,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.employmentStatus)));
                customerInfo.put(XYCDbContract.CustomerTable.digitalAddress,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.digitalAddress)));
                customerInfo.put(XYCDbContract.CustomerTable.customerId,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.customerId)));
                customerInfo.put(XYCDbContract.CustomerTable.customerIdNo,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.customerIdNo)));
                customerInfo.put(XYCDbContract.CustomerTable.userName,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.userName)));
                customerInfo.put(XYCDbContract.CustomerTable.password,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.password)));
            }

            if(customerInfo.get(XYCDbContract.CustomerTable.password).length() > 0){
                cursor.close();
                success = true;

            }else {
                Log.e("Retrieve","Retrieval didn't work");
                success = false;
            }
        }else {
            //Log.e("Retrieve","Retrieval didn't work");
        }

        return success;
    }

    public void logout(){

    }

    public static ArrayList<Loan> getLoans(){

        customerLoans.clear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String [] selectionArgs = {Long.toString(rowId)};
        Cursor cursor = db.query(XYCDbContract.LoanTable.TABLE_NAME,null, XYCDbContract.LoanTable.row_Id + " = ?" ,selectionArgs,null,null,null);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Loan fillLoan = new Loan();
                HashMap<String,String> fillMap = new HashMap<>();

                fillMap.put(XYCDbContract.LoanTable.row_Id,Long.toString(cursor.getLong(cursor.getColumnIndexOrThrow(XYCDbContract.LoanTable.row_Id))));
                fillMap.put(XYCDbContract.LoanTable.customerId,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.LoanTable.customerId)));
                fillMap.put(XYCDbContract.LoanTable.principal,Double.toString(cursor.getDouble(cursor.getColumnIndexOrThrow(XYCDbContract.LoanTable.principal))));
                String strDate = cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.LoanTable.approvedDate));
                try{
                    Date date = simpleDateFormat.parse(strDate);
                    fillMap.put(XYCDbContract.LoanTable.approvedDate,simpleDateFormat.format(date));
                    Calendar loanDate = Calendar.getInstance();
                    loanDate.setTime(date);

                    Calendar today = Calendar.getInstance();

                    Double rate =  0.0083;
                    //Date todayDate = today.getTime();
                    int numberOfMonths = 0;
                    int n = today.get(Calendar.YEAR) - loanDate.get(Calendar.YEAR);
                    numberOfMonths += (n * 12);
                    numberOfMonths += (today.get(Calendar.YEAR) - loanDate.get(Calendar.YEAR));

                    Double interest;
                    if(numberOfMonths <= 0){
                        interest =0.00;
                    }else {
                        interest = Double.parseDouble(fillMap.get(XYCDbContract.LoanTable.principal))* Math.pow(rate,numberOfMonths);
                    }

                    fillMap.put("Interest", Double.toString(interest));

                }catch (Exception e){
                    Log.e("parsing date", "couldn't parse");
                }


                fillMap.put(XYCDbContract.LoanTable.amountPaid,Double.toString(cursor.getDouble(cursor.getColumnIndexOrThrow(XYCDbContract.LoanTable.amountPaid))));
                fillLoan.setLoanDetails(fillMap);
                customerLoans.add(fillLoan);
            }
        }else {

        }
        return customerLoans;
    }
}
