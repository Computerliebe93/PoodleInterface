package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    StudentModel model;
    StudentView view;

    public Controller(StudentModel model) {
        this.model = model;
        try {
            model.connect();
            model.CreateStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<String> getStudentName() {
        ArrayList<String> names = model.studentNameQuerystmt();
        ObservableList<String> studentNames = FXCollections.observableArrayList(names);
        return studentNames;
    }

    public ObservableList<String> getCourseName(){
        ArrayList<String> courses = model.courseNameQuerystmt();
        ObservableList<String> courseNames = FXCollections.observableArrayList(courses);
        return courseNames;
    }

    // Event handler that handles the press of a button
    public void setView(StudentView view){
        this.view = view;
        view.exitBtn.setOnAction( e-> Platform.exit());
        // Student select
        EventHandler<ActionEvent> PrintStudentData = e-> HandlePrintStudentData(view.selectStudentComB.getValue(),
                view.selectCourseComb.getValue(), view.poodleText);
        view.findStudentBtn.setOnAction(PrintStudentData);

        // Course select
        EventHandler<ActionEvent> PrintCourseData = e-> HandlePrintCourseData(view.selectStudentComB.getValue(),
                view.selectCourseComb.getValue(), view.poodleText);
         view.findCourseBtn.setOnAction(PrintCourseData);

         // Grade select student
        EventHandler<ActionEvent> PrintGradeData = e-> HandlePrintGradeSData(view.selectStudentComB.getValue(),
                view.selectCourseComb.getValue(),view.poodleText);
        view.gradeStudentBtn.setOnAction(PrintGradeData);

    }

    // Print student data handler + average grade
    public void HandlePrintStudentData(String name, String city, TextArea poodleText){
        poodleText.clear();
        model.preparedStmtQuery();
        ArrayList<StudentModel.studentEnrollment> Data = model.FindStudentData(name, city, poodleText);
        Float avgStudentGrade = model.FindAvgStudentGrade(name);


        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText("Student: " + Data.get(i).studentName + " - City: " + Data.get(i).city + "\n");
        }
        poodleText.appendText("Average grade: " + avgStudentGrade);
    }

    // Print Course data handler + average grade
    public void HandlePrintCourseData(String studentName, String  course, TextArea poodleText){
        poodleText.clear();
        model.preparedStmtQuery();
        ArrayList<StudentModel.courseData> Data = model.FindCourseData(studentName, course, poodleText);
        Float avgGrade = model.FindAvgCourseGrade(course);


        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText("Student: " +Data.get(i).studentName + " - Course: " + Data.get(i).courseName + " - Teacher: " + Data.get(i).teacher + "\n");
        }
        poodleText.appendText("Average grade: " + avgGrade);
    }

    // Print grades handler
    public void HandlePrintGradeSData(String value, String studentName, TextArea poodleText){
        poodleText.clear();
        model.preparedStmtQuery();
        ArrayList<StudentModel.studentGrade> Data = model.FindStudentGrade(studentName, poodleText);

        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText("Student: " + Data.get(i).studentName + " - Course: " + Data.get(i).courseName + " - Grade: " + Data.get(i).grade + "\n");
        }
    }
}

