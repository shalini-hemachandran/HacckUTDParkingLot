package com.example.hackutdparkinglotapp;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

/**
 * Created by hemac on 3/4/2017.
 */
@DynamoDBTable(tableName = "USER_DETAILS_HACK_UTD")
public class UserDetails
{

    String userName;
    String password;
    String emailId;


    public UserDetails()
    {

    }

    public UserDetails(String userName, String password, String emailId)
    {
        this.userName = userName;
        this.password = password;
        this.emailId = emailId;

    }


    @DynamoDBHashKey(attributeName="userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "emailId")
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
