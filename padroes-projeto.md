Aqui estão os principais padrões que utilizamos no dudus-collection:

1. Padrão DAO (Data Access Object)

    - O que é? É um padrão que abstrai e encapsula todo o acesso a dados de uma fonte específica (no nosso caso, o banco
      de
      dados PostgreSQL via JPA). Ele cria uma ponte entre a aplicação e o banco.
    - Onde usamos? Em toda a sua camada de acesso a dados. As classes ClienteDAOImpl, LivroDAOImpl, DiscoDAOImpl, etc.,
      são
      implementações concretas deste padrão. Elas escondem as complexidades do EntityManager e das queries JPQL das
      outras
      camadas.
    - Por que é útil? Separa a lógica de negócio (nos Services) das complexidades do banco de dados. Se um dia você
      quisesse
      trocar o PostgreSQL por outro banco, só precisaria mudar as classes DAO, sem tocar nos seus serviços ou
      controladores.

2. Camada de Serviço (Service Layer) e Padrão Facade

    - O que é? A Camada de Serviço atua como um ponto central para executar a lógica de negócio. Ela frequentemente age
      como
      um Facade (Fachada), que é um padrão que fornece uma interface simplificada para um subsistema complexo.
    - Onde usamos? As classes ClienteService, AluguelService, etc. são a sua camada de serviço. Quando seu
      AluguelController
      precisa salvar um aluguel, ele não fala com o AluguelDAO, ProdutoDAO ou gerencia transações; ele simplesmente faz
      uma
      chamada: aluguelService.registrarAluguel(). O Service é a fachada que esconde toda a complexidade por trás dessa
      operação.
    - Por que é útil? Centraliza a lógica de negócio, gerencia as transações e desacopla a camada de apresentação (
      Controllers) da camada de dados (DAOs).

3. Padrão MVC (Model-View-Controller) / MVP (Model-View-Presenter)

    - O que é? Um padrão de arquitetura que separa a aplicação em três partes interconectadas. No nosso caso, é uma
      variação
      de MVP.
    - Onde usamos? Em toda a estrutura da aplicação:
        - Model (Modelo): Toda a sua lógica de negócio e dados. Inclui as Entidades (Cliente, Livro), os DAOs e os
          Services.
        - View (Visão): Os arquivos .fxml. Eles são a representação puramente visual da sua interface, sem lógica.
        - Presenter (Apresentador): As classes Controller (ex: ClienteController). Elas agem como o intermediário:
          recebem
          eventos da View (cliques de botão) e comandam o Model (chamando os Services). Depois, recebem os dados de
          volta do
          Model e atualizam a View.
    - Por que é útil? É a base da organização do projeto. Garante que a lógica da interface gráfica esteja separada da
      lógica de negócio, tornando o código mais organizado, testável e fácil de manter.

4. Padrão Observer (Observador)

    - O que é? Um padrão onde um objeto (o "sujeito") mantém uma lista de seus dependentes (os "observadores") e os
      notifica
      automaticamente sobre quaisquer mudanças de estado.
    - Onde usamos? Em toda a parte de atualização automática das tabelas!
        - A FilteredList que usamos na busca é um "observador" do TextField de pesquisa. Toda vez que o texto muda (o "
          sujeito" muda de estado), a lista é notificada e se atualiza.
        - A TableView é uma "observadora" da ObservableList. Quando você adiciona ou remove um item da lista, a tabela é
          notificada e se redesenha automaticamente.
        - Por que é útil? Mantém a interface do usuário sincronizada com os dados da aplicação de forma reativa e
          eficiente,
          sem que precisemos escrever código manual para atualizar a tabela a cada pequena mudança.

5. Padrão Singleton

    - O que é? Garante que uma classe tenha apenas uma única instância e fornece um ponto de acesso global a essa
      instância.
    - Onde usamos? Na nossa classe JPAUtil. O EntityManagerFactory (emf) é um objeto caro de ser criado e deve existir
      apenas um em toda a aplicação. Nossa classe JPAUtil garante que ele seja criado apenas uma vez (static final) e
      fornece um ponto de acesso global (JPAUtil.getEntityManager()).

Por que é útil? Economiza recursos e garante um controle centralizado sobre um componente crítico e compartilhado.

6. Padrão Factory (Fábrica)

    - O que é? Um padrão que fornece uma interface para criar objetos em uma superclasse, mas permite que as subclasses
      alterem o tipo de objetos que serão criados. Nós usamos variações dele.
    - Onde usamos?
        - setControllerFactory: A forma mais explícita. Em vez de deixar o FXMLLoader criar o controller da forma
          padrão (
          new Controller()), nós fornecemos uma "fábrica" (a expressão lambda) que ensinava o FXMLLoader a construir o
          controller com as nossas dependências.

        - setCellFactory: A mesma ideia. Para a coluna "Ações", nós fornecemos uma fábrica que, em vez de criar uma
          célula
          de
          texto padrão, "fabricava" uma célula customizada contendo um HBox com botões.

        - Por que é útil? Desacopla a criação de objetos do seu uso, permitindo lógicas de construção muito mais
          complexas e
          flexíveis.
