package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
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
        view.GradeStudentBtn.setOnAction(PrintGradeData);

    }

    public void HandlePrintStudentData(String course, String city, TextArea poodleText){
        poodleText.clear();
        poodleText.appendText("Student: \n");
        model.preparedStmtQuery();
        // Convert the grade to float here. Maybe calculate the the avg here
        ArrayList<StudentModel.studentEnrollment> Data = model.FindStudentData(course, city, poodleText);
        Float avgStudentGrade = model.FindAvgStudentGrade(course);

        // poodleText.appendText(String.valueOf(Data.size()));

        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText("Student: " + Data.get(i).studentName + " - City: " + Data.get(i).city + " - Course: " + Data.get(i).courseName + "\n");
        }
        poodleText.appendText("Average grade: " + avgStudentGrade);
    }
    public void HandlePrintCourseData(String studentName, String  course, TextArea poodleText){
        poodleText.clear();
        poodleText.appendText("Course: \n");
        model.preparedStmtQuery();
        // Convert the grade to float here. Maybe calculate the the avg here
        ArrayList<StudentModel.courseData> Data = model.FindCourseData(studentName, course, poodleText);
        Float avgGrade = model.FindAvgCourseGrade(course);

        // poodleText.appendText(String.valueOf(Data.size()));

        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText("Student: " +Data.get(i).studentName + " - Course: " + Data.get(i).courseName + " - Teacher: " + Data.get(i).teacher + "\n");
        }
        poodleText.appendText("Average grade: " + avgGrade);
    }
    public void HandlePrintGradeSData(String value, String studentName, TextArea poodleText){
        poodleText.clear();
        poodleText.appendText("Grade: \n");
        model.preparedStmtQuery();
        // Convert the grade to float here. Maybe calculate the the avg here
        ArrayList<StudentModel.studentGrade> Data = model.FindStudentGrade(studentName, poodleText);

        // poodleText.appendText(String.valueOf(Data.size()));

        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText("Student: " + Data.get(i).studentName + " - Course: " + Data.get(i).courseName + " - Grade: " + Data.get(i).grade + "\n");
        }
    }
/*    public void HandlePrintAvgCourseGradeSData(String course, TextArea poodleText){
        poodleText.clear();
        poodleText.appendText("Grade: \n");
        model.preparedStmtQuery();
        // Convert the grade to float here. Maybe calculate the the avg here
        ArrayList<StudentModel.avgCourseGrade> Data = model.FindAvgCourseGrade(course);

        // poodleText.appendText(String.valueOf(Data.size()));

        for (int i = 0; i < Data.size(); i++)
        {
            poodleText.appendText("Grade: " + (Data.get(i).avgGrade) + "\n");
        }
    } */
}

