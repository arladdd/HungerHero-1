package Aplikasi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Aplikasi/View/loginPage.fxml")));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/Aplikasi/View/Design.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("HungerHero");

        // Load the icon file
        String iconPath = getClass().getResource("/Aplikasi/image/hhero.png").toExternalForm();
        primaryStage.getIcons().add(new Image(iconPath));

        primaryStage.show();
    }

}