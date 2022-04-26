/*
 * Author: Hemant Choudhari
 * summary: Test Case 1 Register User
 * Date: 09/14/2019
 */

/******************************************************
Test Register new record in database 
URI:   https://api2.fox.com/v2.0/register 
Request Type: POST
Request Payload(Body): 
{
"email":"hemant17@fox.com", //Please update this email id when you run each time
"password":"aaaaaa",
"gender": "M",
"firstName": "Hemant",
"lastName": "Test"
}

********* Validations **********
Response Payload(Body) : 
{
"email":"hemantc091@fox.com",
"password":"aaaaaa",
"gender": "M",
"firstName": "Hemant",
"lastName": "Test"
}

Status Code : 200
Status Line : HTTP/1.1 200 OK
Content Type : application/json
Content Encoding : gzip

**********************************************************/

package com.userApi.testCases;

import com.userApi.ApiHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;
import com.userApi.base.TestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class UserTests extends TestBase{

	String bearerToken = "6030007dcf420e221cd57f71d676765f4364fc94697dbb3e22b0725896f7a16d";
	ApiHelper apiHelper = new ApiHelper();;

	@Test
	void searchForUsersAndVerifyResultsAreReturnedAsExpected()
	{
		logger.info("********* Started TC001_searchForCatsFactsAnd_VerifyResultsAreReturnedAsExpected **********");

		given().
				when().
				get(apiHelper.BaseURL("")).
				then().log().all().
				assertThat().
				statusCode(200).
				and().
				body(".", hasSize(20));

		logger.info("********* Finished TC001_searchForCatsFactsAnd_VerifyResultsAreReturnedAsExpected **********");
	}

	@Test
	void UpdateStatusOfUserUsingPutRequest()
	{
		logger.info("********* Started TC002_UpdateStatusOfUserUsingPutRequest **********");

		String body = "{\"status\":\"inactive\"}";
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.urlEncodingEnabled(false);
		requestSpecification.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + bearerToken);

		requestSpecification.body(body).put(apiHelper.BaseURL("/3987")).prettyPrint();
		Response response = requestSpecification.body(body).put(apiHelper.BaseURL("/3987"));
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getBody().asString(), "inactive");

		logger.info("********* Finished TC002_UpdateStatusOfUserUsingPutRequest **********");
	}

	@Test
	void CreateNewUserUsingPostRequest()
	{
		String body = "{\"name\":\"Rajiii\",\"email\":\"rajiiib@somgewchere.org\",\"gender\":\"female\",\"status\":\"inactive\"}";

		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.urlEncodingEnabled(false);
		requestSpecification.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + bearerToken);

		requestSpecification.body(body).post(apiHelper.BaseURL("/")).prettyPrint();
		Response response = requestSpecification.body(body).put(apiHelper.BaseURL(""));

		Assert.assertEquals(response.statusCode(), 200);
		/** This assertion is failing here due to probably a bug in the api or in rest assured - although the resource is created successfully, we are getting a 404 error **/

		Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
		Assert.assertEquals(response.getBody().asString(), "Rajiii");
		Assert.assertEquals(response.getBody().asString(), "rajiiib@somgewchere.org");
		Assert.assertEquals(response.getBody().asString(), "female");
		Assert.assertEquals(response.getBody().asString(), "inactive");
	}
		
	@Test
	void WhenUsingAnInvalidEndPoint_404ErrorOccurs()
	{
		logger.info("********* Started WhenUsingAnInvalidEndPoint_404ErrorOccurs **********");

		given().
				when().
				get(apiHelper.BaseURL("/abcd")).
				then().log().all().
				assertThat().
				statusCode(404);
	}
	
	@Test
	void WhenAValidTokenIsNotUsed_401ErrorOccurs()
	{
		logger.info("********* Started WhenUsingAnInvalidEndPoint_404ErrorOccurs **********");

		String body = "{\"status\":\"inactive\"}";
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.urlEncodingEnabled(false);
		requestSpecification.header("Content-Type", "application/json")
				.header("Authorization", "Bearer akdugasfdgjdsgsdg" );

		requestSpecification.body(body).put(apiHelper.BaseURL("")).prettyPrint();
		Response response = requestSpecification.body(body).post(apiHelper.BaseURL(""));
		Assert.assertEquals(response.statusCode(), 401);

		logger.info("********* Finished WhenAValidTokenIsNotUsed_401ErrorOccurs **********");
	}

}
