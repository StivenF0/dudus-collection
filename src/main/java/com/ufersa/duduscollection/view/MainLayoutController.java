package com.ufersa.duduscollection.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class MainLayoutController {
    @FXML
    private StackPane contentArea;

    private Button currentSelectedButton;

//    @FXML
//    public void initialize() {
//        // Inicializa livros por padrão
//        handleMenuLivros(null);
//    }

    @FXML
    void handleMenuLivros(ActionEvent event) {
        loadPage("livros-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuDiscos(ActionEvent event) {
        loadPage("discos-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuClientes(ActionEvent event) {
        loadPage("clientes-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuAlugueis(ActionEvent event) {
        loadPage("alugueis-view.fxml", (Button) ((Node)event.getSource()));
    }

    @FXML
    void handleMenuRelatorios(ActionEvent event) {
        loadPage("relatorios-view.fxml", (Button) ((Node)event.getSource()));
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
}
