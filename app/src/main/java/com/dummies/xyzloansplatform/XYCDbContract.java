package com.dummies.xyzloansplatform;

import android.provider.BaseColumns;

/**
 * Created by Mark on 21-Jan-19.
 */

public final class XYCDbContract {
    private XYCDbContract(){}

    public static class CustomerTable implements BaseColumns{
        public static final String TABLE_NAME = "customer";
        public static final String firstName = "F_Name";
        public static final String rowId = "rowid";
        public static final String lastName = "L_Name";
        public static final String maritalStatus= "Marital_Status";
        public static final String employmentStatus= "Employment_Status";
        public static final String employer= "Employer";
        public static final String digitalAddress = "Digital_Address";
        public static final String customerId = "Customer_Id";
        public static final String customerIdNo = "Customer_Id_No";
        public static final String userName = "User_Name";
        public static final String password = "Password";
    }

    public static class LoanTable implements BaseColumns{
        public static final String TABLE_NAME = "loan";
        public static final String row_Id = "rowid";
        public static final String customerId = "Custmer_Id";
        public static final String principal = "Principal";
        public static final String approvedDate = "Approved_Date";
        public static final String amountPaid = "Amount_Paid";
    }

    public static class AdminTable implements BaseColumns{
        public static final String TABLE_NAME = "admin";
        public static final String rowId = "rowid";
        public static final String userName = "User_Name";
        public static final String password = "Password";
    }
}
