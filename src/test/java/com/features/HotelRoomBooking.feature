# Created by aritr at 11-08-2025
@Booking
Feature: Testing the Booking URL endpoints
  Background:
    Given Set RestAssured Base URL for the Hotel Room BaseURL
    And set request header "accept" as "application/json"

   
  Scenario: View all rooms
    When get the GET response object at endpoint "/viewRoomList"
    Then check if status code is "200"

  Scenario: View room by valid ID
    When get the GET response object at endpoint "/viewRoomById/301"
    Then check if status code is "200"

 
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
