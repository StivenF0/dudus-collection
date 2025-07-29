# dudus-collection

Um sistema de gerenciamento de desktop para uma coleção de livros e discos, permitindo o cadastro de itens, clientes e o controle de aluguéis.

Este projeto foi desenvolvido com JavaFX e utiliza um banco de dados PostgreSQL, gerenciado via Docker Compose para facilitar a configuração do ambiente de desenvolvimento.

## Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Interface Gráfica:** JavaFX 21
* **Build Tool:** Gradle
* **Banco de Dados:** PostgreSQL
* **ORM:** Hibernate / JPA
* **Containerização:** Docker & Docker Compose

## Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas na sua máquina:

* [Git](https://git-scm.com/)
* [JDK 21 ou superior](https://www.oracle.com/java/technologies/downloads/)
* [Docker](https://www.docker.com/products/docker-desktop/) (com Docker Compose incluído)

## Como Rodar o Projeto (Passo a Passo)

Siga estes passos para configurar e executar a aplicação localmente.

### 1. Clonar o Repositório

Abra seu terminal ou Git Bash e clone o projeto para a sua máquina:

```bash
git clone https://github.com/StivenF0/dudus-collection.git
cd dudus-collection
```

### 2. Iniciar o Banco de Dados com Docker

O projeto já inclui um arquivo `docker-compose.yml` que define e configura o contêiner do banco de dados PostgreSQL.

Com o Docker Desktop em execução, navegue até a pasta do projeto no seu terminal e execute o comando:

```bash
docker-compose up -d
```

* O comando `up` irá baixar a imagem do PostgreSQL (apenas na primeira vez) e iniciar o contêiner.
* A flag `-d` (detached) faz com que ele rode em segundo plano.
* Seu banco de dados agora está rodando e acessível em `localhost:5432`.

### 3. Rodar a Aplicação com Gradle

A aplicação já está configurada para se conectar ao banco de dados Docker através do arquivo `src/main/resources/META-INF/persistence.xml`.

Para iniciar a aplicação JavaFX, execute o seguinte comando no terminal, a partir da raiz do projeto:

**Para Linux/macOS:**
```bash
./gradlew run
```

**Para Windows:**
```shell
.\gradlew.bat run
```

O Gradle irá compilar o código e iniciar a interface gráfica da aplicação, que se conectará automaticamente ao banco de dados.

## Parando o Ambiente

Quando terminar de usar a aplicação, você pode parar o contêiner do banco de dados com o seguinte comando (na pasta do projeto):

```bash
docker-compose down
```

Isso irá parar o contêiner, mas seus dados continuarão salvos no volume do Docker, prontos para a próxima vez que você executar `docker-compose up -d`.
