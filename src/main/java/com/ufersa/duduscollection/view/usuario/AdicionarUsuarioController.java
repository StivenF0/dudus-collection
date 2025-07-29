package com.ufersa.duduscollection.view.usuario;

import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.dao.impl.UsuarioDAOImpl;
import com.ufersa.duduscollection.model.entities.Usuario;
import com.ufersa.duduscollection.model.services.UsuarioService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarUsuarioController {

    @FXML private TextField nomeField;
    @FXML private PasswordField senhaField;
    @FXML private PasswordField confirmarSenhaField;
    @FXML private Button salvarButton;

    private final UsuarioService usuarioService;

    public AdicionarUsuarioController() {
        EntityManager em = JPAUtil.getEntityManager();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
        this.usuarioService = new UsuarioService(usuarioDAO);
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        String nome = nomeField.getText();
        String senha = senhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();

        if (nome == null || nome.trim().isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Todos os campos são obrigatórios.");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "As senhas não coincidem.");
            return;
        }

        try {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setSenha(senha);

            usuarioService.adicionarUsuario(novoUsuario);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário salvo com sucesso!");
            closeWindow();

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro ao salvar o usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) salvarButton.getScene().getWindow()).close();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}