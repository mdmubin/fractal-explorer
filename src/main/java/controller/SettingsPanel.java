package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.utils.Constants;

public class SettingsPanel {
    @FXML
    private TextField iterCount;

    public void setIterCount(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                Constants.CURRENT_MAX_ITER = Integer.parseInt(iterCount.getText());
            } catch (Exception parseError) {
                System.out.println("Invalid Zoom Level: " + iterCount.getText());
            }
        }
    }
}
