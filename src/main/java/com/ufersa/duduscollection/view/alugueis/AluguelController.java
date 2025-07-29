package com.ufersa.duduscollection.view.alugueis;

import com.ufersa.duduscollection.model.dao.AluguelDAO;
import com.ufersa.duduscollection.model.dao.impl.AluguelDAOImpl;
import com.ufersa.duduscollection.model.entities.Aluguel;
import com.ufersa.duduscollection.model.entities.Produto;
import com.ufersa.duduscollection.model.services.AluguelService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AluguelController {

    @FXML private TableView<Aluguel> tabelaAlugueis;
    @FXML private TableColumn<Aluguel, Long> colunaId;
    @FXML private TableColumn<Aluguel, String> colunaCliente;
    @FXML private TableColumn<Aluguel, String> colunaItens;
    @FXML private TableColumn<Aluguel, Date> colunaDataInicio;
    @FXML private TableColumn<Aluguel, Date> colunaDataFim;
    @FXML private TableColumn<Aluguel, Double> colunaPreco;
    @FXML private TableColumn<Aluguel, Void> colunaAcoes;
    @FXML private TextField pesquisarField;
    @FXML private ChoiceBox<String> filtroChoiceBox;

    private final AluguelService aluguelService;
    private final ObservableList<Aluguel> masterData = FXCollections.observableArrayList();

    public AluguelController() {
        EntityManager em = JPAUtil.getEntityManager();
        AluguelDAO aluguelDAO = new AluguelDAOImpl(em);
        this.aluguelService = new AluguelService(aluguelDAO);
    }

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colunaDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        colunaCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente().getNome()));

        colunaItens.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProdutos().stream()
                        .map(Produto::getNome)
                        .collect(Collectors.joining(", "))));

        adicionarBotoesDeAcao();
        carregarAlugueis();
        configurarFiltroDeBusca();
    }

    private void carregarAlugueis() {
        List<Aluguel> alugueis = aluguelService.buscarTodos();
        masterData.setAll(alugueis);
    }

    private void configurarFiltroDeBusca() {
        filtroChoiceBox.getItems().addAll("ID", "Cliente");
        filtroChoiceBox.setValue("Cliente");

        FilteredList<Aluguel> filteredData = new FilteredList<>(masterData, p -> true);

        pesquisarField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(aluguel -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                String filtroSelecionado = filtroChoiceBox.getValue();

                switch (filtroSelecionado) {
                    case "ID": return String.valueOf(aluguel.getId()).equals(lowerCaseFilter);
                    case "Cliente": return aluguel.getCliente().getNome().toLowerCase().contains(lowerCaseFilter);
                    default: return false;
                }
            });
        });

        filtroChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) ->
                pesquisarField.setText(pesquisarField.getText()));

        tabelaAlugueis.setItems(filteredData);
    }

    private void adicionarBotoesDeAcao() {
        Callback<TableColumn<Aluguel, Void>, TableCell<Aluguel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button editBtn = new Button("Editar");
            private final Button removeBtn = new Button("Remover");
            private final HBox pane = new HBox(10, editBtn, removeBtn);

            {
                editBtn.getStyleClass().add("edit-button");
                removeBtn.getStyleClass().add("remove-button");

                editBtn.setOnAction(event -> {
                    Aluguel aluguel = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(aluguel);
                });
                removeBtn.setOnAction(event -> {
                    Aluguel aluguel = getTableView().getItems().get(getIndex());
                    removerAluguel(aluguel);
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
        Aluguel aluguelSelecionado = tabelaAlugueis.getSelectionModel().getSelectedItem();
        abrirModalEdicao(aluguelSelecionado);
    }

    @FXML
    void handleRemoverButton(ActionEvent event) {
        Aluguel aluguelSelecionado = tabelaAlugueis.getSelectionModel().getSelectedItem();
        removerAluguel(aluguelSelecionado);
    }

    private void abrirModalAdicao() {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/alugueis/AdicionarAluguel.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Aluguel");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarAlugueis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirModalEdicao(Aluguel aluguel) {
        if (aluguel == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um aluguel para editar.");
            return;
        }
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/alugueis/EditarAluguel.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            EditarAluguelController controller = loader.getController();
            controller.initData(aluguel);

            Stage modalStage = new Stage();
            modalStage.setTitle("Editar Aluguel");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarAlugueis();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removerAluguel(Aluguel aluguel) {
        if (aluguel == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um aluguel para remover.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja remover o aluguel ID: " + aluguel.getId() + "?");
        alert.setContentText("Cliente: " + aluguel.getCliente().getNome());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                aluguelService.deletarAluguel(aluguel);
                carregarAlugueis();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover", "Não foi possível remover o aluguel.");
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