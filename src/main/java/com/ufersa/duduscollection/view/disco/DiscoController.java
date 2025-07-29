package com.ufersa.duduscollection.view.disco;

import com.ufersa.duduscollection.model.dao.DiscoDAO;
import com.ufersa.duduscollection.model.dao.impl.DiscoDAOImpl;
import com.ufersa.duduscollection.model.entities.Disco;
import com.ufersa.duduscollection.model.services.DiscoService;
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

public class DiscoController {

    @FXML private TableView<Disco> tabelaDiscos;
    @FXML private TableColumn<Disco, Long> colunaId;
    @FXML private TableColumn<Disco, String> colunaNome;
    @FXML private TableColumn<Disco, String> colunaEstilo;
    @FXML private TableColumn<Disco, Date> colunaLancamento;
    @FXML private TableColumn<Disco, Integer> colunaExemplares;
    @FXML private TableColumn<Disco, BigDecimal> colunaAluguel;
    @FXML private TableColumn<Disco, Void> colunaAcoes;
    @FXML private TextField pesquisarField;
    @FXML private ChoiceBox<String> filtroChoiceBox;

    private final DiscoService discoService;
    private final ObservableList<Disco> masterData = FXCollections.observableArrayList();

    public DiscoController() {
        EntityManager em = JPAUtil.getEntityManager();
        DiscoDAO discoDAO = new DiscoDAOImpl(em);
        this.discoService = new DiscoService(discoDAO);
    }

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEstilo.setCellValueFactory(new PropertyValueFactory<>("estilo"));
        colunaLancamento.setCellValueFactory(new PropertyValueFactory<>("dataLancamento"));
        colunaExemplares.setCellValueFactory(new PropertyValueFactory<>("qtdExemplares"));
        colunaAluguel.setCellValueFactory(new PropertyValueFactory<>("valorAluguel"));

        adicionarBotoesDeAcao();
        carregarDiscos();
        configurarFiltroDeBusca();
    }

    private void carregarDiscos() {
        List<Disco> discos = discoService.buscarTodos();
        masterData.setAll(discos);
    }

    private void configurarFiltroDeBusca() {
        filtroChoiceBox.getItems().addAll("ID", "Nome", "Estilo");
        filtroChoiceBox.setValue("Nome");

        FilteredList<Disco> filteredData = new FilteredList<>(masterData, p -> true);

        pesquisarField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(disco -> {
                if (newValue == null || newValue.isEmpty()) return true;

                String lowerCaseFilter = newValue.toLowerCase();
                String filtroSelecionado = filtroChoiceBox.getValue();

                switch (filtroSelecionado) {
                    case "ID": return String.valueOf(disco.getId()).equals(lowerCaseFilter);
                    case "Nome": return disco.getNome().toLowerCase().contains(lowerCaseFilter);
                    case "Estilo": return disco.getEstilo().toLowerCase().contains(lowerCaseFilter);
                    default: return false;
                }
            });
        });

        filtroChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) ->
                pesquisarField.setText(pesquisarField.getText()));

        tabelaDiscos.setItems(filteredData);
    }

    private void adicionarBotoesDeAcao() {
        Callback<TableColumn<Disco, Void>, TableCell<Disco, Void>> cellFactory = param -> new TableCell<>() {
            private final Button editBtn = new Button("Editar");
            private final Button removeBtn = new Button("Remover");
            private final HBox pane = new HBox(10, editBtn, removeBtn);

            {
                editBtn.getStyleClass().add("edit-button");
                removeBtn.getStyleClass().add("remove-button");

                editBtn.setOnAction(event -> {
                    Disco disco = getTableView().getItems().get(getIndex());
                    abrirModalEdicao(disco);
                });
                removeBtn.setOnAction(event -> {
                    Disco disco = getTableView().getItems().get(getIndex());
                    removerDisco(disco);
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
        Disco discoSelecionado = tabelaDiscos.getSelectionModel().getSelectedItem();
        abrirModalEdicao(discoSelecionado);
    }

    @FXML
    void handleRemoverButton(ActionEvent event) {
        Disco discoSelecionado = tabelaDiscos.getSelectionModel().getSelectedItem();
        removerDisco(discoSelecionado);
    }

    private void abrirModalAdicao() {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/discos/AdicionarDisco.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Disco");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarDiscos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirModalEdicao(Disco disco) {
        if (disco == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um disco para editar.");
            return;
        }
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/discos/EditarDisco.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            EditarDiscoController controller = loader.getController();
            controller.initData(disco);

            Stage modalStage = new Stage();
            modalStage.setTitle("Editar Disco");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarDiscos();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removerDisco(Disco disco) {
        if (disco == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Por favor, selecione um disco para remover.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja remover o disco?");
        alert.setContentText(disco.getNome());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                discoService.deletarDisco(disco);
                carregarDiscos();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover", "Não foi possível remover o disco.");
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