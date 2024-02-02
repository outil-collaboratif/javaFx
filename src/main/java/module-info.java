module com.example.gestionsalledesport {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires junit;

    opens com.example.gestionsalledesport to javafx.fxml;
    exports com.example.gestionsalledesport;
    exports com.example.gestionsalledesport.controllers;
    opens com.example.gestionsalledesport.controllers to javafx.fxml;
    opens com.example.gestionsalledesport.models;
    exports com.example.gestionsalledesport.services to junit;


}