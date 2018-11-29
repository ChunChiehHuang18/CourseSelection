# CourseSelection #

This is a maven project using Jetty, Jersey and MySql to practice building the back-end API of the course selection system.

## MySQL 
You can import coursexxxx.sql file in mysql folder  into your MySQL database

## Java Doc
You can find Java doc in doc folder

## Start Http Service
Using IntelliJ IDEA to open project folder and run JettyServer.java 

## Web service API

>Host: http://localhost:8081/

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

#### add(@POST)

* /course

Add a course into DB

Post data example:

```java
{"Action": "add","Course_Number": "SA104", "Course_Title": "軟體工程","Course_Size": 10,"Course_Weekday": 5,"Instructor_Number": 5,"Course_Classtime": "5,6,7" }
```

### Selection

#### query(@GET)

* /selection/all

Query all selection list

* /selection/student/{student #}

Query selection list by student number

* /selection/instructor/{instructor #}

Query selection list by instructor number

