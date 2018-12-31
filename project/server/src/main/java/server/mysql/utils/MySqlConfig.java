package server.mysql.utils;

/**
 * MySqlConfig place MySQL config(url, pass...) and schema related value
 */
public class MySqlConfig {
    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/course_selection?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";

    //  Database credentials
    public static final String USER = "root";
    public static final String PASS = "imroot";

    public static final String VALUE_NULL = "null";

    // column string
    public static final String COLUMN_STUDENT_NUMBER = "student_number";
    public static final String COLUMN_STUDENT_NAME = "student_name";
    public static final String COLUMN_STUDENT_GENDER = "student_gender";
    public static final String COLUMN_STUDENT_CLASSTIME = "student_classtime";

    public static final String COLUMN_INSTRUCTOR_NUMBER = "instructor_number";
    public static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";
    public static final String COLUMN_INSTRUCTOR_OFFICE = "instructor_office";

    public static final String COLUMN_COURSE_NUMBER = "course_number";
    public static final String COLUMN_COURSE_TITLE = "course_title";
    public static final String COLUMN_COURSE_SIZE = "course_size";
    public static final String COLUMN_COURSE_REMAIN = "course_remain";
    public static final String COLUMN_COURSE_WEEKDAY = "course_weekday";
    public static final String COLUMN_COURSE_CLASSTIME = "course_classtime";


    public static final String COLUMN_SELECTION_NUMBER = "selection_number";

    // show string
    public static final String SHOW_STUDENT_NUMBER = "Student Number";
    public static final String SHOW_STUDENT_NAME = "Student Name";
    public static final String SHOW_STUDENT_GENDER = "Student Gender";
    public static final String SHOW_STUDENT_CLASSTIME = "Student ClassTime";

    public static final String SHOW_INSTRUCTOR_NUMBER = "Instructor Number";
    public static final String SHOW_INSTRUCTOR_NAME = "Instructor Name";
    public static final String SHOW_INSTRUCTOR_OFFICE = "Instructor Office";

    public static final String SHOW_COURSE_NUMBER = "Course Number";
    public static final String SHOW_COURSE_TITLE = "Course Title";
    public static final String SHOW_COURSE_SIZE = "Course Size";
    public static final String SHOW_COURSE_REMAIN = "Course Remain";
    public static final String SHOW_COURSE_WEEKDAY = "Course Weekday";
    public static final String SHOW_COURSE_CLASSTIME = "Course Classtime";

    public static final String SHOW_SELECTION_NUMBER = "Selection Number";
}

