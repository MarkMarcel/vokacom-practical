package com.dummies.xyzloansplatform.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.dummies.xyzloansplatform.R;
import com.dummies.xyzloansplatform.XYCDbContract;
import com.dummies.xyzloansplatform.models.Customer;

import java.util.HashMap;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText fName;
    private TextInputEditText lName;
    private TextInputEditText dAddress;
    private TextInputEditText phone;
    private TextInputEditText mStatus;
    private TextInputEditText eStatus;
    private TextInputEditText idType;
    private TextInputEditText idNo;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText cPassword;

    private AppCompatButton appCompatButton;
    private AppCompatTextView textViewLink;
    //private TextInputEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initUI(){
        fName = findViewById(R.id.fname);
        lName = findViewById(R.id.lName);
        dAddress = findViewById(R.id.digitalAddress);
        phone = findViewById(R.id.phone);
        mStatus = findViewById(R.id.marriage);
        eStatus = findViewById(R.id.employment);
        idType = findViewById(R.id.type);
        idNo = findViewById(R.id.no);
        email = findViewById(R.id.textInputEditTextEmail);
        password = findViewById(R.id.textInputEditTextPassword);
        cPassword = findViewById(R.id.textInputEditTextConfirmPassword);
        appCompatButton = findViewById(R.id.appCompatButtonRegister);
        textViewLink = findViewById(R.id.appCompatTextViewLoginLink);

        appCompatButton.setOnClickListener(this);
        textViewLink.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonRegister:
                validate();
                break;
        }
    }

    private void validate(){
        String strFName = fName.getText().toString().trim();
        String strLName = lName.getText().toString().trim();
        String strAddress = dAddress.getText().toString().trim();
        String strMStatus = mStatus.getText().toString().trim();
        String strEStatus = eStatus.getText().toString().trim();
        String strType = idType.getText().toString().trim();
        String strIdNo = idNo.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strPassword = password.getText().toString().trim();
        String strCPassword = cPassword.getText().toString().trim();

        if(strFName.length() <= 0 || strLName.length() <= 0 || strAddress.length() <= 0 || strMStatus.length() <= 0 || strEStatus.length() <= 0 || strType.length() <= 0 ||
                strIdNo.length() <= 0 || strEmail.length() <= 0 || strPassword.length() <= 0 ||strCPassword.length() <= 0){
            Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!strCPassword.equals(strPassword)){
            Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
        }else {
            HashMap<String,String> registrationDetails = new HashMap<>();
            registrationDetails.put(XYCDbContract.CustomerTable.firstName,strFName);
            registrationDetails.put(XYCDbContract.CustomerTable.lastName,strLName);
            registrationDetails.put(XYCDbContract.CustomerTable.digitalAddress,strAddress);
            registrationDetails.put(XYCDbContract.CustomerTable.maritalStatus,strMStatus);
            registrationDetails.put(XYCDbContract.CustomerTable.employmentStatus,strEStatus);
            registrationDetails.put(XYCDbContract.CustomerTable.employer,"Employer");
            registrationDetails.put(XYCDbContract.CustomerTable.customerId,strType);
            registrationDetails.put(XYCDbContract.CustomerTable.customerIdNo,strIdNo);
            registrationDetails.put(XYCDbContract.CustomerTable.userName,strEmail);
            registrationDetails.put(XYCDbContract.CustomerTable.password,strPassword);

            long reg = Customer.register(registrationDetails);
            if(reg != 2){
                Toast.makeText(getApplicationContext(),"succesful",Toast.LENGTH_SHORT).show();
                Intent intentLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(intentLogin);

            }else {
                Toast.makeText(getApplicationContext(),"unsuccesful",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
