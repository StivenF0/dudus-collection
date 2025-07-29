package com.ufersa.duduscollection.view.cliente;

import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.dao.impl.ClienteDAOImpl;
import com.ufersa.duduscollection.model.entities.Cliente;
import com.ufersa.duduscollection.model.services.ClienteService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarClienteController {

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField enderecoField;
    @FXML private Button salvarButton;

    private Cliente clienteParaEditar;
    private final ClienteService clienteService;

    public EditarClienteController() {
        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAO clienteDAO = new ClienteDAOImpl(em);
        this.clienteService = new ClienteService(clienteDAO);
    }

    public void initData(Cliente cliente) {
        this.clienteParaEditar = cliente;
        nomeField.setText(cliente.getNome());
        cpfField.setText(cliente.getCpf());
        enderecoField.setText(cliente.getEndereco());
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        String novoNome = nomeField.getText();
        String novoCpf = cpfField.getText();
        String novoEndereco = enderecoField.getText();

        if (novoNome == null || novoNome.trim().isEmpty() ||
                novoCpf == null || novoCpf.trim().isEmpty() ||
                novoEndereco == null || novoEndereco.trim().isEmpty()) {

            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Todos os campos são obrigatórios.");
            return;
        }

        clienteParaEditar.setNome(novoNome);
        clienteParaEditar.setCpf(novoCpf);
        clienteParaEditar.setEndereco(novoEndereco);

        try {
            clienteService.alterarCliente(clienteParaEditar);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Cliente atualizado com sucesso!");
            closeWindow();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Atualizar", "Ocorreu um erro ao atualizar o cliente: " + e.getMessage());
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