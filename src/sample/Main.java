package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    static String url = "jdbc:sqlite:/Users/martinemildaafunder/Desktop/SD/Portfolio3Database/Untitled";
    static StudentModel STD = new StudentModel(url);
    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller controller = new Controller(STD);
        StudentView view = new StudentView(STD, controller);
        controller.setView(view);

        primaryStage.setTitle("Poodle STD");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
