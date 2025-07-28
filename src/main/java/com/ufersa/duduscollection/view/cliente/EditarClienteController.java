package com.ufersa.duduscollection.view.cliente;

import com.ufersa.duduscollection.model.entities.Cliente; // IMPORTE SUA CLASSE Cliente
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarClienteController {

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField enderecoField;
    @FXML private Button salvarButton;

    private Cliente clienteParaEditar;

    public void initData(Cliente cliente) {
        this.clienteParaEditar = cliente;

        nomeField.setText(cliente.getNome());
        cpfField.setText(cliente.getCpf());
        enderecoField.setText(cliente.getEndereco());
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        // Lógica para pegar os dados e atualizar o objeto clienteParaEditar
        System.out.println("Salvando alterações para o cliente: " + clienteParaEditar.getNome());
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