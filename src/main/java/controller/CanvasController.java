package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import main.java.model.Mandelbrot;
import main.java.utils.Constants;


public class CanvasController {
    @FXML
    public Canvas canvas;

    private double current_xpos = Constants.DEFAULT_XPOS;
    private double current_ypos = Constants.DEFAULT_YPOS;
    private double current_zoom = Constants.DEFAULT_ZOOM;

    public static double renderTime;

    public void initialize() {
        drawFractal();
        ControllerInstances.canvasController = this;
    }

    /**
     * Draw the Mandelbrot fractal onto the canvas
     */
    public void drawFractal() {
        PixelWriter drawer = canvas.getGraphicsContext2D().getPixelWriter();
        int mandelbrot_iters;

        long start = System.nanoTime();
        for (int i = 0; i < Constants.VIEW_WIDTH; i++) {
            for (int j = 0; j < Constants.VIEW_HEIGHT; j++) {
                mandelbrot_iters = Mandelbrot.computeIterations(
                        i / current_zoom + current_xpos,
                        current_ypos - j / current_zoom
                );
                drawer.setColor(i, j, getRGBColor(mandelbrot_iters));
            }
        }
        renderTime = (System.nanoTime() - start) / 1e9;
    }

    /**
     * Get the RGB color value based on the number of iterations taken to determine whether a point is in the
     * mandelbrot fractal or not.
     * <p>
     * Returns a value of Color.BLACK if the number of iterations taken to determine is equal to the MAX_ITERS
     * </p>
     */
    public Color getRGBColor(int iters) {
        if (iters == Constants.CURRENT_MAX_ITER)
            return Color.BLACK;
        double smooth_val = iters + 1 - Math.log(Math.log(Mandelbrot.z_n.modulus())) / Math.log(2);
        return Color.hsb(smooth_val, 0.8, 1.0);
    }

    /**
     * Zoom in/out the fractal at the point that was clicked on the canvas.
     * <p>
     * Primary Mouse Button = Zoom in
     * <p>
     * Secondary Mouse Button = Zoom out
     */
    public void zoomHandler(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY)
            adjustZoom(event.getSceneX(), event.getSceneY(), true);
        else if (event.getButton() == MouseButton.SECONDARY)
            adjustZoom(event.getSceneX(), event.getSceneY(), false);

        ControllerInstances.settingsController.zoomLevel.setText(String.format("%d", (int) current_zoom));
        ControllerInstances.settingsController.renderTime.setText("Rendered in " + renderTime + "s");
    }

    /**
     * Implementation of the zooming mechanics of the fractal. The new Zoom value is 1.25 times the original.
     */
    private void adjustZoom(double xPos, double yPos, boolean zoomingIn) {
        if (zoomingIn)
            current_zoom *= 1.25;
        else
            current_zoom /= 1.25;
        // find new center
        current_xpos = xPos / current_zoom + current_xpos;
        current_ypos = current_ypos - yPos / current_zoom;
        // re-adjust top-left
        current_xpos = current_xpos - (Constants.VIEW_WIDTH / 2) / current_zoom;
        current_ypos = current_ypos + (Constants.VIEW_HEIGHT / 2) / current_zoom;

        drawFractal();
    }

    /**
     * Show the Real and Imaginary values of the Canvas at the point where the mouse pointer is.
     */
    public void showReImVals(MouseEvent event) {
        ControllerInstances.settingsController.realVal.setText(
                String.valueOf(current_xpos + event.getSceneX() / current_zoom)
        );
        ControllerInstances.settingsController.imaginaryVal.setText(
                String.valueOf(current_ypos - event.getSceneY() / current_zoom)
        );
    }

    /**
     * Update the real and imaginary values when pointer leaves the canvas to value of the center of the canvas
     */
    public void showCenterVals() {
        ControllerInstances.settingsController.realVal.setText(
                String.valueOf(current_xpos + Constants.VIEW_WIDTH / (current_zoom * 2))
        );
        ControllerInstances.settingsController.imaginaryVal.setText(
                String.valueOf(current_ypos - Constants.VIEW_HEIGHT / (current_zoom * 2))
        );
    }

    /**
     * Reset the canvas to it's original values
     */
    public void resetValues() {
        current_xpos = Constants.DEFAULT_XPOS;
        current_ypos = Constants.DEFAULT_YPOS;
        current_zoom = Constants.DEFAULT_ZOOM;

        drawFractal();
    }
}
