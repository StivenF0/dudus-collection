package com.ufersa.duduscollection.view.livro;

import com.ufersa.duduscollection.model.entities.Livro; // **IMPORTANTE: importe sua classe Livro**
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarLivroController {

    // ... (os mesmos @FXML do AdicionarLivroController) ...
    @FXML private TextField nomeField;
    @FXML private DatePicker dataLancamentoPicker;
    @FXML private TextField generoField;
    @FXML private TextField paginasField;
    @FXML private TextField exemplaresField;
    @FXML private TextField valorAluguelField;
    @FXML private Button salvarButton;

    // Variável para guardar o livro que está sendo editado
    private Livro livroParaEditar;

    /**
     * Este é o método crucial. Ele será chamado pelo LivrosController
     * para passar o objeto Livro e preencher o formulário.
     * @param livro O livro selecionado na tabela.
     */
    public void initData(Livro livro) {
        this.livroParaEditar = livro;

        // Preenche os campos do formulário com os dados do livro
//        nomeField.setText(livro.getNome());
//        dataLancamentoPicker.setValue(livro.getDataLancamento()); // Supondo que sua entidade tenha um método que retorne LocalDate
//        generoField.setText(livro.getGenero());
//        paginasField.setText(String.valueOf(livro.getQtdPaginas()));
//        exemplaresField.setText(String.valueOf(livro.getQtdExemplares()));
//        valorAluguelField.setText(String.valueOf(livro.getValorAluguel()));
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        // Lógica para pegar os dados dos campos e ATUALIZAR o objeto livroParaEditar
        // Depois, chamar o serviço para persistir a alteração no banco de dados.

        System.out.println("Salvando alterações para o livro: " + livroParaEditar.getNome());

        // Fecha a janela após salvar
        closeWindow();
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) salvarButton.getScene().getWindow();
        stage.close();
    }
}