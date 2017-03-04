package com.example.hackutdparkinglotapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog waitDialog;
    private AlertDialog userDialog;
    String userName;
    RegisterSignUpHandler registerSignUpHandler = new RegisterSignUpHandler();
    SignInAuthenticationHandler signInAuthenticationHandler = new SignInAuthenticationHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CognitoUserPool userPool = new CognitoUserPool(getApplicationContext(), "us-east-1:788ffad1-8e12-4157-b748-3786bf0ad3dc", "HackUTDParking", null, Regions.US_EAST_1);

        CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        userAttributes.addAttribute("Name","value");

        showWaitDialog("Signing up...");

        userPool.signUpInBackground("USER_NAME", "PASSWORD",userAttributes,null,registerSignUpHandler);

        CognitoUser user = userPool.getUser("");
        user.getSession(signInAuthenticationHandler);


    }

    public class RegisterSignUpHandler implements SignUpHandler
    {

        @Override
        public void onSuccess(CognitoUser cognitoUser, boolean b, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {

            closeWaitDialog();
            Boolean regState = b;
            if (b) {
                // User is already confirmed
                showDialogMessage("Sign up successful!",userName+" has been Confirmed", true);
            }
            else {
                // User is not confirmed
                confirmSignUp(cognitoUserCodeDeliveryDetails);
            }
        }


        @Override
        public void onFailure(Exception e) {

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
                        exit(userName);
                    }
                } catch (Exception e) {
                    if(exit) {
                        exit(userName);
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
       /* Intent intent = new Intent(this, SignUpConfirm.class);
        intent.putExtra("source","signup");
        intent.putExtra("name", usernameInput);
        intent.putExtra("destination", cognitoUserCodeDeliveryDetails.getDestination());
        intent.putExtra("deliveryMed", cognitoUserCodeDeliveryDetails.getDeliveryMedium());
        intent.putExtra("attribute", cognitoUserCodeDeliveryDetails.getAttributeName());
        startActivityForResult(intent, 10)*/
    }
}
