package com.ufersa.duduscollection.view.disco;

import com.ufersa.duduscollection.model.dao.DiscoDAO;
import com.ufersa.duduscollection.model.dao.impl.DiscoDAOImpl;
import com.ufersa.duduscollection.model.entities.Disco;
import com.ufersa.duduscollection.model.services.DiscoService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class EditarDiscoController {

    @FXML private TextField nomeField;
    @FXML private DatePicker dataLancamentoPicker;
    @FXML private TextField estiloField;
    @FXML private TextField exemplaresField;
    @FXML private TextField valorAluguelField;
    @FXML private Button salvarButton;

    private Disco discoParaEditar;
    private final DiscoService discoService;

    public EditarDiscoController() {
        EntityManager em = JPAUtil.getEntityManager();
        DiscoDAO discoDAO = new DiscoDAOImpl(em);
        this.discoService = new DiscoService(discoDAO);
    }

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
        String nome = nomeField.getText();
        LocalDate dataLancamento = dataLancamentoPicker.getValue();
        String estilo = estiloField.getText();
        String exemplares = exemplaresField.getText();
        String valorAluguel = valorAluguelField.getText();

        if (nome == null || nome.trim().isEmpty() || dataLancamento == null ||
                estilo == null || estilo.trim().isEmpty() || exemplares == null || exemplares.trim().isEmpty() ||
                valorAluguel == null || valorAluguel.trim().isEmpty()) {

            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Todos os campos são obrigatórios.");
            return;
        }

        try {
            discoParaEditar.setNome(nome);
            discoParaEditar.setEstilo(estilo);
            discoParaEditar.setQtdExemplares(Integer.parseInt(exemplares));
            discoParaEditar.setValorAluguel(new BigDecimal(valorAluguel));
            discoParaEditar.setDataLancamento(Date.valueOf(dataLancamento));

            discoService.alterarDisco(discoParaEditar);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Disco atualizado com sucesso!");
            closeWindow();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "Os campos 'Exemplares' e 'Valor' devem ser números válidos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Atualizar", "Ocorreu um erro ao atualizar o disco: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) salvarButton.getScene().getWindow()).close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}