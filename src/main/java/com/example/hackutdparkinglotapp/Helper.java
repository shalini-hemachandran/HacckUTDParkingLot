package com.example.hackutdparkinglotapp;

import android.content.Context;
import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hemac on 3/4/2017.
 */
public class Helper
{
    static CognitoUserPool cognitoUserPool;
    static AmazonDynamoDBClient ddb = null;

    public static void init(Context context)
    {
        cognitoUserPool = new CognitoUserPool(context, "us-east-1_WdWNMN7Nm", "7dp8s4ck2b5jto3f8km2qms8b5", "7hakbv7grhrancdafolp6m03a424acbt2th71lic6lt1muunvic", Regions.US_EAST_1);
        CognitoCachingCredentialsProvider credentials = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:788ffad1-8e12-4157-b748-3786bf0ad3dc",
                Regions.US_EAST_1);

        ddb = new AmazonDynamoDBClient(credentials);
        ddb.setRegion(Region.getRegion(Regions.US_EAST_1));
        //createTable();
        System.out.println(cognitoUserPool+" hiiiiiiiiiiiiiiiiiiiii" + ddb);
    }


  /*  public static void createTable() {

        List<AttributeDefinition> attributeDefinitionList = new ArrayList<>();


        AttributeDefinition ad = new AttributeDefinition().withAttributeName(
                "password").withAttributeType(ScalarAttributeType.S);
        AttributeDefinition ad1 = new AttributeDefinition().withAttributeName(
                "emailId").withAttributeType(ScalarAttributeType.S);

        attributeDefinitionList.add(ad);
        attributeDefinitionList.add(ad1);

        List<KeySchemaElement> keySchemaElements = new ArrayList<>();
        KeySchemaElement kse = new KeySchemaElement().withAttributeName(
                "userName").withKeyType(KeyType.HASH);

        keySchemaElements.add(kse);




        ProvisionedThroughput pt = new ProvisionedThroughput()
                .withReadCapacityUnits(10l).withWriteCapacityUnits(5l);




        try {
            System.out.println("Sending Create table request");
            ddb.createTable(attributeDefinitionList,"USER_DETAILS_HACK",keySchemaElements,pt);
            System.out.println("Create request response successfully recieved");
        } catch (AmazonServiceException ex) {
            ex.printStackTrace();

        }
    }
*/


    public static void registerUser(String userName, String password, String email, MainActivity.RegisterSignUpHandler registerSignUpHandler)
    {
        System.out.println("I am hereeeeeeeeee");
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        userAttributes.addAttribute("email",email);

        cognitoUserPool.signUpInBackground(userName, password,userAttributes,null,registerSignUpHandler);

        System.out.println(cognitoUserPool.getUser().getUserId());

        UserDetails userDetails = new UserDetails(userName,password,email);
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        mapper.save(userDetails);

        System.out.println("saved");

        System.out.println("Hiiii Done");
    }


    public static String formatException(Exception e) {
        String formattedString = "Internal Error";
        Log.e("App Error",e.toString());
        Log.getStackTraceString(e);

        String temp = e.getMessage();

        if(temp != null && temp.length() > 0) {
            formattedString = temp.split("\\(")[0];
            if(temp != null && temp.length() > 0) {
                return formattedString;
            }
        }

        return  formattedString;
    }
}
