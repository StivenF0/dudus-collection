package com.ufersa.duduscollection.view.livro;

// Imports atualizados para usar as classes genéricas de Produto onde possível
import com.ufersa.duduscollection.model.dao.ProdutoDAO;
import com.ufersa.duduscollection.model.dao.impl.ProdutoDAOImpl;
import com.ufersa.duduscollection.model.entities.Livro;
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

public class AdicionarLivroController {

    @FXML private TextField nomeField;
    @FXML private DatePicker dataLancamentoPicker;
    @FXML private TextField generoField;
    @FXML private TextField paginasField;
    @FXML private TextField exemplaresField;
    @FXML private TextField valorAluguelField;
    @FXML private Button salvarButton;

    /**
     * Alterado para usar o ProdutoService genérico.
     * O controller não precisa mais conhecer o serviço específico de Livro,
     * promovendo um menor acoplamento.
     */
    private final ProdutoService produtoService;

    public AdicionarLivroController() {
        EntityManager em = JPAUtil.getEntityManager();
        // Instancia o DAO e o Serviço genéricos.
        ProdutoDAO produtoDAO = new ProdutoDAOImpl(em);
        this.produtoService = new ProdutoService(produtoDAO);
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        String nome = nomeField.getText();
        LocalDate dataLancamento = dataLancamentoPicker.getValue();
        String genero = generoField.getText();
        String paginas = paginasField.getText();
        String exemplares = exemplaresField.getText();
        String valorAluguel = valorAluguelField.getText();

        if (nome == null || nome.trim().isEmpty() || dataLancamento == null ||
                genero == null || genero.trim().isEmpty() || paginas == null || paginas.trim().isEmpty() ||
                exemplares == null || exemplares.trim().isEmpty() || valorAluguel == null || valorAluguel.trim().isEmpty()) {

            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Todos os campos são obrigatórios.");
            return;
        }

        try {
            // ** A MUDANÇA PRINCIPAL ACONTECE AQUI **
            // Em vez de criar um objeto vazio e usar setters,
            // usamos o Builder para uma criação fluente, legível e segura.

            Produto novoLivro = Livro.builder(nome, genero)
                    .qtdPaginas(Integer.parseInt(paginas))
                    .qtdExemplares(Integer.parseInt(exemplares))
                    .valorAluguel(new BigDecimal(valorAluguel))
                    .dataLancamento(Date.valueOf(dataLancamento))
                    .build(); // O objeto é construído de forma atômica aqui.

            // O objeto criado é então passado para o serviço genérico.
            produtoService.adicionarProduto(novoLivro);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Livro salvo com sucesso!");
            closeWindow();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "Os campos 'Páginas', 'Exemplares' e 'Valor' devem ser números válidos.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro ao salvar o livro: " + e.getMessage());
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