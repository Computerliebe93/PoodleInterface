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
        String sql = " SELECT Students.StudentName, City ,Courses FROM Students " +
                 " JOIN Exams as D2 ON Students.StudentName = D2.StudentName " +
                " WHERE Students.StudentName = ?; ";



        String sqlCourse = " SELECT course.CourseName, Teacher, studentName FROM course " +
                  " JOIN Exams ON course.CourseName = Exams.CourseName1 " +
                    " WHERE course.CourseName = ? ;";

        String sqlGrade = " SELECT Exams.StudentName, CourseName1, Grade FROM Exams " +
                " JOIN course ON course.CourseName = Exams.CourseName1 " +
                " WHERE course.CourseName = ? ;";

        String sqlAvgCourseGrade = " SELECT Exams.StudentName, CourseName1, avg(Grade) FROM Exams " +
                " JOIN course ON course.CourseName = Exams.CourseName1 " +
                " WHERE course.CourseName = ? ;";


            try {
                pstmt = conn.prepareStatement(sql);
                pstmt2 = conn.prepareStatement(sqlCourse);
                pstmt3 = conn.prepareStatement(sqlGrade);
                pstmt4 = conn.prepareStatement(sqlAvgCourseGrade);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println("test");
            }
        }

    public ArrayList<studentEnrollment> FindStudentData(String name, String city, TextArea poodleText){
        ArrayList<studentEnrollment> studentdata = new ArrayList<studentEnrollment>();
        try {
            pstmt.setString(1, name);
            ResultSet rs = null;
                 rs = pstmt.executeQuery();

            while (rs !=null && rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2));
                studentEnrollment pull = new studentEnrollment(rs.getString(1), rs.getString(2), rs.getString(3));
                studentdata.add(pull);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    return studentdata;
}

    static class studentEnrollment {
        String studentName;
        String city;
        String courseName;

        public studentEnrollment(String studentName, String city, String courseName) {
            this.studentName = studentName;
            this.city = city;
            this.courseName = courseName;

        }
    }

    public ArrayList<courseData> FindCourseData(String courseName, String course, TextArea poodleText){
        ArrayList<courseData> courseData = new ArrayList<courseData>();
        try {
            pstmt2.setString(1, course);
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
        String studentName;

        public courseData(String courseName, String teacher, String studentName) {
            this.courseName = courseName;
            this.teacher = teacher;
            this.studentName = studentName;

        }
    }
    public ArrayList<studentGrade> FindStudentGrade(String name, TextArea poodleText){
        ArrayList<studentGrade> gradeData = new ArrayList<studentGrade>();
        try {
            pstmt3.setString(1, name);
            ResultSet rs = null;
            rs = pstmt3.executeQuery();

            while (rs !=null && rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2));
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
    public ArrayList<avgCourseGrade> FindAvgCourseGrade(String name, TextArea poodleText){
        ArrayList<avgCourseGrade> avgCourseData = new ArrayList<avgCourseGrade>();
        try {
            pstmt4.setString(1, name);
            ResultSet rs = null;
            rs = pstmt4.executeQuery();

            while (rs !=null && rs.next()) {
                System.out.println(rs.getString(1) + rs.getString(2));
                avgCourseGrade pull = new avgCourseGrade(rs.getString(1), rs.getFloat(2));
                avgCourseData.add(pull);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return avgCourseData;
    }

    static class avgCourseGrade {
        String courseName;
        float avgGrade;

        public avgCourseGrade(String courseName, float avgGrade) {
            this.courseName = courseName;
            this.avgGrade = avgGrade;

        }
    }
}


