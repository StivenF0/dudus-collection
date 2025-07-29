package com.ufersa.duduscollection.view;

import com.ufersa.duduscollection.model.entities.Livro;
import com.ufersa.duduscollection.view.livro.EditarLivroController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class MainLayoutController {
    @FXML
    private StackPane contentArea;

    @FXML
    private Button livrosButton;

    private Button currentSelectedButton;

    @FXML
    public void initialize() {
        loadPage("livros/livros-view.fxml", livrosButton);
    }

    @FXML
    void handleMenuLivros(ActionEvent event) {
        loadPage("livros/livros-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuDiscos(ActionEvent event) {
        loadPage("discos/discos-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuClientes(ActionEvent event) {
        loadPage("clientes/clientes-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuAlugueis(ActionEvent event) {
        loadPage("alugueis/alugueis-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuUsuarios(ActionEvent event) {
        loadPage("usuarios/usuarios-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuRelatorios(ActionEvent event) {
        loadPage("relatorios/relatorios-view.fxml", (Button) ((Node)event.getSource()));
    }

    private void loadPage(String fxmlFileName, Button clickedButton) {
        // Remove o estilo do botão anteriormente selecionado
        if (currentSelectedButton != null) {
            currentSelectedButton.getStyleClass().remove("selected");
        }

        // Adiciona o estilo ao novo botão clicado
        if (clickedButton != null) {
            clickedButton.getStyleClass().add("selected");
            currentSelectedButton = clickedButton;
        }

        try {
            String baseDir = "/com/ufersa/duduscollection/view/main/";
            URL fxmlUrl = getClass().getResource(baseDir + fxmlFileName);
            if (fxmlUrl == null) {
                System.err.println("Não foi possível encontrar o arquivo FXML: " + fxmlFileName);
                return;
            }
            Node page = FXMLLoader.load(fxmlUrl);
            contentArea.getChildren().setAll(page);
        } catch (IOException e) {
            System.err.println("Falha ao carregar a página: " + fxmlFileName);
            e.printStackTrace();
        }
    }

    @FXML
    void handleSair(ActionEvent ignoredEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Logout");
        alert.setHeaderText("Você está prestes a sair da sua sessão.");
        alert.setContentText("Deseja realmente voltar para a tela de login?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                String fxmlPath = "/com/ufersa/duduscollection/view/login/login-view.fxml";
                Parent loginRoot = FXMLLoader.load(getClass().getResource(fxmlPath));

                Stage stage = (Stage) contentArea.getScene().getWindow();
                Scene loginScene = new Scene(loginRoot, 1024, 600);

                stage.setScene(loginScene);
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
