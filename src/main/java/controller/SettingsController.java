package main.java.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import main.java.utils.Constants;

import javax.imageio.ImageIO;
import java.io.File;


public class SettingsController {
    @FXML
    Slider iterationsSlider;
    @FXML
    Spinner<Integer> iterationSpinner; //probably gonna change this to a label or number field. feels unnecessary
    @FXML
    Label renderTime;
    @FXML
    Pane settingsPane;
    @FXML
    Button resetButton;
    @FXML
    Button snapshot;
    @FXML
    Label imaginaryVal;
    @FXML
    Label realVal;
    @FXML
    Label zoomLevel;


    public void initialize() {
        // update spinner values on slider changes
        iterationsSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
            iterationSpinner.getValueFactory().setValue(newVal.intValue());
        });
        iterationsSlider.setValue(Constants.CURRENT_MAX_ITER);

        // update Max Iteration Count on spinner value change
        iterationSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            Constants.CURRENT_MAX_ITER = iterationSpinner.getValue();
        });
        iterationSpinner.getValueFactory().setValue(Constants.CURRENT_MAX_ITER);

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
                ImageIO.write(SwingFXUtils.fromFXImage(
                        ControllerInstances.canvasController.canvas.snapshot(null, null), null),
                        "png", imageFile
                );
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void resetCanvas() {
        ControllerInstances.canvasController.resetValues();
    }
}
