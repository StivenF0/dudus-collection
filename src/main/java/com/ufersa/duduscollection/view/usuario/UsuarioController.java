package com.ufersa.duduscollection.view.usuario;

import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.dao.impl.UsuarioDAOImpl;
import com.ufersa.duduscollection.model.entities.Usuario;
import com.ufersa.duduscollection.model.services.UsuarioService;
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
import java.util.List;
import java.util.Optional;

public class UsuarioController {

    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, Long> colunaId;
    @FXML private TableColumn<Usuario, String> colunaNome;
    @FXML private TableColumn<Usuario, Void> colunaAcoes;
    @FXML private TextField pesquisarField;
    @FXML private ChoiceBox<String> filtroChoiceBox;

    private final UsuarioService usuarioService;
    private final ObservableList<Usuario> masterData = FXCollections.observableArrayList();

    public UsuarioController() {
        EntityManager em = JPAUtil.getEntityManager();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
        this.usuarioService = new UsuarioService(usuarioDAO);
    }

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        adicionarBotoesDeAcao();
        carregarUsuarios();
        configurarFiltroDeBusca();
    }

    private void carregarUsuarios() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        masterData.setAll(usuarios);
    }

    private void configurarFiltroDeBusca() {
        filtroChoiceBox.getItems().addAll("ID", "Nome");
        filtroChoiceBox.setValue("Nome");

        FilteredList<Usuario> filteredData = new FilteredList<>(masterData, p -> true);

        pesquisarField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(usuario -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (filtroChoiceBox.getValue().equals("ID")) {
                    return String.valueOf(usuario.getId()).contains(lowerCaseFilter);
                } else if (filtroChoiceBox.getValue().equals("Nome")) {
                    return usuario.getNome().toLowerCase().contains(lowerCaseFilter);
                }
                return false;
            });
        });

        tabelaUsuarios.setItems(filteredData);
    }

    private void adicionarBotoesDeAcao() {
        Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>> cellFactory = param -> new TableCell<>() {
            private final Button nomeBtn = new Button("Alterar Nome");
            private final Button senhaBtn = new Button("Alterar Senha");
            private final Button removerBtn = new Button("Remover");
            private final HBox pane = new HBox(10, nomeBtn, senhaBtn, removerBtn);

            {
                removerBtn.getStyleClass().add("remove-button");

                nomeBtn.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    abrirModalAlterarNome(usuario);
                });
                senhaBtn.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    abrirModalAlterarSenha(usuario);
                });
                removerBtn.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    removerUsuario(usuario);
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
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/usuarios/AdicionarUsuario.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Usuário");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarUsuarios();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirModalAlterarNome(Usuario usuario) {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/usuarios/AlterarNomeUsuario.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            AlterarNomeUsuarioController controller = loader.getController();
            controller.initData(usuario);

            Stage modalStage = new Stage();
            modalStage.setTitle("Alterar Nome de Usuário");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarUsuarios();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ... (dentro da classe UsuarioController)

    private void abrirModalAlterarSenha(Usuario usuario) {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/usuarios/AlterarSenhaUsuario.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            AlterarSenhaUsuarioController controller = loader.getController();
            controller.initData(usuario);

            Stage modalStage = new Stage();
            modalStage.setTitle("Alterar Senha de Usuário");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removerUsuario(Usuario usuario) {
        if (usuario == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Seleção Inválida", "Nenhum usuário selecionado.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja remover este usuário?");
        alert.setContentText(usuario.getNome());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                usuarioService.deletarUsuario(usuario);
                carregarUsuarios();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover", "Não foi possível remover o usuário.");
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