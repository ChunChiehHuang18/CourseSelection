package server.jsersey.servlet;

import server.mysql.helper.CourseSelectionDBHelper;
import server.mysql.helper.MySqlConfig;

import java.util.Arrays;

/**
 * ServletUtils place servlet post data related value and check post data valid API
 */
public class ServletUtils {
    private static class SingletonHolder {
        private static final ServletUtils INSTANCE = new ServletUtils();
    }

    private ServletUtils() {
        System.out.println("ServletUtils init...");
    }

    /**
     * Generate ServletUtils single instance
     * @return ServletUtils instance
     */
    static ServletUtils getInstance() {
        return ServletUtils.SingletonHolder.INSTANCE;
    }

    // Student
    final static String KEY_STUDENT_NUMBER = "Student_Number";
    final static String KEY_STUDENT_NAME = "Student_Name";
    final static String KEY_STUDENT_GENDER = "Student_Gender";

    /**
     * Check student insert data is valid
     * @param studentName Student's number
     * @param gender Student's gender
     * @return True: Valid, False: Invalid
     */
    boolean validStudentData(String studentName, String gender) {
        return (studentName.length() <= 45 && validGender(gender));
    }

    /**
     * Check gender string is valid
     * @param gender Gender can be Male or Female or Bisexual
     * @return True: Valid, False: Invalid
     */
    private boolean validGender(String gender) {
        return (gender.equalsIgnoreCase("Male") ||
                gender.equalsIgnoreCase("Female") ||
                gender.equalsIgnoreCase("Bisexual"));
    }

    // Instructor
    final static String KEY_INSTRUCTOR_NUMBER = "Instructor_Number";
    final static String KEY_INSTRUCTOR_NAME = "Instructor_Name";
    final static String KEY_INSTRUCTOR_OFFCIE = "Instructor_Office";

    /**
     * Check instructor insert data is valid
     * @param instructorName Instructor's name
     * @param office Instructor's office, fixed to 4 char
     * @return True: Valid, False: Invalid
     */
    boolean validInstructorData(String instructorName, String office) {
        return (instructorName.length() <= 45 && (office.equalsIgnoreCase(MySqlConfig.VALUE_NULL) || office.length() == 4));
    }

    // course
    final static String KEY_COURSE_NUMBER = "Course_Number";
    final static String KEY_COURSE_TITLE = "Course_Title";
    final static String KEY_COURSE_SIZE = "Course_Size";
    final static String KEY_COURSE_WEEKDAY = "Course_Weekday";
    final static String KEY_COURSE_CLASSTIME = "Course_Classtime";

    /**
     * Check course insert data is valid
     * @param courseNumber Course's number, fixed to 5 char
     * @param courseTitle Course's title
     * @param instructorNumber Instructor's number
     * @param courseSize Course's course size (10 ~ 255)
     * @param courseWeekday  Course's week day(1~5)
     * @param courseClasstime  Course's class time (1~8), can be multiple(2,3,4)
     * @return True: Valid, False: Invalid
     */
    boolean validCourseData(String courseNumber, String courseTitle, int instructorNumber,
                                   int courseSize, int courseWeekday, String courseClasstime) {
//        System.out.println("courseNumber: " + courseNumber);
//        System.out.println("courseTitle: " + courseTitle);
//        System.out.println("instructorNumber: " + instructorNumber);
//        System.out.println("courseSize: " + courseSize);
//        System.out.println("courseWeekday: " + courseWeekday);
//        System.out.println("courseClasstime: " + courseClasstime);
        return (courseNumber.length() == 5 && courseTitle.length() <= 45 &&
                instructorNumber > 0 && courseSize >= 10 && courseSize <= 255 &&
                courseWeekday >= 1 && courseWeekday <= 5 && validClasstimeString(courseClasstime));
    }

    /**
     * 1.Check course number length, fixed to 5 char
     * 2.Check course exist
     * @param courseNumber Course's number
     * @return True: Valid, False: Invalid
     */
    boolean validDeleteCourseData(String courseNumber) {
        if(courseNumber.length() == 5 ) {
            CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

            return !dbHelper.queryCourseByNumber(courseNumber).isEmpty();
        } else
            return false;
    }

    /**
     * Check class time string is valid
     * @param classTime Class time can be between 1 and 8 and cannot be repeated
     * @return True: Valid, False: Invalid
     */
    private boolean validClasstimeString(String classTime) {
        boolean[] classtimeOccupy = new boolean[8];
        Arrays.fill(classtimeOccupy, Boolean.FALSE);
        String[] timeArray = classTime.split(",");
        for (String timeString : timeArray) {
            int value = Integer.valueOf(timeString);
            if (!(value >= 1 && value <= 8))
                return false;
            else if (!classtimeOccupy[value - 1]) {
                classtimeOccupy[value - 1] = Boolean.TRUE;
            } else {
                return false;
            }
        }
        return true;
    }

    // selection
    final static String KEY_SELECTION_NUMBER = "Selection_Number";

    /**
     * Check basic student number and course course  is legal
     * @param courseNumber Course's number
     * @param studentNumber Student's number
     * @return True: Valid, False: Invalid
     */
    boolean validSelectionData(int studentNumber, String courseNumber) {
        return (studentNumber > 0 && courseNumber.length() == 5);
    }


    /**
     * Check basic selection number is legal
     * @param selectionNumber Selection's number
     * @return True: Valid, False: Invalid
     */
    boolean validDeleteSelectionData(int selectionNumber) {
        return selectionNumber > 0;
    }


}
