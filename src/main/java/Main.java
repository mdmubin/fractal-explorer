package main.java;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        HBox root = FXMLLoader.load(getClass().getResource("view/application-view.fxml"));
        //init stage properties
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Mandelbrot Fractal Explorer");
        primaryStage.getIcons().add(new Image("res/images/fractal-spiral.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
