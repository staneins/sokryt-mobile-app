module kaminsky.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.naming;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires spring.beans;

    exports service;
    opens service to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
}