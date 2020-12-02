# Getting Started

### Pre-Requisites
Java 8 or higher is required for this application to execute.

### Application Assumptions
Based on the requirements as specified in the task document, the following assumptions have been deduced:
* A store needs to be maintained separate from its inventory.
* If a store is retrieved or removed, all it's related inventory are included in the process.
* The store itself may not be updated, as it's only represented by an ID.
* If no inventory items exist for a persisted store, then they need to be created.
* If inventory items exist for a persisted store, but new ones need to be created, then an update operation may be performed to add to the existing list of items.


### Application Build And Execution
Gradle is a build automation tool that is also used for execution of this application.
This project is pre-packaged with a gradle wrapper that is used for this purpose.

To build this project, run the following:
>               gradlew clean build

All tests (unit and integration) are executed during the build process, and Jacoco is used to measure code coverage
throughout the project. Minimum coverage for success is currently set at 83%.

Output of the tests may be viewed at 
>              store-api\build\reports\jacoco\html\index.html

To execute, proceed to the base directory of this project and run the following:
>               gradlew bootRun

### API Documentation
Swagger has been configured to produce an API blueprint for this application.
Once the application is running, navigate to
>               http://localhost:8080/api/swagger-ui/
