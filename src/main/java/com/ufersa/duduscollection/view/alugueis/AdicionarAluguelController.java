package com.ufersa.duduscollection.view.alugueis;

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

public class AdicionarAluguelController {

    @FXML private ComboBox<Cliente> clienteComboBox;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextField valorTotalField;
    @FXML private Button salvarButton;

    @FXML
    public void initialize() {
        // Este método é chamado para carregar a lista de clientes no ComboBox
        // Você precisará de um ClienteService para buscar os clientes do banco.
        // ClienteService clienteService = new ClienteService();
        // List<Cliente> clientes = clienteService.buscarTodos();
        // clienteComboBox.setItems(FXCollections.observableArrayList(clientes));

        // DICA: Para o ComboBox exibir o nome do cliente, e não um objeto estranho,
        // certifique-se que sua classe Cliente tenha o método toString() implementado:
        // @Override public String toString() { return this.getNome(); }
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        // Lógica para pegar os dados, criar um objeto Aluguel e salvar no banco.
        Cliente clienteSelecionado = clienteComboBox.getSelectionModel().getSelectedItem();
        System.out.println("Salvando novo aluguel para o cliente: " + clienteSelecionado);
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