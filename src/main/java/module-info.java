module br.unicentro.trabalho1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens br.unicentro.trabalho1.view to javafx.fxml;
    opens br.unicentro.trabalho1.controller to javafx.fxml;

    exports br.unicentro.trabalho1.controller;
    exports br.unicentro.trabalho1.model;
    exports br.unicentro.trabalho1.dao;
}