

# Hotel API Testing Framework

## Overview
This project implements an automated testing framework for a Hotel Room Booking API backend. The framework is built using Java and incorporates BDD (Behavior Driven Development) approach with Cucumber, along with RestAssured for API testing and TestNG as the testing framework.

## Project Structure
```
HotelAPI-Testing/
 ├── src/ 
 │ └── test/ 
 │ ├── java/com/ 
 │ │ ├── configs/ 
 │ │ │ └── config.properties 
 │ │ ├── definitions/ 
 │ │ │ └── CommonStepDef 
 │ │ ├── features/ 
 │ │ │ ├── Authentication.feature 
 │ │ │ └── HotelRoomBooking.feature 
 │ │ ├── main/ 
 │ │ │ └── Invoke 
 │ │ ├── setup/ 
 │ │ │ └── ConfigFileReader 
 │ │ └── tests/ 
 │ │   └── CucumberRunnerTests 
 │ └── resources/ 
 ├── test-output/ 
 ├── pom.xml 
 └── testng.xml
``` 

## Prerequisites
- Java JDK 24
- Maven
- IDE (preferably IntelliJ IDEA)

## Framework Components

### 1. API Endpoints
The framework is configured to test the following endpoints:
- Authentication API: `https://webapps.tekstac.com/OAuthRestApi/webapi/auth`
- Hotel Room Service API: `https://webapps.tekstac.com/HotelAPI/RoomService`

### 2. Key Features
- BDD approach using Cucumber
- API testing using RestAssured
- Test execution using TestNG
- Configuration management
- Authentication handling
- Detailed test reporting

### 3. Project Components
- **features/**: Contains Cucumber feature files
  - `Authentication.feature`: Tests for authentication scenarios
  - `HotelRoomBooking.feature`: Tests for room booking functionalities

- **definitions/**: Step definitions for Cucumber scenarios
  - `CommonStepDef`: Common step definitions used across features

- **configs/**: Configuration files
  - `config.properties`: Contains environment URLs and configuration parameters

- **setup/**: Framework setup classes
  - `ConfigFileReader`: Handles reading configuration from properties file

- **tests/**: Test runners
  - `CucumberRunnerTests`: TestNG-based test runner for Cucumber tests



