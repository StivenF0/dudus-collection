package com.ufersa.duduscollection.view.disco;

import com.ufersa.duduscollection.model.entities.Disco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DiscoController {
    @FXML
    private TableView<Disco> tabelaDiscos;

    @FXML
    void handleAdicionarButton(ActionEvent event) {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/discos/AdicionarDisco.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Disco");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            // Atualizar tabela depois de fechar
            tabelaDiscos.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleEditarButton(ActionEvent event) {
//        Disco discoSelecionado = tabelaDiscos.getSelectionModel().getSelectedItem();

        if (true) {
            try {
                String fxmlPath = "/com/ufersa/duduscollection/view/main/discos/EditarDisco.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                EditarDiscoController controller = loader.getController();
//                controller.initData(discoSelecionado);

                Stage modalStage = new Stage();
                modalStage.setTitle("Editar Disco");
                modalStage.setScene(new Scene(root));
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.showAndWait();

                tabelaDiscos.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Mostrar alerta
            System.out.println("Por favor, selecione um disco para editar.");
        }
    }
}
