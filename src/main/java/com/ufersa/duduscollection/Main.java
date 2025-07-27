package com.ufersa.duduscollection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // View atual a ser utilizada
        // Login
        URL fxmlLocation = getClass().getResource("/com/ufersa/duduscollection/view/login/login-view.fxml");

        // Main
//        URL fxmlLocation = getClass().getResource("/com/ufersa/duduscollection/view/main/MainLayout.fxml");


        if (fxmlLocation == null) {
            System.out.println("Não foi possível encontrar o arquivo fxml");
            return;
        }

        // Carrega a estrutura do FXML.
        Parent root = FXMLLoader.load(fxmlLocation);

        // Define o título da janela, como visto no topo da sua imagem.
        stage.setTitle("Dudu's Collection");

        // Cria a cena com o conteúdo carregado e define um tamanho inicial.
        Scene scene = new Scene(root, 1024, 600);

        // Define a cena no palco principal e exibe.
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
