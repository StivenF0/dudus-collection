package com.ufersa.duduscollection.view.login;

import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.dao.impl.UsuarioDAOImpl;
import com.ufersa.duduscollection.model.entities.Usuario;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.ufersa.duduscollection.model.services.LoginService;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    // Injeção dos componentes do FXML. Os nomes devem ser os mesmos dos fx:id.
    @FXML
    private TextField usuarioField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button entrarButton;

    /**
     * Este método é chamado quando o botão "Entrar" é clicado (definido no onAction do FXML).
     * @param event O evento do clique do botão.
     */
    @FXML
    void handleLoginButton(ActionEvent event) {
        String usuario = usuarioField.getText();
        String senha = senhaField.getText();

        if (autenticar(usuario, senha)) {
            System.out.println("Login bem-sucedido!");
            navegarParaTelaPrincipal();
        } else {
            mostrarAlerta("Erro de Login", "Usuário ou senha inválidos.");
            senhaField.clear();
        }
    }

    /**
     * Método de placeholder para simular a autenticação.
     * @param usuario O nome de usuário.
     * @param senha A senha.
     * @return true se a autenticação for bem-sucedida, false caso contrário.
     */
    private boolean autenticar(String usuario, String senha) {
//        return usuario.equals("admin") && senha.equals("123");

        EntityManager em = null;

        try {
            em = JPAUtil.getEntityManager();
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
            LoginService loginService = new LoginService(usuarioDAO);

            Optional<Usuario> usuarioAutenticado = loginService.autenticar(usuario, senha);
            if (usuarioAutenticado.isPresent()) {
                System.out.println("O usuário \"" + usuario + "\" foi autenticado!");
                return true;
            }

            return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Navega da tela de login para a tela principal da aplicação.
     */
    private void navegarParaTelaPrincipal() {
        try {
            // Carrega o FXML da tela principal
            String fxmlPath = "/com/ufersa/duduscollection/view/main/MainLayout.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();

            // Pega a janela atual (a janela de login)
            Stage stage = (Stage) entrarButton.getScene().getWindow();

            // Cria a nova cena com a tela principal
            Scene scene = new Scene(root);

            // Define a nova cena na janela atual, efetivamente trocando de tela
            stage.setScene(scene);
            stage.setTitle("Dudu's Collection - Principal"); // Atualiza o título da janela
            stage.centerOnScreen(); // Centraliza a janela

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível carregar a tela principal.");
        }
    }

    /**
     * Exibe um pop-up de alerta simples.
     * @param titulo O título do alerta.
     * @param mensagem A mensagem a ser exibida.
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
