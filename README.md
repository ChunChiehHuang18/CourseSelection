# CourseSelection #

This is a maven project using Jetty, Jersey and MySql to practice building the back-end API of the course selection system.

## MySQL 
You can import coursexxxx.sql file into your MySQL database

## Start Http Service
Run JettyServer.java 

## Web service API

>Host: http://localhost:8080/

>Produces MIME: application/json

>Consumes MIME: application/json

### Student

#### query(@GET)

* /student/all

Query all student list

#### add(@POST)

* /student

Add a student into DB

Post data example:

```java
 {"Action": "add", "Student_Name": "Harry", "Student_Number": 5, "Student_Gender": "male"}
 ```


### Instructor

#### query(@GET)

* /instructor/all

Query all instructor list

#### add(@POST)

* /instructor

Add a instructor into DB

Post data example:

```java
{"Action": "add", "Instructor_Name": "李白", "Instructor_Number": 8, "Instructor_Office": "C102"}
```


### Course

#### query(@GET)

* /course/all

Query all course list

### Selection

#### query(@GET)

* /selection/all

Query all selection list

* /selection/student/{student #}

Query selection list by student number

* /selection/instructor/{instructor #}

Query selection list by instructor number

