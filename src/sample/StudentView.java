package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class StudentView {
    StudentModel model;
    Controller control;
    private GridPane StartView;
    // Labels
    Label selectStudentLbl = new Label ("Select Student");
    Label selectCourseLbl = new Label("Select Course");
    // Buttons
    Button findStudentBtn = new Button("Find student");
    Button findCourseBtn = new Button("Find course");
    Button gradeStudentBtn = new Button("Course & grade data");
    Button exitBtn = new Button("Exit");

    TextArea poodleText = new TextArea();

    // Combo boxes
    ComboBox<String> selectStudentComB = new ComboBox();
    ComboBox<String> selectCourseComb = new ComboBox();


    public StudentView(StudentModel model, Controller control) {
        this.model = model;
        this.control = control;
        createAndConfigure();
    }

    // Interface design
    private void createAndConfigure() {
        StartView = new GridPane();
        StartView.setMinSize(300, 200);
        StartView.setPadding(new Insets(10, 10, 10, 10));
        StartView.setVgap(5);
        StartView.setHgap(1);

        StartView.add(selectStudentLbl, 1, 1);
        StartView.add(selectCourseLbl, 1, 3);
        StartView.add(findStudentBtn, 15, 6);
        StartView.add(findCourseBtn, 16, 6);
        StartView.add(gradeStudentBtn, 17, 6);
        StartView.add(exitBtn, 20, 15);
        StartView.add(poodleText, 1, 7, 20, 7);


        StartView.add(selectStudentComB, 15, 1);
        StartView.add(selectCourseComb, 15, 3);

        // Observable lists for labels
        ObservableList<String> studentName = control.getStudentName();
        selectStudentComB.setItems(control.getStudentName());
        selectStudentComB.getSelectionModel().selectFirst();

        ObservableList<String> courseName = control.getCourseName();
        selectCourseComb.setItems(control.getCourseName());
        selectCourseComb.getSelectionModel().selectFirst();

    }

    public Parent asParent() {
        return StartView;
    }
}
