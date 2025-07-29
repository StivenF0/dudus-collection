package com.ufersa.duduscollection.view.cliente;

import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.dao.impl.ClienteDAOImpl;
import com.ufersa.duduscollection.model.entities.Cliente;
import com.ufersa.duduscollection.model.services.ClienteService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ClienteController {
    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableColumn<Cliente, String> colunaNome;
    @FXML
    private TableColumn<Cliente, Long> colunaId;
    @FXML
    private TableColumn<Cliente, String> colunaCpf;
    @FXML
    private TableColumn<Cliente, String> colunaEndereco;
    @FXML
    private TextField pesquisarField; // Campo de busca
    @FXML
    private TableColumn<Cliente, Void> colunaAcoes;

    @FXML
    private ChoiceBox<String> filtroChoiceBox; // Caixa de seleção para o filtro

    // Lista para guardar todos os clientes carregados do banco.
    private ObservableList<Cliente> masterData = FXCollections.observableArrayList();

    private final EntityManager em;
    private final ClienteDAO clienteDAO;
    private final ClienteService clienteService;

    public ClienteController() {
        em = JPAUtil.getEntityManager();
        clienteDAO = new ClienteDAOImpl(em);
        clienteService = new ClienteService(clienteDAO);
    }

    @FXML
    public void initialize() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        carregarClientes();

        filtroChoiceBox.getItems().addAll("ID", "Nome", "CPF", "Endereço");
        filtroChoiceBox.setValue("Nome"); // Define "Nome" como o filtro padrão

        // 5. Cria a FilteredList envolvendo a lista mestra
        FilteredList<Cliente> filteredData = new FilteredList<>(masterData, p -> true);

        // 6. Adiciona um listener ao campo de pesquisa. Este código será executado toda vez que o texto mudar.
        pesquisarField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cliente -> {
                // Se o campo de busca estiver vazio, mostra todos os clientes.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Pega o termo de busca e o filtro selecionado
                String lowerCaseFilter = newValue.toLowerCase();
                String filtroSelecionado = filtroChoiceBox.getValue();

                // Aplica o filtro com base na seleção do ChoiceBox
                switch (filtroSelecionado) {
                    case "ID":
                        // Compara o ID exato
                        return String.valueOf(cliente.getId()).equals(lowerCaseFilter);
                    case "Nome":
                        // Verifica se o nome do cliente contém o texto de busca (ignorando maiúsculas/minúsculas)
                        return cliente.getNome().toLowerCase().contains(lowerCaseFilter);
                    case "CPF":
                        // Verifica se o CPF contém o texto de busca
                        return cliente.getCpf().contains(lowerCaseFilter);
                    case "Endereço":
                        return cliente.getEndereco().toLowerCase().contains(lowerCaseFilter);
                    default:
                        return false; // Não mostra nada se o filtro for inválido
                }
            });
        });

        filtroChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Dispara o filtro ao "fingir" que o texto mudou
            pesquisarField.setText(pesquisarField.getText());
        });

        // 7. Define a lista filtrada como o conteúdo da tabela
        tabelaClientes.setItems(filteredData);

        // Cell factory para os botões de remover e editar
        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Cliente, Void> call(final TableColumn<Cliente, Void> param) {
                final TableCell<Cliente, Void> cell = new TableCell<>() {
                    private final Button editBtn = new Button("Editar");
                    private final Button removeBtn = new Button("Remover");

                    private final HBox pane = new HBox(editBtn, removeBtn);
                    {
                        pane.setSpacing(10);
                        editBtn.getStyleClass().add("edit-button");   // Aplica estilos CSS se existirem
                        removeBtn.getStyleClass().add("remove-button");

                        editBtn.setOnAction((ActionEvent event) -> {
                            Cliente cliente = getTableView().getItems().get(getIndex());
                            abrirModalEdicao(cliente); // Chama o método de edição que já tínhamos
                        });

                        removeBtn.setOnAction((ActionEvent event) -> {
                            Cliente cliente = getTableView().getItems().get(getIndex());
                            removerCliente(cliente); // Chama nosso novo método de remoção
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        };

        colunaAcoes.setCellFactory(cellFactory);
    }

    private void carregarClientes() {
        List<Cliente> clientes = clienteService.buscarTodos();
        masterData.setAll(clientes); // Usa setAll para limpar e adicionar os novos dados
    }

    @FXML
    void handleAdicionarButton(ActionEvent event) {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/clientes/AdicionarCliente.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Cliente");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            // Atualizar tabela depois de fechar
            tabelaClientes.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            carregarClientes();
        }
    }

    // 2. Refatore a lógica de abrir o modal para um método privado
    private void abrirModalEdicao(Cliente cliente) {
        if (cliente == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Cliente Selecionado",
                    "Por favor, selecione um cliente na tabela para editar.");
            return;
        }

        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/clientes/EditarCliente.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            EditarClienteController controller = loader.getController();
            controller.initData(cliente); // Passa o cliente para o modal

            Stage modalStage = new Stage();
            modalStage.setTitle("Editar Cliente");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            carregarClientes(); // Atualiza a tabela após a edição

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleEditarButton(ActionEvent event) {
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        abrirModalEdicao(clienteSelecionado);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Este é o novo método privado que contém a lógica de remoção.
    private void removerCliente(Cliente cliente) {
        if (cliente == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Cliente Selecionado",
                    "Por favor, selecione um cliente para remover.");
            return;
        }

        // Cria e exibe um diálogo de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Exclusão");
        alert.setHeaderText("Tem certeza que deseja remover o cliente selecionado?");
        alert.setContentText("Cliente: " + cliente.getNome());

        Optional<ButtonType> result = alert.showAndWait();

        // Se o usuário clicou em "OK", prossiga com a exclusão
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                clienteService.deletarCliente(cliente);
                carregarClientes(); // Atualiza a tabela
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Remover",
                        "Não foi possível remover o cliente. Verifique se ele não possui aluguéis associados.");
                e.printStackTrace();
            }
        }
    }

    // Agora, o seu antigo handleRemoverButton (do botão de fora) fica muito mais simples.
    @FXML
    void handleRemoverButton(ActionEvent event) {
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        removerCliente(clienteSelecionado);
    }
}
