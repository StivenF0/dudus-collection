package com.ufersa.duduscollection.view.livro;

import com.ufersa.duduscollection.model.dao.LivroDAO;
import com.ufersa.duduscollection.model.dao.impl.LivroDAOImpl;
import com.ufersa.duduscollection.model.entities.Livro;
import com.ufersa.duduscollection.model.services.LivroService;
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

    private final LivroService livroService;

    public AdicionarLivroController() {
        EntityManager em = JPAUtil.getEntityManager();
        LivroDAO livroDAO = new LivroDAOImpl(em);
        this.livroService = new LivroService(livroDAO);
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
            Livro novoLivro = new Livro();
            novoLivro.setNome(nome);
            novoLivro.setGenero(genero);
            novoLivro.setQtdPaginas(Integer.parseInt(paginas));
            novoLivro.setQtdExemplares(Integer.parseInt(exemplares));
            novoLivro.setValorAluguel(new BigDecimal(valorAluguel));
            novoLivro.setDataLancamento(Date.valueOf(dataLancamento));

            livroService.adicionarLivro(novoLivro);

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