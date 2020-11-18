package sample;

import javafx.scene.control.TextArea;

import java.sql.*;
import java.util.ArrayList;
import static java.sql.DriverManager.getConnection;
import java.sql.Statement;

public class StudentModel {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;

    String url;

    public StudentModel(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(this.url);
    }

    public void CreateStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    public ArrayList<String> studentNameQuerystmt() {
        ArrayList<String> studentNames = new ArrayList<String>();
        String sql = "SELECT StudentName FROM students ;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                studentNames.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return studentNames;
    }

    public ArrayList<String> courseNameQuerystmt() {
        ArrayList<String> courseNames = new ArrayList<String>();
        String sql = "SELECT CourseName FROM course ;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                courseNames.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return courseNames;
    }


    public void preparedStmtQuery() {
        String sql = " SELECT Students.StudentName, Courses FROM Students " +
                 " JOIN Exams as D2 ON Students.StudentName = D2.StudentName " +
                " WHERE Students.StudentName = ?; ";



        String sqlCourse = " SELECT course.CourseName, Teacher, studentName FROM course " +
                  " JOIN Exams ON course.CourseName = Exams.CourseName1 " +
                    " WHERE course.CourseName = ? ;";



                /* " SELECT course.CourseName, Teacher FROM course ;" +
                            " JOIN EXAMS as D2 ON course.CourseName1 = D2.Grade " +
                            " WHERE course.CourseName = ?; "; */
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt2 = conn.prepareStatement(sqlCourse);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("test");
            }
        }

    public ArrayList<studentEnrollment> FindStudentData(String name, String course, TextArea poodleText){
        ArrayList<studentEnrollment> studentdata = new ArrayList<studentEnrollment>();
        try {
            pstmt.setString(1, name);
           // pstmt.setString(2, course);
            //pstmt.setString(3, exam);
            //pstmt.setInt(4, grade);
            ResultSet rs = null;
                 rs = pstmt.executeQuery();

            while (rs !=null && rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2));
                studentEnrollment pull = new studentEnrollment(rs.getString(1), rs.getString(2));
                studentdata.add(pull);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    return studentdata;
}

    static class studentEnrollment {
        String studentName;
        String courseName;

        public studentEnrollment(String studentName, String courseName) {
            this.studentName = studentName;
            this.courseName = courseName;

        }
    }


    public ArrayList<courseData> FindCourseData(String courseName, String course, TextArea poodleText){
        ArrayList<courseData> courseData = new ArrayList<courseData>();
        try {
            pstmt2.setString(1, course);
            // pstmt.setString(2, course);
            //pstmt.setString(3, exam);
            //pstmt.setInt(4, grade);
            ResultSet rs = null;
            rs = pstmt2.executeQuery();

            while (rs !=null && rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2));
                courseData pull = new courseData(rs.getString(1), rs.getString(2), rs.getString(3));
                courseData.add(pull);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return courseData;
    }

    static class courseData {
        String courseName;
        String teacher;

        public courseData(String courseName, String teacher, String studentName) {
            this.courseName = courseName;
            this.teacher = teacher;

        }
    }
}


