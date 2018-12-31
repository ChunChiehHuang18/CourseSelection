package server.mysql.utils;

/**
 * PrepareStatementUtils place four static class prepare statement string
 */
public class PrepareStatementUtils {

    /**
     * Place instructor prepare statement string
     */
    public static class Instructor {
        // ADD
        public static String ADD =
                "INSERT INTO course_selection.instructor(instructor_number, instructor_name, instructor_office)" +
                        "VALUES (?, ?, ?);";

        // Query
        public static String QUERY_ALL =
                "SELECT * FROM course_selection.instructor;";

        public static String QUERY_BY_NUMBER =
                "SELECT * FROM course_selection.instructor " +
                        "WHERE instructor_number= ?;";
    }


    /**
     * Place student prepare statement string
     */
    public static class Student {
        // ADD
        public static String STUDENT_DEFAULT_CLASSTIME = "{\"1\":\"\",\"2\":\"\",\"3\":\"\",\"4\":\"\",\"5\":\"\"}";

        public static String ADD =
                "INSERT INTO course_selection.student(student_number, student_name, student_gender, student_classtime)" +
                        "VALUES (?, ?, ?, ?);";

        // Query
        public static String QUERY_ALL =
                "SELECT * FROM course_selection.student;";

        public static String QUERY_BY_NUMBER =
                "SELECT * FROM course_selection.student " +
                        "WHERE student_number= ?;";

        // Update
        public static String STUDENT_CLASSTIME_SELECTION = "SELECT student_classtime FROM course_selection.student where student_number=? for UPDATE;";

        public static String STUDENT_CLASSTIME_UPDATE  = "UPDATE course_selection.student set student_classtime =? where student_number=?;";



    }

    /**
     * Place course prepare statement string
     */
    public static class Course {
        // ADD
        public static String ADD =
                "INSERT INTO course_selection.course(course_number, course_title, instructor_number, course_size, course_remain, course_weekday, course_classtime)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);";

        // Delete
        public static String DELETE =
                "DELETE FROM course_selection.course " +
                        "WHERE course_number = ?;";

        // Query
        public static String QUERY_BY_NUMBER =
                "SELECT * FROM course_selection.course " +
                        "WHERE course_number = ?;";

        public static String QUERY_BY_INSTRUCTOR =
                "SELECT * FROM course_selection.course " +
                        "WHERE instructor_number = ?;";

        public static String QUERY_ALL =
                "SELECT * FROM course_selection.course;";

        // Update
        public static String ADD_COURSE_REMAIN_SELECTION = "SELECT course_remain FROM course_selection.course where course_number=? for UPDATE;";

        public static String ADD_COURSE_REMAIN_UPDATE = "UPDATE course_selection.course set course_remain = course_remain + 1 where course_number=? ;";

        public static String DEDUCT_COURSE_REMAIN_SELECTION = "SELECT course_remain FROM course_selection.course where course_number=? AND course_remain > 0 for UPDATE;";

        public static String DEDUCT_COURSE_REMAIN_UPDATE = "UPDATE course_selection.course set course_remain = course_remain - 1 where course_number=? AND course_remain > 0;";

    }


    /**
     * Place selection prepare statement string
     */
    public static class Selection {
        // ADD
        public static String ADD =
                "INSERT INTO course_selection.selection(selection_number, course_number, student_number)" +
                        "VALUES (?, ?, ?);";

        // Delete
        public static String DELETE_BY_NUMBER =
                "DELETE FROM course_selection.selection " +
                        "WHERE selection_number = ?;";

        public static String DELETE_BY_COURSE =
                "DELETE FROM course_selection.selection " +
                        "WHERE course_number = ?;";

        // Query
        public static String QUERY_ALL =
                "SELECT * FROM course_selection.selection;";

        public static String QUERY_BY_NUMBER =
                "SELECT * FROM course_selection.selection " +
                        "WHERE selection_number = ?; ";

        public static String QUERY_BY_COURSE =
                "SELECT * FROM course_selection.selection " +
                        "WHERE course_number = ?; ";

        public static String QUERY_BY_STUDENT = "SELECT * " +
                "FROM (course_selection.selection " +
                "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
                "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
                "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
                "WHERE course_selection.selection.student_number= ?";

        public static String QUERY_BY_INSTRUCTOR = "SELECT * " +
                "FROM (course_selection.selection " +
                "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
                "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
                "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
                "WHERE course_selection.instructor.Instructor_number= ?";

        public static String QUERY_BY_STUDENT_AND_INSTRUCTOR = "SELECT * " +
                "FROM (course_selection.selection " +
                "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
                "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
                "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
                "WHERE course_selection.selection.student_number= ? AND course_selection.instructor.Instructor_number= ?";

    }
}

