package server.mysql.helper;

import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class CourseSelectionDBHelper {

    private PreparedStatement addInstructorStm = null;
    private PreparedStatement addStudentStm = null;
    private PreparedStatement queryAllStudentStm = null;
    private PreparedStatement addCourseStm = null;
    private PreparedStatement selectCourseStm = null;
    private PreparedStatement queryCourseByStudentStm = null;
    private PreparedStatement queryCourseByInstructorStm = null;

    private static class SingletonHolder {
        private static final CourseSelectionDBHelper INSTANCE = new CourseSelectionDBHelper();
    }

    public static CourseSelectionDBHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private CourseSelectionDBHelper() {
        try {
            // Register JDBC driver
            Class.forName(MySqlConfig.JDBC_DRIVER);

            // Open a connection
            System.out.println("Connecting to database...");
            Connection conn = null;
            conn = DriverManager.getConnection(MySqlConfig.DB_URL, MySqlConfig.USER, MySqlConfig.PASS);

            System.out.println("Creating prepare statement...");
            addInstructorStm = conn.prepareStatement(PrepareStatementUtils.addInstructorStmString);
            addStudentStm = conn.prepareStatement(PrepareStatementUtils.addStudentStmString);
            addCourseStm = conn.prepareStatement(PrepareStatementUtils.addCourseStmString);
            queryAllStudentStm = conn.prepareStatement(PrepareStatementUtils.queryAllStudentStmString);
            selectCourseStm = conn.prepareStatement(PrepareStatementUtils.selectCourseStmString);
            queryCourseByStudentStm = conn.prepareStatement(PrepareStatementUtils.queryCourseByStudentStmString);
            queryCourseByInstructorStm = conn.prepareStatement(PrepareStatementUtils.queryCourseByInstructorStmString);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean addInstructor(int instructorNumber, String instructorName, String office) {
        if (instructorName.length() <= 45 && office.length() == 4) {
            try {
                if (instructorNumber > 0)
                    addInstructorStm.setInt(1, instructorNumber);
                else
                    addInstructorStm.setNull(1, 0);
                addInstructorStm.setString(2, instructorName);
                addInstructorStm.setString(3, office);
                addInstructorStm.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean addStudent(int studentNumber, String studentName, String gender) {
        if (studentName.length() <= 45 && gender.length() <= 10) {
            try {
                if (studentNumber > 0)
                    addStudentStm.setInt(1, studentNumber);
                else
                    addStudentStm.setNull(1, 0);
                addStudentStm.setString(2, studentName);
                addStudentStm.setString(3, gender);
                addStudentStm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean checkClasstime(String classTime) {
        String[] timeArray = classTime.split(",");
        for (String timeString:timeArray) {
            int value = Integer.valueOf(timeString);
            if(!(value > 0 && value <= 8))
                return false;
        }
        return true;
    }

    public boolean addCourse(String courseNumber, String courseTitle, int instructorNumber, int courseSize, int courseWeekday, String courseClasstime) {
        if (courseNumber.length() == 5 && courseTitle.length() <= 45 &&
                courseSize > 0 && courseSize <= 255 && courseWeekday > 0 &&
                courseWeekday <= 7 && checkClasstime(courseClasstime)) {
            try {
                addCourseStm.setString(1, courseNumber);
                addCourseStm.setString(2, courseTitle);
                addCourseStm.setInt(3, instructorNumber);
                addCourseStm.setInt(4, courseSize);
                addCourseStm.setInt(5, courseWeekday);
                addCourseStm.setString(6, courseClasstime);
                addCourseStm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean selectCourse(String courseNumber, int studentNumber) {
        if (courseNumber.length() == 5 && studentNumber > 0) {
            try {
                selectCourseStm.setString(1, courseNumber);
                selectCourseStm.setInt(2, studentNumber);
                selectCourseStm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<JSONObject> queryAllStudent() {
        try {
            ResultSet rs = queryAllStudentStm.executeQuery();
            ArrayList<JSONObject> resultJsonList = new ArrayList();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
                obj.put(MySqlConfig.SHOW_STUDENT_NAME, rs.getString(MySqlConfig.COLUMN_STUDENT_NAME));
                obj.put(MySqlConfig.SHOW_STUDENT_GENDER, rs.getString(MySqlConfig.COLUMN_STUDENT_GENDER));
                resultJsonList.add(obj);
            }
            return resultJsonList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private JSONObject getCourseSelectionJson(ResultSet rs) {
        try {
            JSONObject obj = new JSONObject();
            obj.put(MySqlConfig.SHOW_STUDENT_NUMBER, rs.getInt(MySqlConfig.COLUMN_STUDENT_NUMBER));
            obj.put(MySqlConfig.SHOW_STUDENT_NAME, rs.getString(MySqlConfig.COLUMN_STUDENT_NAME));
            obj.put(MySqlConfig.SHOW_STUDENT_GENDER, rs.getString(MySqlConfig.COLUMN_STUDENT_GENDER));
            obj.put(MySqlConfig.SHOW_COURSE_NUMBER, rs.getString(MySqlConfig.COLUMN_COURSE_NUMBER));
            obj.put(MySqlConfig.SHOW_COURSE_TITLE, rs.getString(MySqlConfig.COLUMN_COURSE_TITLE));
            obj.put(MySqlConfig.SHOW_COURSE_SIZE, rs.getString(MySqlConfig.COLUMN_COURSE_SIZE));
            obj.put(MySqlConfig.SHOW_COURSE_WEEKDAY, rs.getString(MySqlConfig.COLUMN_COURSE_WEEKDAY));
            obj.put(MySqlConfig.SHOW_COURSE_CLASSTIME, rs.getString(MySqlConfig.COLUMN_COURSE_CLASSTIME));
            obj.put(MySqlConfig.SHOW_INSTRUCTOR_NUMBER, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_NUMBER));
            obj.put(MySqlConfig.SHOW_INSTRUCTOR_NAME, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_NAME));
            obj.put(MySqlConfig.SHOW_INSTRUCTOR_OFFICE, rs.getString(MySqlConfig.COLUMN_INSTRUCTOR_OFFICE));
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<JSONObject> queryCourseSelectionByStudent(int studentNumber) {
        try {
            queryCourseByStudentStm.setInt(1, studentNumber);
            ResultSet rs = queryCourseByStudentStm.executeQuery();
            ArrayList<JSONObject> resultJsonList = new ArrayList<>();
            while (rs.next()) {
                JSONObject obj = getCourseSelectionJson(rs);
                if(obj != null)
                    resultJsonList.add(obj);
                else
                    return new ArrayList<>();
            }
            return resultJsonList;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public ArrayList<JSONObject> queryCourseSelectionByInstructor(String instructorName) {
        if(instructorName.length() <= 45) {
            try {
                queryCourseByInstructorStm.setString(1, instructorName);
                ResultSet rs = queryCourseByInstructorStm.executeQuery();
                ArrayList<JSONObject> resultJsonList = new ArrayList<>();
                while (rs.next()) {
                    JSONObject obj = getCourseSelectionJson(rs);
                    if(obj != null)
                        resultJsonList.add(obj);
                    else
                        return new ArrayList<>();
                }
                return resultJsonList;
            } catch (SQLException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }


    public static void main(String[] args) {

        CourseSelectionDBHelper dbHelper = getInstance();
        ArrayList<JSONObject> jsonList;

//        System.out.println("Student list -- begin");
//        jsonList = dbHelper.queryAllStudent();
//        for (JSONObject obj:jsonList) {
//            System.out.println(obj.toString());
//        }
//        jsonList.clear();
//        System.out.println("Student list -- end");
//
//        System.out.println("Query by student -- begin");
//        jsonList = dbHelper.queryCourseSelectionByStudent(2);
//        for (JSONObject obj:jsonList) {
//            System.out.println(obj.toString());
//        }
//        jsonList.clear();
//        System.out.println("Query by student -- end");
//
//
//        System.out.println("Query by instructor -- begin");
//        jsonList = dbHelper.queryCourseSelectionByInstructor("Meryl Streep");
//        for (JSONObject obj:jsonList) {
//            System.out.println(obj.toString());
//        }
//        jsonList.clear();
//        System.out.println("Query by instructor -- end");

//        System.out.println("Add student: " + dbHelper.addStudent(11, "蔡依林", "Female"));
//        System.out.println("Add student: " + dbHelper.addInstructor(-1, "Gail", "S116"));
//        System.out.println("Add student: " + dbHelper.addCourse("MS534", "Music", 5, 20, 2, "5,6,7"));
//        System.out.println("Add student: " + dbHelper.selectCourse(-1, "Gail", "S116"));
    }
}
