package br.unicentro.trabalho1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;


public class ControllerPrincipal {

    @FXML
    private Button btnPaciente;

    @FXML
    private Button btnPsicologo;

    @FXML
    void abrirTelaPaciente(ActionEvent event) {
        try {
            // Carrega o FXML da outra tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/trabalho1/view/tela-Pacientes.fxml"));
            Parent root = loader.load();

            // Pega o Stage atual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Define a nova cena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaPsicologo(ActionEvent event) {
        try {
            // Carrega o FXML da outra tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/trabalho1/view/tela-Psicologos.fxml"));

            Parent root = loader.load();

            // Pega o Stage atual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Define a nova cena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}