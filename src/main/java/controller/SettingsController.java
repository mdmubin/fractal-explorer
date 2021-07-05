package main.java.controller;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import main.java.utils.Constants;

import javax.imageio.ImageIO;
import java.io.File;


/**
 * Controller for the Settings Pane
 */
public class SettingsController {
    @FXML Label      realVal;
    @FXML Button     snapshot;
    @FXML Label      zoomLevel;
    @FXML Label      renderTime;
    @FXML Button     resetButton;
    @FXML Label      imaginaryVal;
    @FXML Pane       settingsPane;
    @FXML TextField  iterationValue;
    @FXML Label      coordinatesLabel;
    @FXML Slider     iterationsSlider;


    public void initialize() {
        // update spinner values on slider changes
        iterationsSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
            iterationValue.setText(String.valueOf((int) iterationsSlider.getValue()));
            Constants.CURRENT_MAX_ITER = (int) iterationsSlider.getValue();
        });
        iterationsSlider.setValue(Constants.CURRENT_MAX_ITER);

        // convert the text-field to a number-field
        iterationValue.textProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                iterationValue.setText(newVal.replaceAll("[^\\d]", ""));
            }
            updateIterations();
        });
        iterationValue.setText(String.valueOf(Constants.CURRENT_MAX_ITER));

        // zoom level value
        zoomLevel.setText(String.valueOf(Constants.DEFAULT_ZOOM));

        // time to render mandelbrot set
        renderTime.setText("Rendered in " + CanvasController.renderTime + "s");

        ControllerInstances.settingsController = this;
    }

    /**
     * Take a snapshot of the canvas on mouse click
     */
    public void takeSnapshot(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            try {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extensions = new FileChooser.ExtensionFilter("Image File(s)", "*.png");

                fileChooser.getExtensionFilters().add(extensions);
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.setInitialFileName("mandelbrot");

                File imageFile = fileChooser.showSaveDialog(settingsPane.getScene().getWindow());
                if (imageFile != null)
                    ImageIO.write(SwingFXUtils.fromFXImage(
                            ControllerInstances.canvasController.canvas.snapshot(null, null), null),
                            "png", imageFile
                    );
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not save Snapshot", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void resetCanvas() {
        ControllerInstances.canvasController.resetValues();
    }

    /**
     * Update the Max Iterations for the Mandelbrot set upon receiving a valid iteration count
     */
    private void updateIterations() {
        try {
            int newIterations = Integer.parseInt(iterationValue.getText());
            if (newIterations > 5000)
                throw new Exception();
            else {
                iterationsSlider.setValue(newIterations);
                Constants.CURRENT_MAX_ITER = newIterations;
            }
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Invalid Iteration count.\nIteration counts greater 5000 becomes too slow",
                    ButtonType.OK);
            alert.showAndWait();
            iterationValue.setText(String.valueOf((int) iterationsSlider.getValue()));
        }
    }
}
