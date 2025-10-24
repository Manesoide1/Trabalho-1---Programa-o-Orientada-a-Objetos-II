package br.unicentro.trabalho1.controller;

import br.unicentro.trabalho1.dao.PacienteDAO;
import br.unicentro.trabalho1.dao.PsicologoDAO;
import br.unicentro.trabalho1.dao.PsicologoDBDAO;
import br.unicentro.trabalho1.model.Paciente;
import br.unicentro.trabalho1.model.Psicologo;
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
import java.util.List;

public class ControllerPsicologo {

    @FXML
    private Button btnAtualizarPsicologo;

    @FXML
    private Button btnBuscarPsicologo;

    @FXML
    private Button btnExcluirPsicologo;

    @FXML
    private Button btnInserirPsicologo;

    @FXML
    private Button btnListarTodos;

    @FXML
    private Button btnVoltar;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPsicologo_ID;

    @FXML
    private Label lblSalario;

    @FXML
    private Label lblTempoAtuacao;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblRetornoBotoes;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPsicologo_ID;

    @FXML
    private TextField txtSalario;

    @FXML
    private TextField txtTempoAtuacao;

    @FXML
    public void initialize() {
        // Limitar apenas números inteiros
        txtPsicologo_ID.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            return (change.getControlNewText().matches("\\d*")) ? change : null;
        }));

        txtTempoAtuacao.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            return (change.getControlNewText().matches("\\d*")) ? change : null;
        }));

        // Limitar para números decimais (double)
        txtSalario.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            return (change.getControlNewText().matches("\\d*(\\.\\d{0,2})?")) ? change : null;
        }));
    }

    @FXML
    void atualizarPsicologo(ActionEvent event) {
        PsicologoDBDAO PsicologoDAO = new PsicologoDBDAO();
        Psicologo psicologo = new Psicologo();

        try {
            psicologo.setPsicologoID(Integer.parseInt(txtPsicologo_ID.getText()));
            psicologo.setNome(txtNome.getText());
            psicologo.setTempoAtuacao(Integer.parseInt(txtTempoAtuacao.getText()));
            psicologo.setSalario(Double.parseDouble(txtSalario.getText()));
            if(txtNome.getText().isEmpty()){
                throw new NumberFormatException();
            }
            PsicologoDAO.atualiza(psicologo);
            lblRetornoBotoes.setText("Psicologo atualizado com sucesso!");
        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha os valores corretamente.");
        } catch (SQLDataException e) {
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao atualizar psicólogo.");
        }
    }

    @FXML
    void buscarPsicologo(ActionEvent event) {
        PsicologoDBDAO PsicologoDAO = new PsicologoDBDAO();
        Psicologo psicologo = new Psicologo();
        try {
            psicologo.setPsicologoID(Integer.parseInt(txtPsicologo_ID.getText()));
            psicologo = PsicologoDAO.buscaPorCodigo(psicologo.getPsicologoID());
            lblRetornoBotoes.setText("O Psicólogo  " + psicologo.getPsicologoID() + " chama: " + psicologo.getNome());
        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha o ID corretamente.");
        } catch (SQLDataException e){
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao encontrar psicólogo.");
        }
    }

    @FXML
    void excluirPsicologo(ActionEvent event) {
        PsicologoDBDAO PsicologoDAO = new PsicologoDBDAO();
        Psicologo psicologo = new Psicologo();
        try {
            psicologo.setPsicologoID(Integer.parseInt(txtPsicologo_ID.getText()));
            PsicologoDAO.remove(psicologo);
            lblRetornoBotoes.setText("Psicólogo excluído com sucesso!");
        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha o ID corretamente.");
        } catch (SQLDataException e){
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao excluir psicólogo.");
        }
    }

    @FXML
    void inserirPsicologo(ActionEvent event) {
        PsicologoDBDAO PsicologoDAO = new PsicologoDBDAO();
        Psicologo psicologo = new Psicologo();

        try {
            psicologo.setPsicologoID(Integer.parseInt(txtPsicologo_ID.getText()));
            psicologo.setNome(txtNome.getText());
            psicologo.setTempoAtuacao(Integer.parseInt(txtTempoAtuacao.getText()));
            psicologo.setSalario(Double.parseDouble(txtSalario.getText()));
            if(txtNome.getText().isEmpty()){
                throw new NumberFormatException();
            }
            PsicologoDAO.insere(psicologo);
            lblRetornoBotoes.setText("Psicologo inserido com sucesso!");

        } catch (NumberFormatException nfe) {
            lblRetornoBotoes.setText("Por favor, preencha os valores corretamente.");
        } catch (SQLDataException e){
            lblRetornoBotoes.setText(e.getMessage());
        } catch (SQLException e) {
            lblRetornoBotoes.setText("Erro ao inserir psicologo.");
        }
    }

    @FXML
    void listaTodos(ActionEvent event) {
        PsicologoDBDAO psicologoDAO = new PsicologoDBDAO();

        try {
            List<Psicologo> lista = psicologoDAO.listaTodos();

            if (lista.isEmpty()) {
                mostrarPopup("Lista de Psicólogos", "Nenhum psicólogo encontrado.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Psicologo p : lista) {
                sb.append("ID: ").append(p.getPsicologoID())
                        .append("\nNome: ").append(p.getNome())
                        .append("\nTempo de atuação: ").append(p.getTempoAtuacao())
                        .append(" anos\nSalário: R$ ").append(String.format("%.2f", p.getSalario()))
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