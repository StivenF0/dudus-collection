package com.ufersa.duduscollection.view.livro;

import com.ufersa.duduscollection.model.dao.LivroDAO;
import com.ufersa.duduscollection.model.dao.impl.LivroDAOImpl;
import com.ufersa.duduscollection.model.entities.Livro;
import com.ufersa.duduscollection.model.services.LivroService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class LivroController {

    @FXML private TableView<Livro> tabelaLivros;
    @FXML private TableColumn<Livro, Long> colunaId;
    @FXML private TableColumn<Livro, String> colunaNome;
    @FXML private TableColumn<Livro, Date> colunaLancamento;
    @FXML private TableColumn<Livro, String> colunaGenero;
    @FXML private TableColumn<Livro, Integer> colunaPaginas;
    @FXML private TableColumn<Livro, Integer> colunaExemplares;
    @FXML private TableColumn<Livro, BigDecimal> colunaAluguel;
    @FXML private TableColumn<Livro, Void> colunaAcoes;
    @FXML private TextField pesquisarField;
    @FXML private ChoiceBox<String> filtroChoiceBox;

    private final LivroService livroService;
    private final ObservableList<Livro> masterData = FXCollections.observableArrayList();

    public LivroController() {
        EntityManager em = JPAUtil.getEntityManager();
        LivroDAO livroDAO = new LivroDAOImpl(em);
        this.livroService = new LivroService(livroDAO);
    }

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaLancamento.setCellValueFactory(new PropertyValueFactory<>("dataLancamento"));
        colunaGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colunaPaginas.setCellValueFactory(new PropertyValueFactory<>("qtdPaginas"));
        colunaExemplares.setCellValueFactory(new PropertyValueFactory<>("qtdExemplares"));
        colunaAluguel.setCellValueFactory(new PropertyValueFactory<>("valorAluguel"));

        adicionarBotoesDeAcao();
        carregarLivros();
        configurarFiltroDeBusca();
    }

    private void carregarLivros() {
        List<Livro> livros = livroService.buscarTodos();
        masterData.setAll(livros);
    }

    private void configurarFiltroDeBusca() {
        filtroChoiceBox.getItems().addAll("ID", "Nome", "Gênero");
        filtroChoiceBox.setValue("Nome");

        FilteredList<Livro> filteredData = new FilteredList<>(masterData, p -> true);

        pesquisarField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(livro -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                String filtroSelecionado = filtroChoiceBox.getValue();

                switch (filtroSelecionado) {
                    case "ID": return String.valueOf(livro.getId()).equals(lowerCaseFilter);
                    case "Nome": return livro.getNome().toLowerCase().contains(lowerCaseFilter);
                    case "Gênero": return livro.getGenero().toLowerCase().contains(lowerCaseFilter);
                    default: return false;
                }
            });
        });

        filtroChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) ->
                pesquisarField.setText(pesquisarField.getText()));

        tabelaLivros.setItems(filteredData);
    }

    private void adicionarBotoesDeAcao() {
        Callback<TableColumn<Livro, Void>, TableCell<Livro, Void>> cellFactory = param -> new TableCell<>() {
            private final Button editBtn = new Button("Editar");
            private final Button removeBtn = new Button("Remover");
            private final HBox pane = new HBox(10, editBtn, removeBtn);

            {
                editBtn.getStyleClass().add("edit-button");
                removeBtn.getStyleClass().add("remove-button");

                editBtn.setOnAction(event -> {
                    Livro livro = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(livro);
                });
                removeBtn.setOnAction(event -> {
                    Livro livro = getTableView().getItems().get(getIndex());
                    removerLivro(livro);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
        colunaAcoes.setCellFactory(cellFactory);
    }

    @FXML
    void handleAdicionarButton(ActionEvent event) {
        abrirModalAdicao();
    }

    @FXML
    void handleEditarButton(ActionEvent event) {
        Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
        abrirModalEdicao(livroSelecionado);
    }

    @FXML
    void handleRemoverButton(ActionEvent event) {
        Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
        removerLivro(livroSelecionado);
    }

    private void abrirModalAdicao() {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/livros/AdicionarLivro.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Livro");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarLivros();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirModalEdicao(Livro livro) {
        if (livro == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um livro para editar.");
            return;
        }
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/livros/EditarLivro.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            EditarLivroController controller = loader.getController();
            controller.initData(livro);

            Stage modalStage = new Stage();
            modalStage.setTitle("Editar Livro");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarLivros();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removerLivro(Livro livro) {
        if (livro == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um livro para remover.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja remover o livro?");
        alert.setContentText(livro.getNome());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                livroService.deletarLivro(livro);
                carregarLivros();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover", "Não foi possível remover o livro.");
                e.printStackTrace();
            }
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