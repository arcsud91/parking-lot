# Application

## Structure

```
.
├── lib
│   ├── hamcrest-core-1.3.jar
│   └── junit-4.12.jar
└── src
    ├── com
    │   └── parkinglot
    │       ├── MainDriver.java
    │       ├── constants
    │       │   └── Constants.java
    │       ├── dao
    │       │   ├── ParkingDataManager.java
    │       │   ├── ParkingLevelDataManager.java
    │       │   └── impl
    │       │       ├── ParkingDataManagerImpl.java
    │       │       └── ParkingLevelManagerImpl.java
    │       ├── entities
    │       │   ├── Car.java
    │       │   ├── Color.java
    │       │   ├── ParkingLot.java
    │       │   ├── Vehicle.java
    │       │   └── strategy
    │       │       ├── NearestFirstParkingStrategy.java
    │       │       └── ParkingStrategy.java
    │       ├── exceptions
    │       │   ├── ErrorCode.java
    │       │   └── ParkingException.java
    │       └── service
    │           ├── ParkingService.java
    │           └── impl
    │               └── ParkingServiceImpl.java
    └── test
        └── com
            └── parkinglot
                └── TestParkingService.java

```

### lib
It contains all the required libraries to run the application. In our case it contains only Junit.

### src
Contains all the source code written for the application.
  * MainDriver.java
    This class contains the driver method for taking command line input from user.
  * constants
    This contains all the constants required and used in the application
  * dao
    This package contains files which perform operations on the store
  * entities
    Package contains all the model classes and parking strategy classes.
  * exceptions
    This package contains all the custom exceptions declared within the application
  * service
    This package provides the service methods to perform operations on parking lot.
  * test
    This package contains the tests for the service method
  
