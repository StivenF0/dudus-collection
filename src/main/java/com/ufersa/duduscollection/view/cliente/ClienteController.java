package com.ufersa.duduscollection.view.cliente;

import com.ufersa.duduscollection.model.entities.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ClienteController {
    @FXML
    private TableView<Cliente> tabelaClientes;

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
        }
    }

    @FXML
    void handleEditarButton(ActionEvent event) {
//        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        // clienteSelecionado != null
        if (true) {
            try {
                String fxmlPath = "/com/ufersa/duduscollection/view/main/clientes/EditarCliente.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                EditarClienteController controller = loader.getController();
//                controller.initData(clienteSelecionado);

                Stage modalStage = new Stage();
                modalStage.setTitle("Editar Cliente");
                modalStage.setScene(new Scene(root));
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.showAndWait();

                tabelaClientes.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Mostrar alerta
            System.out.println("Por favor, selecione um cliente para editar.");
        }
    }
}
