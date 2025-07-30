package com.ufersa.duduscollection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carregamento de fontes
        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/NotoSans-Regular.ttf"), 10);
            Font.loadFont(getClass().getResourceAsStream("/fonts/NotoSans-Bold.ttf"), 10);
            Font.loadFont(getClass().getResourceAsStream("/fonts/NotoSans-Black.ttf"), 10);
            System.out.println("Fonte Noto Sans carregada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte Noto Sans.");
            e.printStackTrace();
        }

        // Login
        URL fxmlLocation = getClass().getResource("/com/ufersa/duduscollection/view/login/login-view.fxml");
        
        if (fxmlLocation == null) {
            System.out.println("Não foi possível encontrar o arquivo fxml");
            return;
        }

        // Carrega a estrutura do FXML.
        Parent root = FXMLLoader.load(fxmlLocation);

        stage.setTitle("Dudu's Collection");
        stage.centerOnScreen();

        Scene scene = new Scene(root, 1024, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
