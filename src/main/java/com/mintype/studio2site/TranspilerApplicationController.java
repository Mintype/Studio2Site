package com.mintype.studio2site;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.*;

public class TranspilerApplicationController {

    private File dirFolder;
    private HTTPServer httpServer;
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
            myWriter.write("<!DOCTYPE html>\n" +
                                "<html lang=\"en\">\n" +
                                "<head>\n" +
                                "    <meta charset=\"UTF-8\">\n" +
                                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                                "    <title>Document</title>\n" +
                                "</head>\n");
            myWriter.write("<body>\n");

            // put stuff here now.
            boolean isTextView = false;
            boolean isButton = false;
            try (BufferedReader br = new BufferedReader(new FileReader(dirFolder + "/app/src/main/res/layout/activity_main.xml"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    //System.out.println(line); // prints out each line

                    // code for textview
                    if(line.trim().equals("<TextView")) {
                        isTextView = true;
                        System.out.println("isTextView"  + " " + isTextView);
                        myWriter.write("<p>");
                    } else if(line.trim().contains("android:text") && isTextView) {
                        if(line.trim().contains("/>")) {
                            String textExtracted = line.trim().substring(14, line.trim().length() - 4);
                            myWriter.write(textExtracted + "</p>\n");
                            isTextView = false;
                            System.out.println("isTextView"  + " " + isTextView);
                        } else {
                            String textExtracted = line.trim().substring(14, line.trim().length() - 1);
                            myWriter.write(textExtracted);
                        }
                    } else if(line.trim().equals("/>") && isTextView) {
                        isTextView = false;
                        System.out.println("isTextView"  + " " + isTextView);
                        myWriter.write("</p>\n");
                    } //text view code ends here

                    // code for Button
                    if(line.trim().equals("<Button")) {
                        isButton = true;
                        System.out.println("isButton"  + " " + isButton);
                        myWriter.write("<button>");
                    } else if(line.trim().contains("android:text") && isButton) {
                        if(line.trim().contains("/>")) {
                            String textExtracted = line.trim().substring(14, line.trim().length() - 4);
                            myWriter.write(textExtracted + "</button>\n");
                            isButton = false;
                            System.out.println("isButton"  + " " + isButton);
                        } else {
                            String textExtracted = line.trim().substring(14, line.trim().length() - 1);
                            myWriter.write(textExtracted);
                        }
                    } else if(line.trim().equals("/>") && isButton) {
                        isButton = false;
                        System.out.println("isButton"  + " " + isButton);
                        myWriter.write("</button>\n");
                    } //text view code ends here



                }
            } catch (IOException e) {
                System.out.println("Error when trying to find XML activity_main !!!");
                e.printStackTrace();
            }
            //myWriter.write("<p>hello wewfweforld</p>");



            myWriter.write("\n");
            myWriter.write("</body>\n");
            myWriter.write("</html>");
            myWriter.close();

            if(httpServer == null) {
                httpServer = new HTTPServer(8000, htmlFile.getAbsolutePath());
                httpServer.startServer();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}