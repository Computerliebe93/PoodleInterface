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
    PreparedStatement pstmt3 = null;
    PreparedStatement pstmt4 = null;
    PreparedStatement pstmt5 = null;

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

    // prepared statement for student label
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
    // Prepared statement SQL for course label
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

    // Prepared statements SQL
    public void preparedStmtQuery() {
        String sql = " SELECT Students.StudentName, City FROM Students " +
                 " JOIN Exams as D2 ON Students.StudentName = D2.StudentName  " +
                " WHERE Students.StudentName = ?; ";

        String sqlCourse = " SELECT course.CourseName, Teacher, studentName FROM course " +
                  " JOIN Exams ON course.CourseName = Exams.CourseName1 " +
                    " WHERE course.CourseName = ? ;";

        String sqlGrade = " SELECT Exams.StudentName, CourseName1, Grade FROM Exams " +
                " JOIN course ON course.CourseName = Exams.CourseName1 " +
                " WHERE course.CourseName = ? ;";

        String sqlAvgCourseGrade = " SELECT AVG(Grade)  FROM Exams " +
                " WHERE Exams.CourseName1 = ? ;";

        String sqlAvgStudentGrade = " SELECT AVG(Grade)  FROM Exams " +
                " WHERE Exams.StudentName = ? ;";


            try {
                pstmt = conn.prepareStatement(sql);
                pstmt2 = conn.prepareStatement(sqlCourse);
                pstmt3 = conn.prepareStatement(sqlGrade);
                pstmt4 = conn.prepareStatement(sqlAvgCourseGrade);
                pstmt5 = conn.prepareStatement(sqlAvgStudentGrade);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("test");
            }
        }

        // Find stundent method
    public ArrayList<studentEnrollment> FindStudentData(String name, String city, TextArea poodleText){
        ArrayList<studentEnrollment> studentData = new ArrayList<studentEnrollment>();
        try {
            pstmt.setString(1, name);
            ResultSet rs = null;
                 rs = pstmt.executeQuery();

            while (rs !=null && rs.next()) {
                studentEnrollment pull = new studentEnrollment(rs.getString(1), rs.getString(2));
                studentData.add(pull);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    return studentData;
}

    static class studentEnrollment {
        String studentName;
        String city;

        public studentEnrollment(String studentName, String city) {
            this.studentName = studentName;
            this.city = city;

        }
    }

    // Find course method
    public ArrayList<courseData> FindCourseData(String courseName, String course, TextArea poodleText){
        ArrayList<courseData> courseData = new ArrayList<courseData>();
        try {
            pstmt2.setString(1, course);
            ResultSet rs = null;
            rs = pstmt2.executeQuery();

            while (rs !=null && rs.next()) {
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
        String studentName;

        public courseData(String courseName, String teacher, String studentName) {
            this.courseName = courseName;
            this.teacher = teacher;
            this.studentName = studentName;

        }
    }

    // Find student grade method
    public ArrayList<studentGrade> FindStudentGrade(String name, TextArea poodleText){
        ArrayList<studentGrade> gradeData = new ArrayList<studentGrade>();
        try {
            pstmt3.setString(1, name);
            ResultSet rs = null;
            rs = pstmt3.executeQuery();

            while (rs !=null && rs.next()) {
                studentGrade pull = new studentGrade(rs.getString(1), rs.getString(2), rs.getInt(3));
                gradeData.add(pull);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return gradeData;
    }

    static class studentGrade {
        String studentName;
        String courseName;
        Integer grade;

        public studentGrade(String studentName, String courseName, Integer grade) {
            this.studentName = studentName;
            this.courseName = courseName;
            this.grade = grade;

        }
    }

    // Find average grade for course and student method
    public Float FindAvgCourseGrade(String course){
        return getaFloat(course, pstmt4);
    }
    public Float FindAvgStudentGrade(String course){
        return getaFloat(course, pstmt5);
    }

    private Float getaFloat(String course, PreparedStatement pstmt5) {
        Float avgStudentGrade = null;
        try {
            pstmt5.setString(1, (course));
            ResultSet rs = null;
            rs = pstmt5.executeQuery();

            while (rs !=null && rs.next()) {
                System.out.println(rs.getFloat(1));
                avgStudentGrade = (rs.getFloat(1));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(avgStudentGrade);
        return avgStudentGrade;
    }
}


