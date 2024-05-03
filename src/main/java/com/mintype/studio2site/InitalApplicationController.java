package com.mintype.studio2site;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class InitalApplicationController {

    @FXML
    private Button selectFolderButton;

    @FXML
    private void handleSelectFolder() {
        System.out.println("Open Folder Button Clicked!");

        // Create directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        // Show folder selection dialog
        Stage stage = (Stage) selectFolderButton.getScene().getWindow();
        String selectedDirectory = directoryChooser.showDialog(stage).getAbsolutePath();
        System.out.println("Selected folder: " + selectedDirectory);

        // Load and show the second window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Stage secondStage = new Stage();
            secondStage.setScene(new Scene(root));
            secondStage.setTitle("Second Window");
            secondStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}