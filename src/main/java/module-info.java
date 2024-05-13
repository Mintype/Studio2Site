module com.mintype.studio2site {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;


    opens com.mintype.studio2site to javafx.fxml;
    exports com.mintype.studio2site;
}