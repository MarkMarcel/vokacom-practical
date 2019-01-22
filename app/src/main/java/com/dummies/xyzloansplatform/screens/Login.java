package com.dummies.xyzloansplatform.screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.dummies.xyzloansplatform.Config;
import com.dummies.xyzloansplatform.R;
import com.dummies.xyzloansplatform.models.Customer;

public class Login extends AppCompatActivity implements View.OnClickListener{

    //bind ui elements
    TextInputEditText email;
    TextInputEditText password;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.textInputEditTextEmail);
        password = findViewById(R.id.textInputEditTextPassword);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);

        //Config
        Config.runConfig(getApplicationContext());

    }

    public void validateInput(){
        String strEmail = email.getText().toString().trim();
        String strPassword = password.getText().toString().trim();

        if(strEmail.length() > 0 && strPassword.length() > 0){
            if(Customer.login(strEmail,strPassword)){
                Intent intentLoan = new Intent(getApplicationContext(), Loans.class);
                startActivity(intentLoan);
            }else{
                Toast.makeText(getApplicationContext(),"Wrong Username or Password",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Enter email or password",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.appCompatButtonLogin:
                validateInput();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(intentRegister);
                break;
        }
    }
}
