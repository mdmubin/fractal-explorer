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
        // the root node for the application
        HBox root = new HBox();
        // set scene for the application
        Scene scene = new Scene(root);

        Pane canvasPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/canvas-pane.fxml")));
//        CanvasControl.setControlOperations((Canvas) canvasPane.getChildren().get(0));
        root.getChildren().add(canvasPane);

        //init stage properties
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mandelbrot Fractal Explorer");
        primaryStage.getIcons().add(new Image("res/images/fractal-spiral.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
