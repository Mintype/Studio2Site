package com.mintype.studio2site;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InitalApplicationController {

    @FXML
    private Button selectFolderButton;

    @FXML
    private void handleSelectFolder() {
        System.out.println("Open Folder Button Clicked!");
    }

}