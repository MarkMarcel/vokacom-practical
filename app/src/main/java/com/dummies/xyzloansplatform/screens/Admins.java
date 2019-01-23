package com.dummies.xyzloansplatform.screens;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
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

import static com.dummies.xyzloansplatform.Config.db;

public class Admins extends AppCompatActivity {

    private ArrayList<HashMap<String,String>> customersArray = new ArrayList<>();
    ArrayList<Loan> customerLoans = new ArrayList<>();
    private LinearLayout llCustomers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        llCustomers = findViewById(R.id.llcustomer);
        getCustomers();
    }

    private void getCustomers(){
        Cursor cursor = db.query(XYCDbContract.CustomerTable.TABLE_NAME,null,null,null,null,null, XYCDbContract.CustomerTable.firstName);
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                HashMap<String,String> fillMap = new HashMap<>();

                fillMap.put(XYCDbContract.CustomerTable.rowId,Long.toString(cursor.getLong(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.rowId))));
                fillMap.put(XYCDbContract.CustomerTable.firstName,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.firstName)));
                fillMap.put(XYCDbContract.CustomerTable.lastName,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.lastName)));
                fillMap.put(XYCDbContract.CustomerTable.digitalAddress,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.digitalAddress)));
                fillMap.put(XYCDbContract.CustomerTable.employmentStatus,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.employmentStatus)));
                fillMap.put(XYCDbContract.CustomerTable.userName,cursor.getString(cursor.getColumnIndexOrThrow(XYCDbContract.CustomerTable.userName)));
                customersArray.add(fillMap);
            }
            setData();
        }else {
            Toast.makeText(getApplicationContext(),"Wrong Username or Password",Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        LayoutInflater inflater = getLayoutInflater();
        final Context context = this;

        if(customersArray.size() > 0){
            for(int i = 0; i < customersArray.size(); i++){
                final View view = inflater.inflate(R.layout.rowadmincustomers, llCustomers,false);
                view.setTag(i);
                AppCompatTextView name = view.findViewById(R.id.name);
                AppCompatTextView digitalAddress = view.findViewById(R.id.digitalAddress);
                AppCompatTextView employment = view.findViewById(R.id.employment);
                AppCompatTextView email = view.findViewById(R.id.email);
                AppCompatButton btnLoans = view.findViewById(R.id.appCompatButtonLoans);
                HashMap<String,String> details = customersArray.get(i);

                name.setText(details.get(XYCDbContract.CustomerTable.firstName) + " " + details.get(XYCDbContract.CustomerTable.firstName));
                digitalAddress.setText(details.get(XYCDbContract.CustomerTable.digitalAddress));
                employment.setText(details.get(XYCDbContract.CustomerTable.employmentStatus));
                email.setText(details.get(XYCDbContract.CustomerTable.userName));

                btnLoans.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      int tag = (int) view.getTag();
                      Customer.rowId = Long.parseLong(customersArray.get(tag).get(XYCDbContract.CustomerTable.rowId));
                      getCustomerLoans();
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.activity_loans);
                        LinearLayout customerLoansLl;
                        dialog.findViewById(R.id.customer).setVisibility(View.GONE);
                        dialog.findViewById(R.id.apply).setVisibility(View.GONE);
                        AppCompatTextView tvYour = dialog.findViewById(R.id.yourloan);
                        tvYour.setText("Customer Loans");
                        customerLoansLl = dialog.findViewById(R.id.customerloan);

                        LayoutInflater inflater = getLayoutInflater();

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
                            Toast.makeText(getApplicationContext(),"No loans for this customer",Toast.LENGTH_LONG).show();
                        }

                        dialog.show();
                    }
                });

                llCustomers.addView(view);
            }
        }
    }

    private void getCustomerLoans(){
        customerLoans = Customer.getLoans();
    }


}
