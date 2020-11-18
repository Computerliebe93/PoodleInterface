package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
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

    public ObservableList<String> getExams(){
        ArrayList<String> exams = model.courseNameQuerystmt();
        ObservableList<String> grade = FXCollections.observableArrayList(exams);
        return grade;
    }

    public void setView(StudentView view){
        this.view = view;
        view.exitBtn.setOnAction(e-> Platform.exit());
        EventHandler<ActionEvent> PrintStudentData = e-> HandlePrintStudentData(view.selectStudentComB.getValue(),
                view.selectCourseComb.getValue(), view.selectExamComb.getValue(), view.poodleText); //ETC 2:55
        view.searchBtn.setOnAction(PrintStudentData);
    }

    public void HandlePrintStudentData(String student, String course, String exam, TextArea poodleText){
        poodleText.clear();
        poodleText.appendText("Student: ");
        model.preparedStmtTQuery();
        // Convert the grade to float here. Maybe calculate the the avg here
        ArrayList<StudentModel.studentEnrollment> Data = model.FindStudentData(student, course, exam);
        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText(Data.get(i).studentName + " " + Data.get(i).courseName +
                    " " + Data.get(i).grade + " " + Data.get(i).grade);
        }
    }
}
