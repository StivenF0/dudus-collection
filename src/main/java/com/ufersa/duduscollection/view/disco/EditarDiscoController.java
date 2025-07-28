package com.ufersa.duduscollection.view.disco;

import com.ufersa.duduscollection.model.entities.Disco; // IMPORTE SUA CLASSE Disco
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarDiscoController {

    @FXML private TextField nomeField;
    @FXML private DatePicker dataLancamentoPicker;
    @FXML private TextField estiloField;
    @FXML private TextField exemplaresField;
    @FXML private TextField valorAluguelField;
    @FXML private Button salvarButton;

    private Disco discoParaEditar;

    public void initData(Disco disco) {
        this.discoParaEditar = disco;

        nomeField.setText(disco.getNome());
        estiloField.setText(disco.getEstilo());
        exemplaresField.setText(String.valueOf(disco.getQtdExemplares()));
        valorAluguelField.setText(disco.getValorAluguel().toPlainString());

        if (disco.getDataLancamento() != null) {
            dataLancamentoPicker.setValue(new java.sql.Date(disco.getDataLancamento().getTime()).toLocalDate());
        }
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        // Lógica para pegar os dados e atualizar o objeto discoParaEditar
        System.out.println("Salvando alterações para o disco: " + discoParaEditar.getNome());
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