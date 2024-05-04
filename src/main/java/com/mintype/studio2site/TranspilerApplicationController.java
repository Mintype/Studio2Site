package com.mintype.studio2site;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.File;

public class TranspilerApplicationController {

    private File dirFolder;
    @FXML
    private Text appTitle;

    public TranspilerApplicationController(String directory) {
        this.dirFolder = new File(directory);
    }

    @FXML
    public void initialize() {
        System.out.println("Starting Main Application...");

        // Set title to application folder title
        appTitle.setText(getDirFolder().getName());
    }

    public File getDirFolder() {
        return dirFolder;
    }

    @FXML
    private void testt() {
        System.out.println("HEY: " + getDirFolder());
    }
}