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

  /*  public ObservableList<String> getExams(){
        ArrayList<String> exams = model.courseNameQuerystmt();
        ObservableList<String> grade = FXCollections.observableArrayList(exams);
        return grade; */


    public void setView(StudentView view){
        this.view = view;
        view.exitBtn.setOnAction(e-> Platform.exit());
        EventHandler<ActionEvent> PrintStudentData = e-> HandlePrintStudentData(view.selectStudentComB.getValue(),
                view.selectCourseComb.getValue(), view.poodleText);
        view.findStudentBtn.setOnAction(PrintStudentData);

        EventHandler<ActionEvent> PrintCourseData = e-> HandlePrintCourseData(view.selectStudentComB.getValue(),
                view.selectCourseComb.getValue(), view.poodleText);
         view.findCourseBtn.setOnAction(PrintCourseData);

    }

    public void HandlePrintStudentData(String student, String course, TextArea poodleText){
        poodleText.clear();
        poodleText.appendText("Student: \n");
        model.preparedStmtQuery();
        // Convert the grade to float here. Maybe calculate the the avg here
        ArrayList<StudentModel.studentEnrollment> Data = model.FindStudentData(student, course, poodleText);

        // poodleText.appendText(String.valueOf(Data.size()));

        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText(Data.get(i).studentName + " " + Data.get(i).courseName + "\n");
        }
    }
    public void HandlePrintCourseData(String courseName, String teacher, TextArea poodleText){
        poodleText.clear();
        poodleText.appendText("Course: \n");
        model.preparedStmtQuery();
        // Convert the grade to float here. Maybe calculate the the avg here
        ArrayList<StudentModel.courseData> Data = model.FindCourseData(courseName, teacher, poodleText);

        // poodleText.appendText(String.valueOf(Data.size()));

        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText(Data.get(i).courseName + " " + Data.get(i).teacher + "\n");
        }
    }
}

