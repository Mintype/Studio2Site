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

        // Check if html directory already exists and if true then delete it.
        File folder = new File("html");
        if (folder.exists()) {
            // Delete the folder
            deleteFolder(folder);
            System.out.println("Folder deleted successfully.");
        } else {
            System.out.println("Folder does not exist.");
        }
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

        // Create the main.js file
        File jsFile = new File("html/main.js");
        try {
            boolean fileCreated = jsFile.createNewFile();
            if (fileCreated) {
                System.out.println("main.js file created successfully.");
            } else {
                System.out.println("Failed to create main.js file or it already exists.");
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
            boolean isVertical = false;
            int counter = 0;
            String layout = "";
            try (BufferedReader br = new BufferedReader(new FileReader(dirFolder + "/app/src/main/res/layout/activity_main.xml"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line); // prints out each line

                    if(counter == 1) {
                        layout = line.trim().substring(1, line.trim().indexOf(" "));
                        System.out.println("LAYOUT:" + layout);
                    } else if (counter == 6) {
                        if (line.trim().contains("vertical")) {
                            isVertical = true;
                        }
                    }
                    counter++;

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
            myWriter.write("<style>\n");
            myWriter.write("body {\n" +
                    "    padding: 0;\n" +
                    "    margin: 0;\n");

            // use flex if its linaer layout
            if(layout.equalsIgnoreCase("LinearLayout"))
                myWriter.write("    display: flex;\n");


            if(isVertical)
                myWriter.write("    flex-direction: column;\n");
            else
                myWriter.write("    flex-direction: row;\n");


            myWriter.write("    background-color: rgb(240, 240, 240);\n" +
                    "    font-family: Arial, Helvetica, sans-serif;\n" +
                    "}\n" +
                    "button {\n" +
                    "    background-color: #6750A4;\n" +
                    "    color: white;\n" +
                    "    border: none;\n" +
                    "    border-radius: 50px;\n" +
                    "    padding: 15px;\n" +
                    "    width: fit-content;\n" +
                    "}\n");
            myWriter.write("</style>\n");
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

    private void deleteFolder(File folder) {
        File[] contents = folder.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }
}