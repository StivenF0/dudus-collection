package com.ufersa.duduscollection.view.cliente;

import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.dao.impl.ClienteDAOImpl;
import com.ufersa.duduscollection.model.entities.Cliente;
import com.ufersa.duduscollection.model.exception.CpfDuplicadoException;
import com.ufersa.duduscollection.model.services.ClienteService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdicionarClienteController {

    @FXML private TextField nomeField;
    @FXML private TextField cpfField;
    @FXML private TextField enderecoField;
    @FXML private Button salvarButton;

    private final EntityManager em;
    private final ClienteDAO clienteDAO;
    private final ClienteService clienteService;

    public AdicionarClienteController() {
        em = JPAUtil.getEntityManager();
        clienteDAO = new ClienteDAOImpl(em);
        clienteService = new ClienteService(clienteDAO);
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        // 1. Coleta os dados dos campos da interface
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String endereco = enderecoField.getText();

        // 2. Validação básica para garantir que os campos não estão vazios
        if (nome == null || nome.trim().isEmpty() ||
                cpf == null || cpf.trim().isEmpty() ||
                endereco == null || endereco.trim().isEmpty()) {

            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Todos os campos são obrigatórios.");
            return; // Interrompe a execução do método se a validação falhar
        }

        try {
            // 3. Cria e popula o objeto da entidade
            Cliente novoCliente = new Cliente(nome, endereco, cpf);

            // 4. Chama o serviço para salvar o novo cliente
            clienteService.adicionarCliente(novoCliente); // Supondo que seu ClienteService tenha um método salvar()

            // 5. Fornece feedback de sucesso e fecha a janela
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Cliente salvo com sucesso!");
            closeWindow();

        } catch (CpfDuplicadoException e) {
            System.out.println(e);
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", e.getMessage());
        } catch (Exception e) {
            // 6. Em caso de erro na camada de serviço ou DAO (ex: CPF duplicado), mostra um alerta
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro ao salvar o cliente: " + e.getMessage());
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
        alert.setHeaderText(null); // Remove o cabeçalho
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}