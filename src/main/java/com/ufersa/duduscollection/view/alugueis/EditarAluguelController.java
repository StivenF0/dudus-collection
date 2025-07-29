package com.ufersa.duduscollection.view.alugueis;

import com.ufersa.duduscollection.model.dao.AluguelDAO;
import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.dao.ProdutoDAO; // Importe o DAO de Produto
import com.ufersa.duduscollection.model.dao.impl.AluguelDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.ClienteDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.ProdutoDAOImpl; // Importe a implementação
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

public class EditarAluguelController {

    @FXML private ComboBox<Cliente> clienteComboBox;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TextField valorTotalField;
    @FXML private ComboBox<Produto> produtosDisponiveisComboBox;
    @FXML private ListView<Produto> produtosNoAluguelListView;
    @FXML private Button salvarButton;

    private Aluguel aluguelParaEditar;
    private final AluguelService aluguelService;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private final ObservableList<Produto> produtosNoAluguel = FXCollections.observableArrayList();

    public EditarAluguelController() {
        EntityManager em = JPAUtil.getEntityManager();
        this.aluguelService = new AluguelService(new AluguelDAOImpl(em));
        this.clienteService = new ClienteService(new ClienteDAOImpl(em));
        this.produtoService = new ProdutoService(new ProdutoDAOImpl(em));
    }

    @FXML
    public void initialize() {
        List<Cliente> clientes = clienteService.buscarTodos();
        clienteComboBox.setItems(FXCollections.observableArrayList(clientes));

        List<Produto> produtosDisponiveis = produtoService.buscarTodos();
        produtosDisponiveisComboBox.setItems(FXCollections.observableArrayList(produtosDisponiveis));

        produtosNoAluguelListView.setItems(produtosNoAluguel);
    }

    public void initData(Aluguel aluguel) {
        this.aluguelParaEditar = aluguel;

        clienteComboBox.setValue(aluguel.getCliente());
        valorTotalField.setText(String.valueOf(aluguel.getValorTotal()));

        if (aluguel.getProdutos() != null) {
            produtosNoAluguel.setAll(aluguel.getProdutos());
        }

        if (aluguel.getDataInicio() != null) {
            dataInicioPicker.setValue(new java.sql.Date(aluguel.getDataInicio().getTime()).toLocalDate());
        }
        if (aluguel.getDataFim() != null) {
            dataFimPicker.setValue(new java.sql.Date(aluguel.getDataFim().getTime()).toLocalDate());
        }
    }

    @FXML
    void handleAdicionarProduto(ActionEvent event) {
        Produto produtoSelecionado = produtosDisponiveisComboBox.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null && !produtosNoAluguel.contains(produtoSelecionado)) {
            produtosNoAluguel.add(produtoSelecionado);
            recalcularValorTotal();
        }
    }

    @FXML
    void handleRemoverProduto(ActionEvent event) {
        Produto produtoSelecionado = produtosNoAluguelListView.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            produtosNoAluguel.remove(produtoSelecionado);
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
            double valorTotal = Double.parseDouble(valorTotalField.getText().replace(",","."));

            aluguelParaEditar.setCliente(cliente);
            aluguelParaEditar.setDataInicio(Date.valueOf(dataInicio));
            aluguelParaEditar.setDataFim(Date.valueOf(dataFim));
            aluguelParaEditar.setValorTotal(valorTotal);
            aluguelParaEditar.setProdutos(new ArrayList<>(produtosNoAluguel));

            aluguelService.registrarDevolucao(aluguelParaEditar);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Aluguel atualizado com sucesso!");
            closeWindow();

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Atualizar", "Ocorreu um erro ao atualizar o aluguel: " + e.getMessage());
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