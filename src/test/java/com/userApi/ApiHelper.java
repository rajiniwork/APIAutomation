package com.userApi;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

public class ApiHelper {

    RequestSpecification httpRequest;

    public String BaseURL(String endPoint)
    {
        return "https://gorest.co.in/public/v2/users" + endPoint;
    }
}
