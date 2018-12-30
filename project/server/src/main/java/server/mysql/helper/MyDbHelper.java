package server.mysql.helper;

import java.sql.*;



/**
 * MyDbHelper collect all db helpers and provide single instance
 */
public class MyDbHelper {

    private Connection conn;

    // Instructor
    private InstructorDbHelper instructorDbHelper;

    // Student
    private StudentDbHelper studentDbHelper;

    // Course
    private CourseDbHelper courseDbHelper;

    // Selection
    private SelectionDbHelper selectionDbHelper;

    private static class SingletonHolder {
        private static final MyDbHelper INSTANCE = new MyDbHelper();
    }

    /**
     * Generate MyDbHelper single instance
     * @return MyDbHelper instance
     */
    public static MyDbHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private MyDbHelper() {
        try {
            // Register JDBC driver
            Class.forName(MySqlConfig.JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(MySqlConfig.DB_URL, MySqlConfig.USER, MySqlConfig.PASS);

            System.out.println("Initial db helper instance ");
            // Instructor
            instructorDbHelper = new InstructorDbHelper(conn);

            // Student
            studentDbHelper = new StudentDbHelper(conn);

            // Course
            courseDbHelper = new CourseDbHelper(conn);

            // Selection
            selectionDbHelper = new SelectionDbHelper(conn);

            // Class time
            ClasstimeDbHelper classtimeDbHelper = new ClasstimeDbHelper(conn, courseDbHelper);
            courseDbHelper.setClasstimeDbHelper(classtimeDbHelper);
            selectionDbHelper.setClasstimeDbHelper(classtimeDbHelper);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public InstructorDbHelper instructor() {
        return instructorDbHelper;
    }

    public StudentDbHelper student() {
        return studentDbHelper;
    }

    public CourseDbHelper course() {
        return courseDbHelper;
    }

    public SelectionDbHelper selection() {
        return selectionDbHelper;
    }

}
