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

    public ArrayList<String> examsQuerystmt() {
        ArrayList<String> exams = new ArrayList<String>();
        String sql = "SELECT CourseName1 FROM Exams ;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                exams.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return exams;
    }

    public void preparedStmtTQuery() {
        String sql = " SELECT D1.StudentName, D1.CourseName, D1.Exam FROM Students as D1 " +
                " JOIN EXAMS as D2 ON D1.StudentName = D2.StudentName " +
                " WHERE D1.StudentName = ? D2.StudentName; ";
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<studentEnrollment> FindStudentData(String name, String course, String exam){
        ArrayList<studentEnrollment> data = new ArrayList<studentEnrollment>();
        try {
            pstmt.setString(1, name);
            pstmt.setString(2, course);
            pstmt.setString(3, exam);
            //pstmt.setInt(4, grade);
            ResultSet rs = pstmt.executeQuery();
            while (rs !=null && rs.next()) {
                studentEnrollment pull = new studentEnrollment (rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    return data;
}

    class studentEnrollment {
        String studentName;
        String courseName;
        String exam;
        Integer grade;

        public studentEnrollment(String studentName, String courseName, String exam, Integer grade) {
            this.studentName = studentName;
            this.courseName = courseName;
            this.exam = exam;
            this.grade = grade;
        }
    }
}


