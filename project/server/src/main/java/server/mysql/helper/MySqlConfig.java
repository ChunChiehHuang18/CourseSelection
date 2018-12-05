package server.mysql.helper;

/**
 * MySqlConfig place MySQL config(url, pass...) and schema related value
 */
public class MySqlConfig {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/course_selection?serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "imroot";

    public static final String VALUE_NULL = "null";

    // column string
    static final String COLUMN_STUDENT_NUMBER = "student_number";
    static final String COLUMN_STUDENT_NAME = "student_name";
    static final String COLUMN_STUDENT_GENDER = "student_gender";

    static final String COLUMN_INSTRUCTOR_NUMBER = "instructor_number";
    static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";
    static final String COLUMN_INSTRUCTOR_OFFICE = "instructor_office";

    static final String COLUMN_COURSE_NUMBER = "course_number";
    static final String COLUMN_COURSE_TITLE = "course_title";
    static final String COLUMN_COURSE_SIZE = "course_size";
    static final String COLUMN_COURSE_REMAIN = "course_remain";
    static final String COLUMN_COURSE_WEEKDAY = "course_weekday";
    static final String COLUMN_COURSE_CLASSTIME = "course_classtime";

    static final String COLUMN_SELECTION_NUMBER = "selection_number";

    // show string
    static final String SHOW_STUDENT_NUMBER = "Student Number";
    static final String SHOW_STUDENT_NAME = "Student Name";
    static final String SHOW_STUDENT_GENDER = "Student Gender";

    static final String SHOW_INSTRUCTOR_NUMBER = "Instructor Number";
    static final String SHOW_INSTRUCTOR_NAME = "Instructor Name";
    static final String SHOW_INSTRUCTOR_OFFICE = "Instructor Office";

    static final String SHOW_COURSE_NUMBER = "Course Number";
    static final String SHOW_COURSE_TITLE = "Course Title";
    public static final String SHOW_COURSE_SIZE = "Course Size";
    static final String SHOW_COURSE_REMAIN = "Course Remain";
    public static final String SHOW_COURSE_WEEKDAY = "Course Weekday";
    public static final String SHOW_COURSE_CLASSTIME = "Course Classtime";

    static final String SHOW_SELECTION_NUMBER = "Selection Number";
}

