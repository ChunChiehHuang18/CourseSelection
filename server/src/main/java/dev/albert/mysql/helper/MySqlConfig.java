package dev.albert.mysql.helper;

public class MySqlConfig {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/course_selection?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "imroot";

    // column string
    static final String COLUMN_STUDENT_NUMBER = "student_number";
    static final String COLUMN_STUDENT_NAME = "student_name";
    static final String COLUMN_STUDENT_GENDER = "student_gender";

    static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";
    static final String COLUMN_INSTRUCTOR_OFFICE = "instructor_office";

    static final String COLUMN_COURSE_NUMBER = "course_number";
    static final String COLUMN_COURSE_TITLE = "course_title";

    // show string
    static final String SHOW_STUDENT_NUMBER = "Student Number";
    static final String SHOW_STUDENT_NAME = "Student Name";
    static final String SHOW_STUDENT_GENDER = "Student Gender";

    static final String SHOW_INSTRUCTOR_NAME = "Instructor Name";
    static final String SHOW_INSTRUCTOR_OFFICE = "Instructor Office";

    static final String SHOW_COURSE_NUMBER = "Course Number";
    static final String SHOW_COURSE_TITLE = "Course Title";
}
