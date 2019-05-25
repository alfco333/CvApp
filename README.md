#### CV APP

Sample project to display a Resume as part of the Challenge exercise.

###### Project Structure

The project is composed by the following parts:
* Clean Architecture
* MVP
* Multi-Modules with Gradle (Data - Domain - App/Presentation)
* Dagger for dependencies injection
* Realm for the local storage
* Delegates to manage how the view is composed

Each of them with a specific purpose:

###### Clean
* Is the architectural patterns used to divide the app in different layers so the division of concerns is well defined

###### DATA:
* This layer is in charge of managing all the data for the app, through this layer the api requests and realm management are performed.

###### DOMAIN:
* This layer is in charge of defining the business rules and communicating the intended actions to the data layer.

###### APP/PRESENTATION:
* This layer is in charge of presenting the information and receiving events from and to the user. Events like clicks, refreshing the screen, etc are performed here.

###### TESTS:
* Each layer has it's own set of tests that correspond to the different tasks they perform.

