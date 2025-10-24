package br.unicentro.trabalho1.controller;

import br.unicentro.trabalho1.dao.PacienteDBDAO;
import br.unicentro.trabalho1.model.Paciente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerPaciente {

    @FXML
    private Button btnAtualizarPaciente;

    @FXML
    private Button btnBuscarPaciente;

    @FXML
    private Button btnExcluirPaciente;

    @FXML
    private Button btnInserirPaciente;

    @FXML
    private Button btnListarTodos;

    @FXML
    private Button btnVoltar;

    @FXML
    private Label lblCPF;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPaciente_ID;

    @FXML
    private Label lblpaciente_ID;

    @FXML
    private Label lblRetornoBotoes;

    @FXML
    private Label lblTitulo;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPaciente_ID;

    @FXML
    private TextField txtPsicologo_ID;

    @FXML
    public void initialize() {
        // Limitar apenas números inteiros
        txtPaciente_ID.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            return (change.getControlNewText().matches("\\d*")) ? change : null;
        }));

        txtCPF.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            return (change.getControlNewText().matches("\\d{0,11}")) ? change : null;
        }));

        // Limitar para números decimais (double)
        txtPaciente_ID.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            return (change.getControlNewText().matches("\\d*")) ? change : null;
        }));
    }

    @FXML
    void atualizarPaciente(ActionEvent event) {
        PacienteDBDAO pacienteDAO = new PacienteDBDAO();
        Paciente paciente = new Paciente();

        try {
            paciente.setPacienteID(Integer.parseInt(txtPaciente_ID.getText()));
            paciente.setNome(txtNome.getText());
            paciente.setCpf(txtCPF.getText());
            paciente.setPsicologo_ID(Integer.parseInt(txtPsicologo_ID.getText()));
            if(txtNome.getText().isEmpty() || txtCPF.getText().length() != 11){
                throw new NumberFormatException();
            }
            pacienteDAO.atualiza(paciente);
            lblRetornoBotoes.setText("Paciente atualizado com sucesso!");
        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha os valores corretamente.");
        } catch (SQLDataException e) {
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao atualizar paciente.");
        }
    }

    @FXML
    void buscarPaciente(ActionEvent event) {
        PacienteDBDAO pacienteDAO = new PacienteDBDAO();
        Paciente paciente = new Paciente();
        try {
            paciente.setPacienteID(Integer.parseInt(txtPaciente_ID.getText()));
            paciente = pacienteDAO.buscaPorCodigo(paciente.getPacienteID());
            lblRetornoBotoes.setText("O paciente com ID " + paciente.getPacienteID() + " chama: " + paciente.getNome());
        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha o ID corretamente.");
        } catch (SQLDataException e){
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao encontrar paciente.");
        }
    }

    @FXML
    void excluirPaciente(ActionEvent event) {
        PacienteDBDAO pacienteDAO = new PacienteDBDAO();
        Paciente paciente = new Paciente();
        try {
            paciente.setPacienteID(Integer.parseInt(txtPaciente_ID.getText()));
            pacienteDAO.remove(paciente);
            lblRetornoBotoes.setText("Paciente excluído com sucesso!");
        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha o ID corretamente.");
        } catch (SQLDataException e){
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao excluir paciente.");
        }
    }

    @FXML
    void inserirPaciente(ActionEvent event) {
        PacienteDBDAO pacienteDAO = new PacienteDBDAO();
        Paciente paciente = new Paciente();

        try {
            paciente.setPacienteID(Integer.parseInt(txtPaciente_ID.getText()));
            paciente.setNome(txtNome.getText());
            paciente.setCpf(txtCPF.getText());
            paciente.setPsicologo_ID(Integer.parseInt(txtPsicologo_ID.getText()));
            if(txtNome.getText().isEmpty() || txtCPF.getText().length() != 11){
                throw new NumberFormatException();
            }
            pacienteDAO.insere(paciente);
            lblRetornoBotoes.setText("Paciente inserido com sucesso!");

        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha os valores corretamente.");
        } catch (SQLDataException e){
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao inserir Paciente.");
        }
    }

    @FXML
    void listaTodos(ActionEvent event) {
        PacienteDBDAO pacienteDAO = new PacienteDBDAO();

        try {
            List<Paciente> lista = pacienteDAO.listaTodos();

            if (lista.isEmpty()) {
                mostrarPopup("Lista de Psicólogos", "Nenhum psicólogo encontrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Paciente p : lista) {
                sb.append("ID: ").append(p.getPacienteID())
                        .append("\nNome: ").append(p.getNome())
                        .append("\nCPF: ").append(p.getCpf())
                        .append("\nID do Psicólogo: ").append(p.getPsicologo_ID())
                        .append("\n----------------------\n");
            }

            mostrarPopup("Lista de Psicólogos", sb.toString());
        } catch (SQLException e) {
            mostrarPopup("Erro", "Erro ao listar psicólogos: " + e.getMessage());
        }
    }

    private void mostrarPopup(String titulo, String mensagem) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    void voltarTelaPrincipal(ActionEvent event) {
        try {
            // Carrega o FXML da outra tela
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/trabalho1/view/tela-Principal.fxml"));

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