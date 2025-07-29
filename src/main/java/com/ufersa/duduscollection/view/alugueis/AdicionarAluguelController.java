package com.ufersa.duduscollection.view.alugueis;

import com.ufersa.duduscollection.model.dao.impl.AluguelDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.ClienteDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.ProdutoDAOImpl;
import com.ufersa.duduscollection.model.entities.Aluguel;
import com.ufersa.duduscollection.model.entities.Cliente;
import com.ufersa.duduscollection.model.entities.Produto;
import com.ufersa.duduscollection.model.services.AluguelService;
import com.ufersa.duduscollection.model.services.ClienteService;
import com.ufersa.duduscollection.model.services.ProdutoService; // Importe o serviço de Produto
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdicionarAluguelController {

    @FXML private ComboBox<Cliente> clienteComboBox;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextField valorTotalField;
    @FXML private ComboBox<Produto> produtosDisponiveisComboBox;
    @FXML private ListView<Produto> produtosNoAluguelListView;
    @FXML private Button salvarButton;

    private final AluguelService aluguelService;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private final ObservableList<Produto> produtosNoAluguel = FXCollections.observableArrayList();

    public AdicionarAluguelController() {
        // Obviamente, você precisará criar o DAO e Service para Produto
        EntityManager em = JPAUtil.getEntityManager();
        this.aluguelService = new AluguelService(new AluguelDAOImpl(em));
        this.clienteService = new ClienteService(new ClienteDAOImpl(em));
        this.produtoService = new ProdutoService(new ProdutoDAOImpl(em));
    }

    @FXML
    public void initialize() {
        // Carrega os ComboBoxes com dados do banco
         List<Cliente> clientes = clienteService.buscarTodos();
         clienteComboBox.setItems(FXCollections.observableArrayList(clientes));
         List<Produto> produtos = produtoService.buscarTodos();
         produtosDisponiveisComboBox.setItems(FXCollections.observableArrayList(produtos));

        // Liga a ListView à nossa lista observável
        produtosNoAluguelListView.setItems(produtosNoAluguel);
    }

    @FXML
    void handleAdicionarProduto(ActionEvent event) {
        Produto produtoSelecionado = produtosDisponiveisComboBox.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null && !produtosNoAluguel.contains(produtoSelecionado)) {
            produtosNoAluguel.add(produtoSelecionado);
            recalcularValorTotal();
        }
    }

    private void recalcularValorTotal() {
        double total = 0.0;
        for (Produto p : produtosNoAluguel) {
            total += p.getValorAluguel().doubleValue();
        }
        valorTotalField.setText(String.format("%.2f", total));
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        Cliente cliente = clienteComboBox.getSelectionModel().getSelectedItem();
        LocalDate dataInicio = dataInicioPicker.getValue();
        LocalDate dataFim = dataFimPicker.getValue();

        if (cliente == null || dataInicio == null || dataFim == null || produtosNoAluguel.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Validação", "Cliente, datas e ao menos um produto são obrigatórios.");
            return;
        }

        try {
            double valorTotal = Double.parseDouble(valorTotalField.getText().replace(",", "."));

            Aluguel novoAluguel = new Aluguel();
            novoAluguel.setCliente(cliente);
            novoAluguel.setDataInicio(Date.valueOf(dataInicio));
            novoAluguel.setDataFim(Date.valueOf(dataFim));
            novoAluguel.setValorTotal(valorTotal);
            novoAluguel.setProdutos(new ArrayList<>(produtosNoAluguel));

            aluguelService.registrarAluguel(novoAluguel);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Aluguel registrado com sucesso!");
            closeWindow();

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Ocorreu um erro ao registrar o aluguel.");
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