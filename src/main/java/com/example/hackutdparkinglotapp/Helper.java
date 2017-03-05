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
        cognitoUserPool = new CognitoUserPool(context, "us-east-1_WdWNMN7Nm", "7dp8s4ck2b5jto3f8km2qms8b5", "7hakbv7grhrancdafolp6m03a424acbt2th71lic6lt1muunvic", Regions.US_EAST_1);

        System.out.println(cognitoUserPool+" hiiiiiiiiiiiiiiiiiiiii");
    }


    public static void registerUser(String userName, String password, String email, MainActivity.RegisterSignUpHandler registerSignUpHandler)
    {
        System.out.println("I am hereeeeeeeeee");
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        userAttributes.addAttribute("email",email);

        cognitoUserPool.signUpInBackground(userName, password,userAttributes,null,registerSignUpHandler);

        System.out.println(cognitoUserPool.getUser().getUserId());


        System.out.println("Hiiii Done");
    }


}
