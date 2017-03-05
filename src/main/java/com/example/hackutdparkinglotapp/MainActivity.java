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
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

public class MainActivity extends AppCompatActivity  {

    SignInAuthenticationHandler signInAuthenticationHandler = new SignInAuthenticationHandler();
    private ProgressDialog waitDialog;
    private AlertDialog userDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Helper.init(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText userNameTxt = (EditText) findViewById(R.id.userName);
        String userName = userNameTxt.getText().toString();



        EditText passTxt = (EditText) findViewById(R.id.password);
        String password = passTxt.getText().toString();

        Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new LoginButtonListener(userName, password));


        Button regButton = (Button) findViewById(R.id.regButton);
        regButton.setOnClickListener(new RegisterButtonListener());




/*

        CognitoUser user = userPool.getUser("");
        user.getSession(signInAuthenticationHandler);
*/


    }

    private class RegisterButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {


            callRegister();



        }
    }

    private void callRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 10);
    }


    public class SignInAuthenticationHandler implements AuthenticationHandler
    {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {

            openHomePage();



        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String UserId) {

            closeWaitDialog();
            getUserAuthentication(authenticationContinuation);
            System.out.println("HIIIII GET AUTH DETAILS");
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

            System.out.println("HIIIII MFA CODE");
        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

            System.out.println("HIIIII AUTHENTICATION CHALLENGE");
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Sign-in failed", Helper.formatException(exception), true);
        }
    }

    private void getUserAuthentication(AuthenticationContinuation authenticationContinuation) {
        String username =  ((EditText) (findViewById(R.id.userName))).toString();
        String password =  ((EditText) (findViewById(R.id.password))).toString();
        if(password == null) {
            ((EditText) (findViewById(R.id.userName))).setText(username);
            password = ((EditText) findViewById(R.id.password)).getText().toString();
            if(password == null) {
                showDialogMessage("Password Required", "Password field cannot be empty", true);
                return;
            }

        }
        AuthenticationDetails authenticationDetails = new AuthenticationDetails(((EditText) (findViewById(R.id.userName))).toString(), password, null);
        authenticationContinuation.setAuthenticationDetails(authenticationDetails);
        authenticationContinuation.continueTask();
    }

    private void openHomePage() {

        System.out.println("TRYING TO OPEN HOME PAGE");
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivityForResult(intent, 10);

    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }
    private class LoginButtonListener implements View.OnClickListener {
        String userName;
        String password;
        public LoginButtonListener(String userName, String passTxt) {

            this.userName = userName;
            this.password = passTxt;
        }

        @Override
        public void onClick(View v)
        {

            Helper.authenticateUser(userName, password, signInAuthenticationHandler);

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
                        exit("");
                    }
                } catch (Exception e) {
                    if(exit) {
                        exit("");
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
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


}
