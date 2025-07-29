package com.ufersa.duduscollection.view.relatorio;

import com.ufersa.duduscollection.model.dao.AluguelDAO;
import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.dao.DiscoDAO;
import com.ufersa.duduscollection.model.dao.LivroDAO;
import com.ufersa.duduscollection.model.dao.impl.AluguelDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.ClienteDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.DiscoDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.LivroDAOImpl;
import com.ufersa.duduscollection.model.entities.Aluguel;
import com.ufersa.duduscollection.model.entities.Produto;
import com.ufersa.duduscollection.model.services.*;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RelatorioController {
    @FXML private Label totalLivrosLabel;
    @FXML private Label totalDiscosLabel;
    @FXML private Label totalClientesLabel;
    @FXML private Label alugueisAtivosLabel;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private TableView<Aluguel> tabelaAlugueisFiltrados;
    @FXML private TableColumn<Aluguel, Long> colunaId;
    @FXML private TableColumn<Aluguel, String> colunaCliente;
    @FXML private TableColumn<Aluguel, String> colunaItens;
    @FXML private TableColumn<Aluguel, Date> colunaDataInicio;
    @FXML private TableColumn<Aluguel, Date> colunaDataFim;
    @FXML private TableColumn<Aluguel, Double> colunaPreco;

    private final EntityManager entityManager;

    private final LivroDAO livroDAO;
    private final DiscoDAO discoDAO;
    private final ClienteDAO clienteDAO;
    private final AluguelDAO aluguelDAO;

    private final LivroService livroService;
    private final DiscoService discoService;
    private final ClienteService clienteService;
    private final AluguelService aluguelService;

    public RelatorioController() {
        entityManager = JPAUtil.getEntityManager();

        livroDAO = new LivroDAOImpl(entityManager);
        discoDAO = new DiscoDAOImpl(entityManager);
        clienteDAO = new ClienteDAOImpl(entityManager);
        aluguelDAO = new AluguelDAOImpl(entityManager);

        livroService = new LivroService(livroDAO);
        discoService = new DiscoService(discoDAO);
        clienteService = new ClienteService(clienteDAO);
        aluguelService = new AluguelService(aluguelDAO);
    }

    @FXML
    public void initialize() {
        carregarResumoGeral();
        configurarTabela();
    }

    private void carregarResumoGeral() {
        totalLivrosLabel.setText(String.valueOf(livroService.contarTodos()));
        totalDiscosLabel.setText(String.valueOf(discoService.contarTodos()));
        totalClientesLabel.setText(String.valueOf(clienteService.contarTodos()));
        alugueisAtivosLabel.setText(String.valueOf(aluguelService.contarAtivos()));
    }

    private void configurarTabela() {
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colunaDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colunaCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente().getNome()));
        colunaItens.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProdutos().stream()
                        .map(Produto::getNome).collect(Collectors.joining(", "))));
    }

    @FXML
    void handleFiltrarPorData(ActionEvent event) {
        LocalDate inicio = dataInicioPicker.getValue();
        LocalDate fim = dataFimPicker.getValue();

        if (inicio == null || fim == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datas Inválidas", "Por favor, selecione a data de início e de fim.");
            return;
        }

        if (fim.isBefore(inicio)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Datas Inválidas", "A data de fim não pode ser anterior à data de início.");
            return;
        }

        List<Aluguel> alugueis = aluguelService.buscarPorIntervaloDeDatas(inicio, fim);
        tabelaAlugueisFiltrados.setItems(FXCollections.observableArrayList(alugueis));
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}