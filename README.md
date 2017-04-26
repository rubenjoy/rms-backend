# rms-backend
Mitrais Java Bootcamp Training Project, to practice Spring Framework: Data JPA, Data REST.

The RMS backend provide RESTful API for RMS frontend. 
The project still in development.

# calling rest api

all the api endpoint are located on /rms/api basepath.
The employee entity can be obtained from /rms/api/employees endpoint.
All weak entities are nested on /rms/api/employees endpoint.

# commands

- gradle clean
- gradle build
- gradle bootRun
- gradle test
- gradle test jacocoTestReport

# dependencies

dependencies found on build.gradle:
- Spring Data REST starter
- Spring Data JPA starter
- H2 memory database
- Spring Test starter

# TODO

- <del>Unit Test w/ Spring starter test & Mockito MVC</del>
- adding validation to DTO
- Meaningfull error message for rest controller