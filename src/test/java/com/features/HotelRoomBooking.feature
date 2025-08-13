# Created by aritr at 11-08-2025
@Booking
Feature: Testing the Booking URL endpoints
  Background:
    Given Set RestAssured Base URL for the Hotel Room BaseURL
    And set request header "accept" as "application/json"

   
  Scenario: View all rooms
    When get the GET response object at endpoint "/viewRoomList"
    Then check if status code is "200"
    And print all rooms from the list
      
  Scenario: View room by valid ID
    When get the GET response object at endpoint "/viewRoomById/301"
    Then check if status code is "200"
    And verify room details for ID "301"
 
  Scenario: View room by non-existing ID
    When get the GET response object at endpoint "/viewRoomById/702"
    Then check if status code is "404"

  Scenario: View room by invalid ID format
    When get the GET response object at endpoint "/viewRoomById/abc"
    Then check if status code is "400"

  
  Scenario: View room by valid type
    When get the GET response object at endpoint "/viewRoomByType?roomType=DELUXE"
    Then check if status code is "200"

  Scenario: View room by invalid type
    When get the GET response object at endpoint "/viewRoomByType?roomType=TRIPLE"
    Then check if status code is "404"
    
   Scenario: Delete room by valid id
    When get the DELETE response object at endpoint "/deleteRoomById/101"
    Then check if status code is "200"
    Then check if body does not have string "101"
    
  Scenario: Delete room by invalid id
    When get the DELETE response object at endpoint "deleteRoomById/9999"
    Then check if status code is "404"
    Then check if body does not have string "101"
    
   Scenario: Delete room by alphabetical id
    When get the DELETE response object at endpoint "deleteRoomById/abc"
    Then check if status code is "404"
   
   Scenario: Delete room by wrong HTTP request
    When get the GET response object at endpoint "/deleteRoomById/101"
    Then check if status code is "405"

  Scenario: Update Room Price – Valid Room ID and Price
    And set request header "Content-Type" as "application/x-www-form-urlencoded"
    And set parameter "roomId" in request body as "101"
    And set parameter "roomPrice" in request body as "1500"
    And get the PUT response object at endpoint "/updateRoomPrice"
    Then check if status code is "200"
    Then verify room details in update response for roomId "101" and price "1500.0"


  Scenario: Update Room Price – Missing Price Parameter
    And set request header "Content-Type" as "application/x-www-form-urlencoded"
    And set parameter "roomId" in request body as "301"
    And set parameter "roomPrice" in request body as ""
    And get the PUT response object at endpoint "/updateRoomPrice"
    Then check if status code is "400"


  Scenario: Update Room Price – Invalid Room ID
    And set request header "Content-Type" as "application/x-www-form-urlencoded"
    And set parameter "roomId" in request body as "9999"
    And set parameter "roomPrice" in request body as "2000"
    And get the PUT response object at endpoint "/updateRoomPrice"
    Then check if status code is "200"

  Scenario: Add Room Details – Valid Data
    And set request header "Content-Type" as "application/x-www-form-urlencoded"
    And set parameter "roomId" in request body as "11"
    And set parameter "hotelId" in request body as "1001a"
    And set parameter "roomType" in request body as "SINGLE"
    And set parameter "roomStatus" in request body as "NOTAVAILABLE"
    And set parameter "roomPrice" in request body as "20000"
    And get the response object for post response at endpoint "/addRoom"
    Then check if status code is "200"
    Then verify room details in response for roomId "11"


  Scenario: Add Room Details – Value Already Exists
    And set request header "Content-Type" as "application/x-www-form-urlencoded"
    And set parameter "roomId" in request body as "101"
    And set parameter "hotelId" in request body as "H2001"
    And set parameter "roomType" in request body as "SINGLE"
    And set parameter "roomStatus" in request body as "NOTAVAILABLE"
    And set parameter "roomPrice" in request body as "1500"
    And get the response object for post response at endpoint "/addRoom"
    Then check if status code is "409"

