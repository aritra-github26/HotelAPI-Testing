package com.definitions;

import com.setup.ConfigFileReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommonStepDef {

    public static RequestSpecification httpRequest;
    public static Response response;
    public static ConfigFileReader configFileReader = new ConfigFileReader();

    @Given("Set RestAssured Base URL for the Authentication")
    public void setRestAssuredBaseURLForTheAuthentication() {
        RestAssured.baseURI = configFileReader.getAuthURL();
    }

    @Given("Set RestAssured Base URL for the Hotel Room BaseURL")
    public void setRestAssuredBaseURLForTheHotelRoomBaseURL() {
        RestAssured.baseURI = configFileReader.getBaseURL();
    }

    @And("set request header {string} as {string}")
    public static void setRequestHeaderAs(String header, String value) {
        httpRequest = RestAssured.given().header(header, value);
    }

    @And("set parameter {string} in request body as {string}")
    public void setParameterInRequestBodyAs(String paramName, String paramValue) {
        httpRequest = httpRequest.formParam(paramName, paramValue);
    }
    @And("get the response object for post response at endpoint {string}")
    public void getTheResponseObjectAtEndpoint(String endpoint) {
        response = httpRequest.post(endpoint);
    }

    @And("get the GET response object at endpoint {string}")
    public void getTheGETResponseObjectAtEndpoint(String endpoint) {
        response = httpRequest.get(endpoint);
    }
    
    @Then("check if status code is {string}")
    public void ifStatusCodeIs(String expectedStatusCode) {
        int statuscode = response.getStatusCode();
        assertThat(statuscode, is(Integer.parseInt(expectedStatusCode)));
    }


    @Then("set retrieved auth_code as environment variable {string}")
    public void setRetrievedAuth_codeAsEnvironmentVariable(String auth) {
        System.setProperty(auth, response.jsonPath().getString("auth_code"));
        System.out.println("auth_code: "+response.jsonPath().getString("auth_code"));
    }

    @And("get environment variable {string} for request body")
    public void getEnvironmentVariableForRequestBody(String auth) {
        String authCode = System.getProperty(auth);
        httpRequest = httpRequest.formParam("auth_code", authCode);
    }

    @Then("set retrieved access_token as environment variable {string}")
    public void setRetrievedAccess_tokenAsEnvironmentVariable(String access) {
        System.setProperty(access, response.jsonPath().getString("access_token"));
        System.out.println("access_token: "+response.jsonPath().getString("access_token"));
    }


}
