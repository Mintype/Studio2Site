package com.mintype.studio2site;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TranspilerApplicationController {

    private String directory;

    public TranspilerApplicationController(String directory) {
        this.directory = directory;
    }

    @FXML
    public void initialize() {
        System.out.println("starting main part of app! " + directory);
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
        System.out.println("Setting dir to: " + directory);
    }
    @FXML
    private void testt() {
        System.out.println("HEY: " + getDirectory());
    }

}