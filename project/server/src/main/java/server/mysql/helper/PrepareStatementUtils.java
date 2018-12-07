package server.mysql.helper;

/**
 * PrepareStatementUtils place all prepare statement string
 */
public class PrepareStatementUtils {

    // Instructor

    // ADD
    static String addInstructorStmString =
            "INSERT INTO course_selection.instructor(instructor_number, instructor_name, instructor_office)" +
                    "VALUES (?, ?, ?);";

    // Query
    static String queryAllInstructorStmString =
            "SELECT * FROM course_selection.instructor;";

    static String queryInstructorByNumberStmString =
            "SELECT * FROM course_selection.instructor " +
                    "WHERE instructor_number= ?;";

    // Student

    // ADD
    static String addStudentStmString =
            "INSERT INTO course_selection.student(student_number, student_name, student_gender)" +
                    "VALUES (?, ?, ?);";

    // Query
    static String queryAllStudentStmString =
            "SELECT * FROM course_selection.student;";

    static String queryStudentByNumberStmString =
            "SELECT * FROM course_selection.student " +
                    "WHERE student_number= ?;";

    // Course

    // ADD
    static String addCourseStmString =
            "INSERT INTO course_selection.course(course_number, course_title, instructor_number, course_size, course_remain, course_weekday, course_classtime)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";

    // Query
    static String queryCourseByNumberStmString =
            "SELECT * FROM course_selection.course " +
                    "WHERE course_number = ?;";

    static String queryCourseByInstructorStmString =
            "SELECT * FROM course_selection.course " +
                    "WHERE instructor_number = ?;";

    static String queryAllCourseStmString =
            "SELECT * FROM course_selection.course;";


    // Selection

    // ADD
    static String deductCourseRemainSelectionStmString = "SELECT course_remain FROM course_selection.course where course_number=? AND course_remain > 0 for UPDATE;";

    static String deductCourseRemainUpdateStmString =  "UPDATE course_selection.course set course_remain = course_remain - 1 where course_number=? AND course_remain > 0;";

    static String addSelectionStmString =
            "INSERT INTO course_selection.selection(selection_number, course_number, student_number)" +
                    "VALUES (?, ?, ?);";

    static String querySelectionDuplicateStmString =
            "SELECT COUNT(course_selection.selection.student_number) " +
                    "FROM course_selection.selection " +
                    "WHERE course_selection.selection.student_number = ? AND course_selection.selection.course_number = ? FOR UPDATE;";

    static String queryStudentClasstimeStmString = "SELECT course_selection.course.course_classtime, course_selection.course.course_weekday " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number) " +
            "WHERE student_number= ? FOR UPDATE;";

    // Delete
    static String addCourseRemainSelectionStmString = "SELECT course_remain FROM course_selection.course where course_number=? for UPDATE;";

    static String addCourseRemainUpdateStmString =  "UPDATE course_selection.course set course_remain = course_remain + 1 where course_number=? ;";

    static String deleteSelectionStmString =
            "DELETE FROM course_selection.selection " +
            "WHERE selection_number = ?;";

    // Query
    static String queryAllSelectionStmString =
            "SELECT * FROM course_selection.selection;";

    static String querySelectionByNumberStmString =
            "SELECT * FROM course_selection.selection " +
            "WHERE selection_number = ?; ";

    static String querySelectionByStudentStmString = "SELECT * " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
            "WHERE course_selection.selection.student_number= ?";

    static String querySelectionByInstructorStmString = "SELECT * " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
            "WHERE course_selection.instructor.Instructor_number= ?";

    static String querySelectionByStudentAndInstructorStmString = "SELECT * " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_number=course_selection.instructor.instructor_number) " +
            "WHERE course_selection.selection.student_number= ? AND course_selection.instructor.Instructor_number= ?";
}

