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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class AlterarSenhaUsuarioController {

    @FXML private Label usuarioLabel;
    @FXML private PasswordField novaSenhaField;
    @FXML private PasswordField confirmarSenhaField;
    @FXML private Button salvarButton;

    private Usuario usuarioParaEditar;
    private final UsuarioService usuarioService;

    public AlterarSenhaUsuarioController() {
        EntityManager em = JPAUtil.getEntityManager();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
        this.usuarioService = new UsuarioService(usuarioDAO);
    }

    public void initData(Usuario usuario) {
        this.usuarioParaEditar = usuario;
        usuarioLabel.setText("Alterando senha para: " + usuario.getNome());
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        String novaSenha = novaSenhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();

        if (novaSenha.isEmpty() || confirmarSenha.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Os campos de senha não podem ser vazios.");
            return;
        }

        if (novaSenha.length() < 3) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "A senha deve ter no mínimo 3 caracteres.");
            return;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "As senhas não coincidem.");
            return;
        }

        try {
            usuarioParaEditar.setSenha(novaSenha);

            usuarioService.alterarUsuario(usuarioParaEditar);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Senha atualizada com sucesso!");
            closeWindow();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Atualizar", "Não foi possível atualizar a senha: " + e.getMessage());
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