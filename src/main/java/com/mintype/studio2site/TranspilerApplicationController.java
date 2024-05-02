package com.mintype.studio2site;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TranspilerApplicationController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}