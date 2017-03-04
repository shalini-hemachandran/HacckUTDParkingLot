package com.example.hackutdparkinglotapp;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

/**
 * Created by hemac on 3/4/2017.
 */
public class Helper
{
    static CognitoUserPool cognitoUserPool;
    public static void init(Context context)
    {
        cognitoUserPool = new CognitoUserPool(context, "us-east-1:788ffad1-8e12-4157-b748-3786bf0ad3dc", "HackUTDParking", null, Regions.US_EAST_1);
    }


    public static void registerUser(String userName, String password, String email, MainActivity.RegisterSignUpHandler registerSignUpHandler)
    {
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        userAttributes.addAttribute("email",email);
        cognitoUserPool.signUpInBackground(userName, password,userAttributes,null,registerSignUpHandler);

    }


}
