package server.mysql.helper;

/**
 * PrepareStatementUtils place all prepare statement string
 */
public class PrepareStatementUtils {

    // Instructor

    // ADD
    static String ADD_INSTRUCTOR_STM_STRING =
            "INSERT INTO course_selection.instructor(instructor_number, instructor_name, instructor_office)" +
                    "VALUES (?, ?, ?);";

    // Query
    static String QUERY_ALL_INSTRUCTOR_STM_STRING =
            "SELECT * FROM course_selection.instructor;";

    static String QUERY_INSTRUCTOR_BY_NUMBER_STM_STRING =
            "SELECT * FROM course_selection.instructor " +
                    "WHERE instructor_number= ?;";

    // Student

    // ADD
    static String STUDENT_DEFAULT_CLASSTIME = "{\"1\":\"\",\"2\":\"\",\"3\":\"\",\"4\":\"\",\"5\":\"\"}";

    static String ADD_STUDENT_STM_STRING =
            "INSERT INTO course_selection.student(student_number, student_name, student_gender, student_classtime)" +
                    "VALUES (?, ?, ?, ?);";

    // Query
    static String QUERY_ALL_STUDENT_STM_STRING =
            "SELECT * FROM course_selection.student;";

    static String QUERY_STUDENT_BY_NUMBER_STM_STRING =
            "SELECT * FROM course_selection.student " +
                    "WHERE student_number= ?;";

    // Course

    // ADD
    static String ADD_COURSE_STM_STRING =
            "INSERT INTO course_selection.course(course_number, course_title, instructor_number, course_size, course_remain, course_weekday, course_classtime)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";

    // Delete
    static String DELETE_COURSE_STM_STRING =
            "DELETE FROM course_selection.course " +
                    "WHERE course_number = ?;";

    // Query
    static String QUERY_COURSE_BY_NUMBER_STM_STRING =
            "SELECT * FROM course_selection.course " +
                    "WHERE course_number = ?;";

    static String QUERY_COURSE_BY_INSTRUCTOR_STM_STRING =
            "SELECT * FROM course_selection.course " +
                    "WHERE instructor_number = ?;";

    static String QUERY_ALL_COURSE_STM_STRING =
            "SELECT * FROM course_selection.course;";


    // Selection

    // ADD
    static String DEDUCT_COURSE_REMAIN_SELECTION_STM_STRING = "SELECT course_remain FROM course_selection.course where course_number=? AND course_remain > 0 for UPDATE;";

    static String DEDUCT_COURSE_REMAIN_UPDATE_STM_STRING =  "UPDATE course_selection.course set course_remain = course_remain - 1 where course_number=? AND course_remain > 0;";

    static String STUDENT_CLASSTIME_SELECTION_STM_STRING = "SELECT student_classtime FROM course_selection.student where student_number=? for UPDATE;";

    static String STUDENT_CLASSTIME_UPDATE_STM_STRING =  "UPDATE course_selection.student set student_classtime =? where student_number=?;";

    static String ADD_SELECTION_STM_STRING =
            "INSERT INTO course_selection.selection(selection_number, course_number, student_number)" +
                    "VALUES (?, ?, ?);";

    // Delete
    static String ADD_COURSE_REMAIN_SELECTION_STM_STRING = "SELECT course_remain FROM course_selection.course where course_number=? for UPDATE;";

    static String ADD_COURSE_REMAIN_UPDATE_STM_STRING =  "UPDATE course_selection.course set course_remain = course_remain + 1 where course_number=? ;";

    static String DELETE_SELECTION_BY_NUMBER_STM_STRING =
            "DELETE FROM course_selection.selection " +
            "WHERE selection_number = ?;";

    static String DELETE_SELECTION_BY_COURSE_STM_STRING =
            "DELETE FROM course_selection.selection " +
                    "WHERE course_number = ?;";

    // Query
    static String QUERY_ALL_SELECTION_STM_STRING =
            "SELECT * FROM course_selection.selection;";

    static String QUERY_SELECTION_BY_NUMBER_STM_STRING =
            "SELECT * FROM course_selection.selection " +
            "WHERE selection_number = ?; ";

    static String QUERY_SELECTION_BY_COURSE_STM_STRING =
            "SELECT * FROM course_selection.selection " +
                    "WHERE course_number = ?; ";

    static String QUERY_SELECTION_BY_STUDENT_STM_STRING = "SELECT * " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
            "WHERE course_selection.selection.student_number= ?";

    static String QUERY_SELECTION_BY_INSTRUCTOR_STM_STRING = "SELECT * " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
            "WHERE course_selection.instructor.Instructor_number= ?";

    static String QUERY_SELECTION_BY_STUDENT_AND_INSTRUCTOR_STM_STRING = "SELECT * " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
            "WHERE course_selection.selection.student_number= ? AND course_selection.instructor.Instructor_number= ?";
}

