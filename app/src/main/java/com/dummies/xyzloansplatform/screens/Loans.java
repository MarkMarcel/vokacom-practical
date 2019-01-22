package com.dummies.xyzloansplatform.screens;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dummies.xyzloansplatform.R;
import com.dummies.xyzloansplatform.XYCDbContract;
import com.dummies.xyzloansplatform.models.Customer;
import com.dummies.xyzloansplatform.models.Loan;

import java.util.ArrayList;
import java.util.HashMap;

public class Loans extends AppCompatActivity implements View.OnClickListener{
    //UI elements
    LinearLayout customer, customerLoansLl;
    AppCompatButton apply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans);

        initUI();
        loadCustomer();
        loadCustomerLoans();
    }

    private void initUI(){
        customer = findViewById(R.id.customer);
        customerLoansLl = findViewById(R.id.customerloan);
        apply = findViewById(R.id.apply);

        apply.setOnClickListener(this);
    }
    private void loadCustomer(){
        LayoutInflater inflater = getLayoutInflater();

        for(int i = 0; i < 4; i++){
            View view = inflater.inflate(R.layout.rowcustomer, customer,false);
            AppCompatTextView label = view.findViewById(R.id.label);
            AppCompatTextView value = view.findViewById(R.id.value);
            String strLabel;
            String strValue;

            switch (i){
                case 0:
                    strLabel = "Name: ";
                    strValue = Customer.customerInfo.get(XYCDbContract.CustomerTable.firstName) + " " + Customer.customerInfo.get(XYCDbContract.CustomerTable.firstName);
                    label.setText(strLabel);
                    value.setText(strValue);
                    customer.addView(view);
                    break;
                case 1:
                    strLabel = "Address: ";
                    strValue = Customer.customerInfo.get(XYCDbContract.CustomerTable.digitalAddress);
                    label.setText(strLabel);
                    value.setText(strValue);
                    customer.addView(view);
                    break;
                case 2:
                    strLabel = "Marital Status: ";
                    strValue = Customer.customerInfo.get(XYCDbContract.CustomerTable.maritalStatus);
                    label.setText(strLabel);
                    value.setText(strValue);
                    customer.addView(view);
                    break;
                case 3:
                    strLabel = "Employment Status: ";
                    strValue = Customer.customerInfo.get(XYCDbContract.CustomerTable.employmentStatus);
                    label.setText(strLabel);
                    value.setText(strValue);
                    customer.addView(view);
                    break;
            }
        }
        Toast.makeText(getApplicationContext(),Long.toString(Customer.rowId),Toast.LENGTH_LONG).show();
    }

    private void loadCustomerLoans(){
        LayoutInflater inflater = getLayoutInflater();

        ArrayList<Loan> customerLoans = Customer.getLoans();
        if(customerLoans.size() > 0){
            for(int i = 0; i < customerLoans.size(); i++){
                View view = inflater.inflate(R.layout.rowloans, customerLoansLl,false);
                AppCompatTextView date = view.findViewById(R.id.date);
                AppCompatTextView principal = view.findViewById(R.id.principal);
                AppCompatTextView interest = view.findViewById(R.id.interest);
                AppCompatTextView amount = view.findViewById(R.id.amountpaid);
                HashMap<String,String> details = customerLoans.get(i).getLoanDetails();

                date.setText(details.get(XYCDbContract.LoanTable.approvedDate));
                principal.setText(details.get(XYCDbContract.LoanTable.principal));
                amount.setText(details.get(XYCDbContract.LoanTable.amountPaid));
                interest.setText(details.get("Interest"));

                customerLoansLl.addView(view);
            }
        }else{
            Toast.makeText(getApplicationContext(),"You don't have any loans",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.apply:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.applyforloan);
                AppCompatButton dialogButton =  dialog.findViewById(R.id.appCompatButtonApply);
                final TextInputEditText etAmount =  dialog.findViewById(R.id.loanamount);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strAmount = etAmount.getText().toString().trim();

                        if(strAmount.length() <= 0){
                            Toast.makeText(getApplicationContext(),"Enter Amount",Toast.LENGTH_SHORT).show();
                            return;
                        }else if(Double.parseDouble(strAmount) <= 0){
                            Toast.makeText(getApplicationContext(),"Please enter valid amount",Toast.LENGTH_SHORT).show();
                        }else {
                            HashMap<String, String> sendLoan = new HashMap<>();
                            sendLoan.put(XYCDbContract.LoanTable.principal,strAmount);
                            sendLoan.put(XYCDbContract.LoanTable.customerId,Long.toString(Customer.rowId));
                            Loan application = new Loan();
                            application.loanApply(sendLoan);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }

        }

}
