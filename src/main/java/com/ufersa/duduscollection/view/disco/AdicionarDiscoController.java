package com.ufersa.duduscollection.view.disco;

// Imports atualizados
import com.ufersa.duduscollection.model.dao.ProdutoDAO;
import com.ufersa.duduscollection.model.dao.impl.ProdutoDAOImpl;
import com.ufersa.duduscollection.model.entities.Disco;
import com.ufersa.duduscollection.model.entities.Produto;
import com.ufersa.duduscollection.model.services.ProdutoService;
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

public class AdicionarDiscoController {

    @FXML private TextField nomeField;
    @FXML private DatePicker dataLancamentoPicker;
    @FXML private TextField estiloField;
    @FXML private TextField exemplaresField;
    @FXML private TextField valorAluguelField;
    @FXML private Button salvarButton;

    /**
     * Alterado para usar o ProdutoService genérico.
     */
    private final ProdutoService produtoService;

    public AdicionarDiscoController() {
        EntityManager em = JPAUtil.getEntityManager();
        // Instancia o DAO e o Serviço genéricos.
        ProdutoDAO produtoDAO = new ProdutoDAOImpl(em);
        this.produtoService = new ProdutoService(produtoDAO);
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
            // ** Utilizando o Builder para criar o objeto Disco **
            Produto novoDisco = Disco.builder(nome, estilo)
                    .qtdExemplares(Integer.parseInt(exemplares))
                    .valorAluguel(new BigDecimal(valorAluguel))
                    .dataLancamento(Date.valueOf(dataLancamento))
                    .build();

            // Usando o serviço genérico para salvar
            produtoService.adicionarProduto(novoDisco);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Disco salvo com sucesso!");
            closeWindow();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "Os campos 'Exemplares' e 'Valor' devem ser números válidos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro ao salvar o disco: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}