package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tim(Hustl)er");
        Scene xscene = new Scene(root, 577, 412);
        primaryStage.setScene(xscene);
        root.getStylesheets().add("styles/styles.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
