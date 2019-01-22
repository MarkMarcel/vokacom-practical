package com.dummies.xyzloansplatform.models;

import android.content.ContentValues;
import android.util.Log;

import com.dummies.xyzloansplatform.XYCDbContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.dummies.xyzloansplatform.Config.db;



/**
 * Created by Mark on 21-Jan-19.
 */

public class Loan {
    //QUERY STRINGS
    private String PAY_SELECTION = XYCDbContract.LoanTable.row_Id + "LIKE ?";
    private HashMap<String,String> loanDetails = new HashMap<>();

    //Loan data
    long row;

    private static final String dateFormat = "dd-MM-yyyy kk:mm:ss";

    public HashMap<String,String> getLoanDetails(){
        return loanDetails;

    }

    public long loanApply(HashMap<String,String> details){

        if(details == null || details.size() <= 0){
            row = -2;

        }else {
            ContentValues values = new ContentValues();
            values.put(XYCDbContract.LoanTable.customerId,Integer.parseInt(details.get(XYCDbContract.LoanTable.customerId)));
            values.put(XYCDbContract.LoanTable.principal,Double.parseDouble(details.get(XYCDbContract.LoanTable.principal)));
            try{

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                String strDate = simpleDateFormat.format(date);
                values.put(XYCDbContract.LoanTable.approvedDate,strDate);

            }catch (Exception e){
                Log.e("parsing date", "couldn't parse loan apply");
            }


            row = db.insert(XYCDbContract.LoanTable.TABLE_NAME,null,values);

        }
        return row;
    }
    public void setLoanDetails(HashMap<String,String> loanDetails1){
        loanDetails = loanDetails1;
    }

    public int payLoan(Double amount){
        String [] selectionArgs = {loanDetails.get(XYCDbContract.LoanTable.row_Id)};
        ContentValues values = new ContentValues();
        values.put(XYCDbContract.LoanTable.amountPaid, amount);

        int count = db.update(XYCDbContract.LoanTable.TABLE_NAME,values,PAY_SELECTION,selectionArgs);
        return count;
    }


}
