package server.mysql.helper;

public class PrepareStatementUtils {
    static String addInstructorStmString =
            "INSERT INTO course_selection.instructor(instructor_name, instructor_office)" +
                    "VALUES (?, ?);";
    static String addStudentStmString =
            "INSERT INTO course_selection.student(student_number, student_name, student_gender)" +
                    "VALUES (?, ?, ?);";
    static String queryAllStudentStmString =
            "SELECT * FROM course_selection.student;";
    static String addCourseStmString =
            "INSERT INTO course_selection.course(course_number, course_title, instructor_name)" +
                    "VALUES (?, ?, ?);";
    static String selectCourseStmString =
            "INSERT INTO course_selection.selection(course_number, student_number)" +
                    "VALUES (?, ?);";
    static String queryCourseByStudentStmString = "SELECT course_selection.student.student_number, " +
            "course_selection.student.student_name, course_selection.student.student_gender, course_selection.course.course_number, " +
            "course_selection.course.course_title, course_selection.instructor.Instructor_name,  course_selection.instructor.Instructor_office " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_name=course_selection.instructor.Instructor_name) " +
            "WHERE course_selection.selection.student_number= ?";

    static String queryCourseByInstructorStmString = "SELECT course_selection.student.student_number, " +
            "course_selection.student.student_name, course_selection.student.student_gender, course_selection.course.course_number, " +
            "course_selection.course.course_title, course_selection.instructor.Instructor_name,  course_selection.instructor.Instructor_office " +
            "FROM (course_selection.selection " +
            "JOIN course_selection.student ON course_selection.student.student_number=course_selection.selection.student_number " +
            "JOIN course_selection.course ON course_selection.course.course_number=course_selection.selection.course_number " +
            "JOIN course_selection.instructor ON course_selection.course.instructor_name=course_selection.instructor.Instructor_name) " +
            "WHERE course_selection.instructor.Instructor_name= ?";
}

