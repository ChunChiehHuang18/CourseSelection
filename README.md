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

#### query(@GET)

* /student

Query all student list

* /student/{student #}

Query student by student integer type number

Example: http://localhost:8081/student/19

#### add(@POST)

* /student

Add a student into DB

Post data: 
1. Ation: add
2. Student_Number: Integer
3. Student_Name: Less than 45 char and can be used in Chinese
4. Student_Gender: Gender can be Male or Female or Bisexual

Post data example:

```java
 {"Action": "add", "Student_Name": "Harry", "Student_Number": 5, "Student_Gender": "male"}
 ```


### Instructor

#### query(@GET)

* /instructor/all

Query all instructor list

* /instructor/{instructor #}

Query instructor by instructor integer type number

Example: http://localhost:8081/instructor/2

#### add(@POST)

* /instructor

Add a instructor into DB

Post data: 
1. Ation: add
2. Instructor_Number: Integer
3. Instructor_Name: Less than 45 char and can be used in Chinese
4. Instructor_Office: Fixed to 4 char

Post data example:

```java
{"Action": "add", "Instructor_Name": "李白", "Instructor_Number": 8, "Instructor_Office": "C102"}
```


### Course

#### query(@GET)

* /course

Query all course list

* /course/{course #}

Query course by course string type number

Example: http://localhost:8081/course/BS777

#### add(@POST)

* /course

Add a course into DB

Post data: 
1. Ation: add
2. Course_Number: Fixed to 5 char
3. Course_Title: Less than 45 char and can be used in Chinese
4. Course_Size: 10 ~ 255 (Integer)
5. Course_Weekday: 1 ~ 5 (Integer)
6. Course_Classtime: Use","separate each class time (1~8)

Post data example:

```java
{"Action": "add","Course_Number": "SA104", "Course_Title": "軟體工程","Course_Size": 10,"Course_Weekday": 5,"Instructor_Number": 5,"Course_Classtime": "5,6,7" }
```

### Selection

#### query(@GET)

* /selection

Query all selection list

* /selection/query?studentid={id}&instructorid={id}

Query selection list by filter 

1. Filter by student type number

Example: http://localhost:8081/selection/query?studentid=20

2. Filter by instrutor type number

Example: http://localhost:8081/selection/query?instructorid=5

3. Filter by intersection of student and instructor integer type number

Example: http://localhost:8081/selection/query?studentid=20&instructorid=5


#### add(@POST)

* /selection

Select a course and insert into DB

Post data: 
1. Ation: add
2. Selection_Number: Integer
2. Course_Number: Fixed to 5 char
3. Student_Number: Integer

Post data example:

```java
{"Action": "add", "Selection_Number":20 , "Course_Number": "MS534", "Student_Number": 17 }
```

