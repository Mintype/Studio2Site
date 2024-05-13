package com.mintype.studio2site;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    // todo: remove this later
    @FXML
    private void testt() {
        System.out.println("HEY: " + getDirFolder());
    }

    @FXML
    private void createWebApp() {
        // Create the html directory and index.html file
        File directory = new File("html");
        boolean directoryCreated = directory.mkdirs(); // mkdirs() creates parent directories if they don't exist
        if (directoryCreated) {
            System.out.println("html directory created successfully.");
        } else {
            System.out.println("Failed to create html directory or it already exists.");
        }

        // Create the index.html file
        File htmlFile = new File("html/index.html");
        try {
            boolean fileCreated = htmlFile.createNewFile();
            if (fileCreated) {
                System.out.println("index.html file created successfully.");
            } else {
                System.out.println("Failed to create index.html file or it already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // todo: add some util thing to help below.
        // Write text to index.html file
        try {
            FileWriter myWriter = new FileWriter(htmlFile.getAbsolutePath());
            myWriter.write("<p>hello world</p>");
            myWriter.close();

            HTTPServer httpServer = new HTTPServer(8000, htmlFile.getAbsolutePath());
            httpServer.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}