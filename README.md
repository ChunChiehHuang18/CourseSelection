# CourseSelection #

This is a maven project using Jetty, Jersey and MySql to practice building the back-end API of the course selection system.

## MySQL 
You can import coursexxxx.sql file in mysql folder  into your MySQL database.
And modify config in MySqlConfig.java if you need.

## Java Doc
You can find this Project's Java doc in doc folder

## Start Http Service
Method 1: Uber-jar

Open uber-jar folder and use **java -jar server-xxxx.jar** command to start service 

Method 2: Maven project

Using IntelliJ IDEA to open project folder and run **JettyServer.java** to start service

## Web service API

>Host: http://localhost:8081/

>Produces MIME: application/json

>Consumes MIME: application/json

### Student

#### add(@POST)

* /student

Add a student into DB

JSON format: 

KEY | Type | Description
---------|----------|---------
 Student_Number | Integer | Student's number (Optional)
 Student_Name | String | Less than 45 char and can be used in Chinese
 Student_Gender | Stirng | Male or Female or Bisexual (Optional)

Example:

```java
 {"Student_Name": "Harry", "Student_Number": 5, "Student_Gender": "male"}
 ```


#### query(@GET)

* /student

Query all student list

* /student/{student #}

Query student by student integer type number

Example: http://localhost:8081/student/19

* /student/{student #}/selection

Query courses selected by student number

Example: http://localhost:8081/student/19/selection


### Instructor

#### add(@POST)

* /instructor

Add a instructor into DB

JSON format: 

KEY | Type | Description
---------|----------|---------
 Instructor_Number | Integer | Instructor's number (Optional)
 Instructor_Name | String | Less than 45 char and can be used in Chinese
 Instructor_Office | Stirng | Fixed to 4 char (Optional)
 
Example:

```java
{"Instructor_Name": "李白", "Instructor_Number": 8, "Instructor_Office": "C102"}
```

#### query(@GET)

* /instructor/all

Query all instructor list

* /instructor/{instructor #}

Query instructor by instructor integer type number

Example: http://localhost:8081/instructor/2

* /instructor/{instructor #}/course

Query course offered by instructor integer type number

Example: http://localhost:8081/instructor/2/course


### Course

#### add(@POST)

* /course

Add a course into DB

JSON format: 

KEY | Type | Description
---------|----------|---------
 Course_Number | String | Fixed to 5 char
 Course_Title | String | Less than 45 char and can be used in Chinese
 Course_Size | Integer | 10 ~ 255 
 Course_Weekday | Integer | 1 ~ 5 
 Course_Classtime | String | 1 ~ 8, Use","separate each class time 

Example:

```java
{"Course_Number": "SA104", "Course_Title": "軟體工程","Course_Size": 10,"Course_Weekday": 5,"Instructor_Number": 5,"Course_Classtime": "5,6,7" }
```

#### delete(@DELETE)

* /course

Delete the course and course's selection data

JSON format: 

KEY | Type | Description
---------|----------|---------
 Course_Number | String | Fixed to 5 char

Example:

```java
{"Course_Number": "SA104"}
```

#### query(@GET)

* /course

Query all course list

* /course/{course #}

Query course by course string type number

Example: http://localhost:8081/course/BS777


### Selection

#### add(@POST)

* /selection

Select a course and insert into DB

JSON format: 

KEY | Type | Description
---------|----------|---------
 Selection_Number | Integer | Selection's number (Optional)
 Course_Number | String | Fixed to 5 char
 Student_Number | Integer | 10 ~ 255 


Example:

```java
{"Selection_Number":20 , "Course_Number": "MS534", "Student_Number": 17 }
```

#### delete(@DELETE)

* /selection

Delete a selection and update DB

JSON format: 

KEY | Type | Description
---------|----------|---------
 Selection_Number | Integer | Selection's number 


Example:

```java
{"Selection_Number":68 }
```

#### query(@GET)

* /selection

Query all selection list

* /selection/{selection #}

Query selection by selection number

Example: http://localhost:8081/selection/66

* /selection/query?studentid={id}&instructorid={id}

Query selection list by filter 

1. Filter by student type number

Example: http://localhost:8081/selection/query?studentid=20

2. Filter by instrutor type number

Example: http://localhost:8081/selection/query?instructorid=5

3. Filter by intersection of student and instructor integer type number

Example: http://localhost:8081/selection/query?studentid=20&instructorid=5


