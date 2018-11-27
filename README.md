# CourseSelection #

This is a project using Jetty, Jersey and MySql to practice building the back-end API of the course selection system.

## MySQL 
You can import coursexxxx.sql file into your MySQL database

## Start Http Service
Run JettyServer.java 


## Query
>Host: http://localhost:8080/

>MIME: application/json

### Student

* all: /student/all

### Instructor

* all: /instructor/all

### Course

* all: /course/all

### Selection

* all: /selection/all
* by student #: /selection/student/{student #}
* by instructor #: /selection/instructor/{instructor #}

