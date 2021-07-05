package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import main.java.model.Mandelbrot;
import main.java.utils.Constants;


public class CanvasPanel {
    @FXML
    public Canvas canvas;

    private double current_xpos = Constants.DEFAULT_XPOS;
    private double current_ypos = Constants.DEFAULT_YPOS;
    private double current_zoom = Constants.DEFAULT_ZOOM;

    public static double renderTime;

    public void initialize() {
        drawFractal();
    }

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
                drawer.setColor(i, j, getColor(mandelbrot_iters));
            }
        }
        renderTime = (System.nanoTime() - start) / 1e9;
    }

    public Color getColor(int iters) {
        if (iters == Constants.CURRENT_MAX_ITER)
            return Color.BLACK;
        double smooth_val = iters + 1 - Math.log(Math.log(Mandelbrot.z_n.modulus())) / Math.log(2);
        return Color.hsb(smooth_val, .5, 1.0);
    }

    public void zoomHandler(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY)
            adjustZoom(event.getSceneX(), event.getSceneY(), true);
        else if (event.getButton() == MouseButton.SECONDARY)
            adjustZoom(event.getSceneX(), event.getSceneY(), false);

        Label zoom = (Label) canvas.getParent().getChildrenUnmodifiable().get(1).lookup("#zoomLevel");
        Label renderLabel = (Label) canvas.getParent().getChildrenUnmodifiable().get(1).lookup("#renderTime");

        zoom.setText(String.format("%d", (int) current_zoom));
        renderLabel.setText("Rendered in " + renderTime + "s");
    }

    public void adjustZoom(double xPos, double yPos, boolean zoomingIn) {
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

    public void showMouseXY(MouseEvent event) {
        Label re_val = (Label) canvas.getParent().getChildrenUnmodifiable().get(1).lookup("#realVal");
        Label im_val = (Label) canvas.getParent().getChildrenUnmodifiable().get(1).lookup("#imaginaryVal");

        re_val.setText(String.valueOf(current_xpos + event.getSceneX() / current_zoom));
        im_val.setText(String.valueOf(current_ypos + event.getSceneY() / current_zoom));
    }

    public void updateVals() {
        Label re_val = (Label) canvas.getParent().getChildrenUnmodifiable().get(1).lookup("#realVal");
        Label im_val = (Label) canvas.getParent().getChildrenUnmodifiable().get(1).lookup("#imaginaryVal");

        re_val.setText(String.valueOf(current_xpos + Constants.VIEW_WIDTH / current_zoom));
        im_val.setText(String.valueOf(current_ypos + Constants.VIEW_HEIGHT / current_zoom));
    }
}
