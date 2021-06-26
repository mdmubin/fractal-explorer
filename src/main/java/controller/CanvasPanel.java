package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import main.java.model.Mandelbrot;
import main.java.utils.Constants;


public class CanvasPanel {
    @FXML
    public Canvas canvas;

    private double current_xpos = Constants.DEFAULT_XPOS;
    private double current_ypos = Constants.DEFAULT_YPOS;
    private double current_zoom = Constants.DEFAULT_ZOOM;

    @FXML
    public void initialize() {
        long start = System.nanoTime();
        drawFractal();
        System.out.println("Fractal Computations took: " + (System.nanoTime() - start) / 1e9 + "s");
    }

    public void drawFractal() {
        PixelWriter drawer = canvas.getGraphicsContext2D().getPixelWriter();

        for (int i = 0; i < Constants.VIEW_WIDTH; i++) {
            for (int j = 0; j < Constants.VIEW_HEIGHT; j++) {
                int mandelbrot_iters = Mandelbrot.computeIterations(
                        i / current_zoom + current_xpos,
                        current_ypos - j / current_zoom
                );
                drawer.setColor(i, j, getColor(mandelbrot_iters));
            }
        }
    }

    public Color getColor(int iters) {
        if (iters == Constants.MAX_ITER)
            return Color.BLACK;
        double smooth_val = iters + 1 - Math.log(Math.log(Mandelbrot.z_n.modulus())) / Math.log(2);
        return Color.hsb(smooth_val, .5, 1.0);
    }
}
