module logi.logi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens logi to javafx.fxml;
    exports logi;
    exports logi.controllers;
    opens logi.controllers to javafx.fxml;
    exports logi.models;
    opens logi.models to javafx.fxml;
}