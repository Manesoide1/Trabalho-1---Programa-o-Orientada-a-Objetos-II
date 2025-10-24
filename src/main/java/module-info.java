module br.unicentro.trabalho1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.graphics;


    opens br.unicentro.trabalho1.view to javafx.fxml;
    exports br.unicentro.trabalho1.controller;
    //exports br.unicentro.trabalho1.controller;
    opens br.unicentro.trabalho1.controller to javafx.fxml;
}