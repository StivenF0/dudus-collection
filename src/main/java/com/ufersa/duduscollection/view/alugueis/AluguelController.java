package com.ufersa.duduscollection.view.alugueis;

import com.ufersa.duduscollection.model.entities.Aluguel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AluguelController {
    // Dentro da classe AlugueisController.java

    @FXML
    private TableView<Aluguel> tabelaAlugueis;

    @FXML
    void handleAdicionarButton(ActionEvent event) {
        try {
            String fxmlPath = "/com/ufersa/duduscollection/view/main/alugueis/AdicionarAluguel.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Aluguel");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.showAndWait();

            tabelaAlugueis.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleEditarButton(ActionEvent event) {
//        Aluguel aluguelSelecionado = tabelaAlugueis.getSelectionModel().getSelectedItem();

        if (true) {
            try {
                String fxmlPath = "/com/ufersa/duduscollection/view/main/alugueis/EditarAluguel.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                EditarAluguelController controller = loader.getController();
//                controller.initData(aluguelSelecionado);

                Stage modalStage = new Stage();
                modalStage.setTitle("Editar Aluguel");
                modalStage.setScene(new Scene(root));
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.showAndWait();

                tabelaAlugueis.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, selecione um aluguel para editar.");
        }
    }
}
