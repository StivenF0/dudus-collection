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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AlterarNomeUsuarioController {

    @FXML private Label nomeAtualLabel;
    @FXML private TextField novoNomeField;
    @FXML private Button salvarButton;

    private Usuario usuarioParaEditar;
    private final UsuarioService usuarioService;

    public AlterarNomeUsuarioController() {
        EntityManager em = JPAUtil.getEntityManager();
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
        this.usuarioService = new UsuarioService(usuarioDAO);
    }

    public void initData(Usuario usuario) {
        this.usuarioParaEditar = usuario;
        nomeAtualLabel.setText("Usuário: " + usuario.getNome());
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        String novoNome = novoNomeField.getText();

        if (novoNome == null || novoNome.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "O novo nome não pode ser vazio.");
            return;
        }

        if (novoNome.equals(usuarioParaEditar.getNome())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Nenhuma Alteração", "O novo nome é igual ao nome atual.");
            return;
        }

        try {
            usuarioParaEditar.setNome(novoNome);
            usuarioService.alterarUsuario(usuarioParaEditar);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Nome de usuário atualizado com sucesso!");
            closeWindow();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Atualizar", "Não foi possível atualizar o nome: " + e.getMessage());
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