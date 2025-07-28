package com.ufersa.duduscollection.view.livro;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LivroController {
    @FXML
    void handleAdicionarButton(ActionEvent event) { // Certifique-se que o onAction do seu botão "+" chame este método
        try {
            // 1. Carrega o arquivo FXML da nova janela
            String fxmlPath = "/com/ufersa/duduscollection/view/main/livros/AdicionarLivro.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // 2. Cria um novo Stage (uma nova janela)
            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar Novo Livro");
            modalStage.setScene(new Scene(root));

            // 3. Define a modalidade da janela.
            //    WINDOW_MODAL bloqueia a interação com a janela principal enquanto esta estiver aberta.
            modalStage.initModality(Modality.APPLICATION_MODAL);

            // Opcional: Define a janela principal como "dona" desta nova janela
            // Stage primaryStage = (Stage) pesquisarField.getScene().getWindow(); // pega a janela principal
            // modalStage.initOwner(primaryStage);

            // 4. Mostra a janela e espera até que ela seja fechada
            modalStage.showAndWait();

            // Após a janela ser fechada, você pode adicionar código aqui para atualizar a tabela
            // de livros, por exemplo.

        } catch (IOException e) {
            e.printStackTrace();
            // Tratar o erro, talvez mostrando um alerta para o usuário
        }
    }

    @FXML
    void handleEditarButton(ActionEvent event) { // Conecte este método ao onAction do seu botão de editar (✎)
        // 1. Pega o item selecionado na tabela
//        Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();

        // 2. Verifica se um item foi realmente selecionado
        if (true) {
            try {
                // 3. Carrega o FXML da janela de edição
                String fxmlPath = "/com/ufersa/duduscollection/view/main/livros/EditarLivro.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                // 4. Pega o controlador da janela de edição
                EditarLivroController controller = loader.getController();

                // 5. PASSA O LIVRO SELECIONADO para o controlador da nova janela
//                controller.initData(livroSelecionado);

                // 6. Configura e exibe a janela modal
                Stage modalStage = new Stage();
                modalStage.setTitle("Editar Livro");
                modalStage.setScene(new Scene(root));
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.showAndWait();

                // Opcional: Atualizar a tabela após fechar a janela
                //tabelaLivros.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Se nenhum livro estiver selecionado, mostre um alerta ao usuário
            // (Implementação de um alerta seria o próximo passo ideal aqui)
            System.out.println("Por favor, selecione um livro para editar.");
        }
    }
}
