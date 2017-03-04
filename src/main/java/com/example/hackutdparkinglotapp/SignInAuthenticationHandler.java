package com.example.hackutdparkinglotapp;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

/**
 * Created by hemac on 3/4/2017.
 */
public class SignInAuthenticationHandler implements AuthenticationHandler
{
    @Override
    public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {

    }

    @Override
    public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String UserId) {

    }

    @Override
    public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

    }

    @Override
    public void authenticationChallenge(ChallengeContinuation continuation) {

    }

    @Override
    public void onFailure(Exception exception) {

    }
}
