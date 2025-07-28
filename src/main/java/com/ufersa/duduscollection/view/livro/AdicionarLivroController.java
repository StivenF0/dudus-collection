package com.ufersa.duduscollection.view.livro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarLivroController {
    @FXML private TextField nomeField;
    @FXML private DatePicker dataLancamentoPicker;
    @FXML private TextField generoField;
    @FXML private TextField paginasField;
    @FXML private TextField exemplaresField;
    @FXML private TextField valorAluguelField;
    @FXML private Button salvarButton;
    @FXML private Button cancelarButton;

    @FXML
    void handleSalvar(ActionEvent event) {
        // Lógica para pegar os dados dos campos, criar um objeto Livro e salvar no banco.
        // Por enquanto, vamos apenas imprimir no console e fechar a janela.
        System.out.println("Nome: " + nomeField.getText());
        System.out.println("Data: " + dataLancamentoPicker.getValue());

        // Fecha a janela após salvar
        closeWindow();
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        // Apenas fecha a janela
        closeWindow();
    }

    private void closeWindow() {
        // Pega a referência do Stage (a janela) a partir de qualquer componente, como o botão
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        stage.close();
    }
}
