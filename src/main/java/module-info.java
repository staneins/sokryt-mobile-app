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
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    exports service;
    opens service to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;

    opens entity to org.hibernate.orm.core;
}