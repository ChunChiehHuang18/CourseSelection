package server.jsersey.servlet;

import org.json.JSONArray;
import server.mysql.helper.CourseSelectionDBHelper;
import server.mysql.helper.MySqlConfig;

import java.util.Arrays;

/**
 * ServletUtils store servlet post data related value and check post data valid API
 */
public class ServletUtils {
    private static class SingletonHolder {
        private static final ServletUtils INSTANCE = new ServletUtils();
    }

    private ServletUtils() {
        System.out.println("ServletUtils init...");
    }

    public static ServletUtils getInstance() {
        return ServletUtils.SingletonHolder.INSTANCE;
    }

    // Action
    final static String KEY_ACTION = "Action";
    final static String ACTION_ADD = "Add";
    final static String ACTION_UPDATE = "Update";

    // Student
    final static String KEY_STUDENT_NUMBER = "Student_Number";
    final static String KEY_STUDENT_NAME = "Student_Name";
    final static String KEY_STUDENT_GENDER = "Student_Gender";

    boolean validStudentData(String studentName, String gender) {
        return (studentName.length() <= 45 && validGender(gender));
    }

    private boolean validGender(String gender) {
        return (gender.equalsIgnoreCase("Male") ||
                gender.equalsIgnoreCase("Female") ||
                gender.equalsIgnoreCase("Bisexual"));
    }

    // Instructor
    final static String KEY_INSTRUCTOR_NUMBER = "Instructor_Number";
    final static String KEY_INSTRUCTOR_NAME = "Instructor_Name";
    final static String KEY_INSTRUCTOR_OFFCIE = "Instructor_Office";

    boolean validInstructorData(String instructorName, String office) {
        return (instructorName.length() <= 45 && (office.equalsIgnoreCase(MySqlConfig.VALUE_NULL) || office.length() == 4));
    }

    // course
    final static String KEY_COURSE_NUMBER = "Course_Number";
    final static String KEY_COURSE_TITLE = "Course_Title";
    final static String KEY_COURSE_SIZE = "Course_Size";
    final static String KEY_COURSE_WEEKDAY = "Course_Weekday";
    final static String KEY_COURSE_CLASSTIME = "Course_Classtime";

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
    boolean validSelectionData(int studentNumber, String courseNumber) {
        if(studentNumber > 0 && courseNumber.length() == 5) {
            CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
            boolean duplicate = dbHelper.querySelectionDuplicate(studentNumber, courseNumber);
//            System.out.println("duplicate: " + duplicate);

//            if(duplicate)
//                System.out.println("duplicate");
//            if (exceedCourseSize(courseNumber))
//                System.out.println("exceedCourseSize");
//            if(occupyClasstime(studentNumber, courseNumber))
//                System.out.println("occupyClasstime");

            return (!duplicate && !exceedCourseSize(courseNumber) && !occupyClasstime(studentNumber, courseNumber));
        } else
            return false;
    }

    private boolean exceedCourseSize(String courseNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();
        int courseSize = dbHelper.queryCourseByNumber(courseNumber).getInt(MySqlConfig.SHOW_COURSE_SIZE);
        int selectionCount = dbHelper.querySelectionCountByCourse(courseNumber);
//        System.out.println("courseSize: " + courseSize);
//        System.out.println("selectionCount: " + selectionCount);
        return selectionCount + 1 > courseSize;
    }

    private boolean occupyClasstime(int studentNumber, String courseNumber) {
        CourseSelectionDBHelper dbHelper = CourseSelectionDBHelper.getInstance();

        // Get student's current occupy classtime
        JSONArray studenClasstime = dbHelper.queryStudentClasstime(studentNumber);
        boolean[][] classtimeOccupy = new boolean[5][8];
        for (boolean[] row: classtimeOccupy)
            Arrays.fill(row, Boolean.FALSE);
        for (int i = 0; i < studenClasstime.length(); i++) {
            int weekday = studenClasstime.getJSONObject(i).getInt(MySqlConfig.SHOW_COURSE_WEEKDAY);
            String[] classtime = studenClasstime.getJSONObject(i).getString(MySqlConfig.SHOW_COURSE_CLASSTIME).split(",");
            for (String time: classtime) {
                classtimeOccupy[weekday - 1][Integer.valueOf(time) - 1] = Boolean.TRUE;
            }
        }

        // Get selection classtime
        int selectiontWeekday = dbHelper.queryCourseByNumber(courseNumber).getInt(MySqlConfig.SHOW_COURSE_WEEKDAY);
        String[] selectionClassTime = dbHelper.queryCourseByNumber(courseNumber).getString(MySqlConfig.SHOW_COURSE_CLASSTIME).split(",");
        for (String time: selectionClassTime) {
            if(classtimeOccupy[selectiontWeekday - 1][Integer.valueOf(time) - 1])
                return true;
        }
        return false;
    }

}
