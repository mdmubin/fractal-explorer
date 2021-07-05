package main.java.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
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


public class SettingsPanel {
    @FXML
    public Slider iterationsSlider;
    @FXML
    public Spinner<Integer> iterationSpinner;
    @FXML
    public Label renderTime;
    @FXML
    public Pane settingsPane;
    @FXML
    private Label zoomLevel;


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
        renderTime.setText("Rendered in " + CanvasPanel.renderTime + "s");
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
                Canvas canvas = (Canvas) settingsPane.getParent().getChildrenUnmodifiable().get(0);
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, null), null), "png", imageFile);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
