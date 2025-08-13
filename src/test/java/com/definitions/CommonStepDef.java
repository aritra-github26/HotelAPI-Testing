package com.definitions;

import com.setup.ConfigFileReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class CommonStepDef {

    public static RequestSpecification httpRequest;
    public static Response response;
    public static ConfigFileReader configFileReader = new ConfigFileReader();

    @Given("Set RestAssured Base URL for the Authentication")
    public void setRestAssuredBaseURLForTheAuthentication() {
        RestAssured.baseURI = configFileReader.getAuthURL();
        httpRequest = RestAssured.given();
    }

    @Given("Set RestAssured Base URL for the Hotel Room BaseURL")
    public void setRestAssuredBaseURLForTheHotelRoomBaseURL() {
        RestAssured.baseURI = configFileReader.getBaseURL();
        httpRequest = RestAssured.given();
    }

    @And("set request header {string} as {string}")
    public static void setRequestHeaderAs(String header, String value) {
        httpRequest = httpRequest.header(header, value);
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
    
    @And("get the DELETE response object at endpoint {string}")
    public void getTheDELETEResponseObjectAtEndpoint(String endpoint) {
    	response = RestAssured.given().delete(endpoint);
    }
    
    @Then("print all rooms from the list")
    public void printAllRoomsFromList() {
        List<Map<String, Object>> rooms = response.jsonPath().getList("");
        if (rooms.isEmpty()) {
            System.out.println("No rooms found");
        } else {
            rooms.forEach(r -> 
                System.out.println("roomId: " + r.get("roomId") + ", roomType: " + r.get("roomType"))
            );
        }
    }

    @Then("verify room details for ID {string}")
    public void verifyRoomDetailsForID(String expectedId) {
        
        io.restassured.path.json.JsonPath jsonPath = response.jsonPath();

        int roomId = jsonPath.getInt("roomId");
        String roomType = jsonPath.getString("roomType");
        double roomPrice = jsonPath.getDouble("roomPrice");

        org.testng.Assert.assertEquals(roomId, Integer.parseInt(expectedId), "Room ID does not match!");

       
        System.out.println("roomType: " + roomType + ", roomPrice: " + roomPrice);

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
    
    @Then("check if body does not have string {string}")
    public void check_if_body_does_not_have_string(String str) {
        String body = response.getBody().asString();
        org.testng.Assert.assertFalse(body.contains(str), 
            "Body contains the unexpected string: " + str);
    }

    @And("get the PUT response object at endpoint {string}")
    public void getThePUTResponseObjectAtEndpoint(String endpoint) {
        response = httpRequest.put(endpoint);
    }


    @Then("verify room details in response for roomId {string}")
    public void verifyRoomDetailsInResponse(String expectedRoomId) {
        System.out.println("Response Body:\n" + response.asString());

        List<Map<String, Object>> rooms = response.jsonPath().getList("$");
        boolean found = false;

        for (Map<String, Object> room : rooms) {
            if (String.valueOf(room.get("roomId")).equals(expectedRoomId)) {
                found = true;
                org.testng.Assert.assertEquals(room.get("roomId").toString(), expectedRoomId, "Room ID mismatch!");
                System.out.println("Room details added: " + room);
                break;
            }
        }

        org.testng.Assert.assertTrue(found, "Room with ID " + expectedRoomId + " not found in the response!");
    }

    @Then("verify room details in update response for roomId {string} and price {string}")
    public void verifyUpdatedRoomDetailsFromArray(String expectedRoomId, String expectedPrice) {
        System.out.println("Update Response Body:\n" + response.asString());

        List<Map<String, Object>> rooms = response.jsonPath().getList("$");
        boolean found = false;

        for (Map<String, Object> room : rooms) {
            String actualRoomId = String.valueOf(room.get("roomId"));
            String actualPrice = String.valueOf(room.get("roomPrice"));

            if (actualRoomId.equals(expectedRoomId)) {
                found = true;
                org.testng.Assert.assertEquals(actualPrice, expectedPrice, "Room price mismatch after update!");
                System.out.println("Updated room: " + room);
                break;
            }
        }

        org.testng.Assert.assertTrue(found, "Room with ID " + expectedRoomId + " not found in update response!");
    }






}
