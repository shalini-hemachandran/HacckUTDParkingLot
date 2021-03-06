package com.example.hackutdparkinglotapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

/**
 * Created by hemac on 3/4/2017.
 */
public class RegisterActivity extends AppCompatActivity
{
    private ProgressDialog waitDialog;
    private AlertDialog userDialog;
    String uName;
    String mail;

    String pssword;

    RegisterSignUpHandler registerSignUpHandler = new RegisterSignUpHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);

        Button button = (Button) findViewById(R.id.register);
        button.setOnClickListener(new ButtonListener());

    }


    public class RegisterSignUpHandler implements SignUpHandler
    {

        @Override
        public void onSuccess(CognitoUser cognitoUser, boolean b, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {

            closeWaitDialog();
            Boolean regState = b;
            Helper.add(uName, pssword, mail);

            if (b) {
                // User is already confirmed
                showDialogMessage("Sign up successful!",uName+" has been Confirmed", true);
            }
            else {
                // User is not confirmed
                confirmSignUp(cognitoUserCodeDeliveryDetails);
            }
        }


        @Override
        public void onFailure(Exception e) {

            closeWaitDialog();
            showDialogMessage("Sign up failed",Helper.formatException(e),false);

        }
    }


    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        exit(uName);
                    }
                } catch (Exception e) {
                    if(exit) {
                        exit(uName);
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }

    private void exit(String uname) {
        exit(uname, null);
    }

    private void exit(String uname, String password) {
        Intent intent = new Intent();
        if (uname == null) {
            uname = "";
        }
        if (password == null) {
            password = "";
        }
        intent.putExtra("name", uname);
        intent.putExtra("password", password);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void confirmSignUp(CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
        Intent intent = new Intent(this, SignUpConfirm.class);
        intent.putExtra("source","signup");
        intent.putExtra("name", uName);
        intent.putExtra("destination", cognitoUserCodeDeliveryDetails.getDestination());
        intent.putExtra("deliveryMed", cognitoUserCodeDeliveryDetails.getDeliveryMedium());
        intent.putExtra("attribute", cognitoUserCodeDeliveryDetails.getAttributeName());
        startActivityForResult(intent, 10);
    }

    public class ButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            showWaitDialog("Signing up...");

            EditText userNameTxt = (EditText) findViewById(R.id.userName);
            String userName = userNameTxt.getText().toString();

            uName = userName;

            EditText passTxt = (EditText) findViewById(R.id.password);
            String password = passTxt.getText().toString();

            pssword = password;

            EditText emailTxt = (EditText) findViewById(R.id.email);
            String email = emailTxt.getText().toString();

            mail = email;
            Helper.registerUser(userName, password, email, registerSignUpHandler);
        }
    }


}
