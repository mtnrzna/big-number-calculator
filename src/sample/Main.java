package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
        try {
            Image image = new Image("file:icon.png");
            primaryStage.getIcons().add(image);
        }
        catch(Exception e) {
            System.out.println("Icon image not found!");
        }
        primaryStage.setTitle("Calculator");
        primaryStage.setResizable(false);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
