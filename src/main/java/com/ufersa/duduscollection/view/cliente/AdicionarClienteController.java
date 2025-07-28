package com.ufersa.duduscollection.view.cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarClienteController {

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField enderecoField;
    @FXML private Button salvarButton;

    @FXML
    void handleSalvar(ActionEvent event) {
        // Lógica para pegar os dados, criar um objeto Cliente e salvar no banco.
        System.out.println("Salvando novo cliente: " + nomeField.getText());
        closeWindow();
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) salvarButton.getScene().getWindow()).close();
    }
}