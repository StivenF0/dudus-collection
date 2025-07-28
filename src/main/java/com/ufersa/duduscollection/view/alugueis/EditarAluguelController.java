package com.ufersa.duduscollection.view.alugueis;

import com.ufersa.duduscollection.model.entities.Aluguel; // IMPORTE SUA CLASSE Aluguel
import com.ufersa.duduscollection.model.entities.Cliente; // IMPORTE SUA CLASSE Cliente
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.List;

public class EditarAluguelController {

    @FXML private ComboBox<Cliente> clienteComboBox;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextField valorTotalField;
    @FXML private Button salvarButton;

    private Aluguel aluguelParaEditar;

    @FXML
    public void initialize() {
        // Carrega a lista de todos os clientes possíveis no ComboBox
        // ClienteService clienteService = new ClienteService();
        // List<Cliente> clientes = clienteService.buscarTodos();
        // clienteComboBox.setItems(FXCollections.observableArrayList(clientes));
    }

    public void initData(Aluguel aluguel) {
        this.aluguelParaEditar = aluguel;

        // Define os valores do aluguel nos campos do formulário
        clienteComboBox.setValue(aluguel.getCliente()); // Seleciona o cliente atual
        valorTotalField.setText(String.valueOf(aluguel.getValorTotal()));

        if (aluguel.getDataInicio() != null) {
            dataInicioPicker.setValue(new java.sql.Date(aluguel.getDataInicio().getTime()).toLocalDate());
        }
        if (aluguel.getDataFim() != null) {
            dataFimPicker.setValue(new java.sql.Date(aluguel.getDataFim().getTime()).toLocalDate());
        }
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        System.out.println("Salvando alterações para o aluguel do cliente: " + aluguelParaEditar.getCliente());
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