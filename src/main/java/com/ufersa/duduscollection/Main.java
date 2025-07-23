package com.ufersa.duduscollection;

import com.ufersa.duduscollection.model.dao.ClienteDAO;
import com.ufersa.duduscollection.model.dao.LivroDAO;
import com.ufersa.duduscollection.model.dao.ProdutoDAO;
import com.ufersa.duduscollection.model.dao.UsuarioDAO;
import com.ufersa.duduscollection.model.dao.impl.ClienteDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.LivroDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.ProdutoDAOImpl;
import com.ufersa.duduscollection.model.dao.impl.UsuarioDAOImpl;
import com.ufersa.duduscollection.model.entities.Cliente;
import com.ufersa.duduscollection.model.entities.Livro;
import com.ufersa.duduscollection.model.entities.Produto;
import com.ufersa.duduscollection.model.entities.Usuario;
import com.ufersa.duduscollection.model.services.ClienteService;
import com.ufersa.duduscollection.model.services.LivroService;
import com.ufersa.duduscollection.model.services.LoginService;
import com.ufersa.duduscollection.model.services.ProdutoService;
import com.ufersa.duduscollection.util.JPAUtil;
import jakarta.persistence.EntityManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.Optional;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        // launch();

        EntityManager em = JPAUtil.getEntityManager();

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl(em);
        LoginService loginService = new LoginService(usuarioDAO);

        ClienteDAO clienteDAO = new ClienteDAOImpl(em);
        ClienteService clienteService = new ClienteService(clienteDAO);

        ProdutoDAO produtoDAO = new ProdutoDAOImpl(em);
        ProdutoService produtoService = new ProdutoService(produtoDAO);

        LivroDAO livroDAO = new LivroDAOImpl(em);
        LivroService livroService = new LivroService(livroDAO);


        // Representação da tela de login
        String nomeDigitado = "admin";
        String senhaDigitada = "123456";

        Optional<Usuario> usuarioAutenticado = loginService.autenticar(nomeDigitado, senhaDigitada);

        if (usuarioAutenticado.isPresent()) {
            Usuario usuario = usuarioAutenticado.get();
            System.out.println("Bem-vindo! " + usuario);
        } else {
            System.out.println("Login ou senha inválidos!");
        }

        // Adicionar Cliente
        Cliente cliente1 = new Cliente("Stiven", "Rua teste", "11111111111");
        clienteService.adicionarCliente(cliente1);

        /*

        // Alterar Cliente
        Optional<Cliente> clienteOpt2 = clienteService.buscarPorCpf("11111111111");
        if (clienteOpt2.isPresent()) {
            Cliente cliente2 = clienteOpt2.get();
            cliente2.setNome("Felipe");
            clienteService.alterarCliente(cliente2);
        }

        // Remover Cliente
        Optional<Cliente> clienteOpt3 = clienteService.buscarPorCpf("11111111111");
        if (clienteOpt3.isPresent()) {
            Cliente cliente3 = clienteOpt3.get();
            clienteService.deletarCliente(cliente3);
        }

        // Adicionar um produto
        Produto produto1 =
                new Produto("Livro 1", null, 15, new BigDecimal("150.22"));
        produtoService.adicionarProduto(produto1);

        // Modificar um produto
        Optional<Produto> produtoOpt2 = produtoService.buscarPorId(1L);
        if (produtoOpt2.isPresent()) {
            Produto produto2 = produtoOpt2.get();
            produto2.setValorAluguel(new BigDecimal("200.70"));
            produtoService.alterarProduto(produto2);
        }

        // Remover um produto
        Optional<Produto> produtoOpt3 = produtoService.buscarPorId(1L);
        if (produtoOpt3.isPresent()) {
            Produto produto3 = produtoOpt3.get();
            produtoService.deletarProduto(produto3);
        }

        // Adicionar um livro
        Livro livro1 =
                new Livro("Livro 1", null, 4, new BigDecimal("200"), "Ficção", 200);
        livroService.adicionarLivro(livro1);
        */
    }
}
