# Desafio Backend - Gerenciador de Tarefas Assíncronas

Este projeto é uma solução completa para o desafio de avaliação de habilidades em Spring Boot e Programação Assíncrona

A aplicação consiste em um serviço de gerenciamento de tarefas que implementa desde operações CRUD básicas até funcionalidades avançadas como processamento assíncrono com controle de concorrência e tolerância a falhas.

## Tecnologias Utilizadas
* **Java 17**
* **Spring Boot 3.3.1**
* **Spring Web:** Para a criação da API RESTful.
* **Spring Data JPA:** Para a persistência de dados.
* **Spring Retry:** Para a implementação da lógica de retentativas.
* **Spring AOP:** Como dependência para o funcionamento do Retry.
* **H2 Database:** Banco de dados em memória para desenvolvimento e testes.
* **Maven:** Gerenciador de dependências e build do projeto.
* **Lombok:** Para a redução de código boilerplate.

## Funcionalidades Implementadas

O projeto foi desenvolvido seguindo as três etapas propostas, cobrindo todos os requisitos.

* **🟢 Etapa 1 – Obrigatória (Básico)**
    * Criação de uma API REST completa com endpoints para as operações CRUD (Create, Read, Update, Delete).
    * Persistência de dados utilizando Spring Data JPA e banco de dados H2 em memória.
    * Entidade `Tarefa` com os campos `id`, `nome` e `status`.

* **🟡 Etapa 2 – Intermediário (Recomendável)**
    * Implementação de processamento assíncrono com a anotação `@Async` para não bloquear a thread principal.
    * Criação de novas tarefas de forma agendada a cada 5 segundos utilizando `@Scheduled`.

* **🔴 Etapa 3 – Avançado (Diferencial Técnico)**
    * **Controle de Concorrência:** Utilização de um `ThreadPoolTaskExecutor` customizado para limitar o processamento a um máximo de **3 tarefas simultâneas**.
    * **Tolerância a Falhas:** Implementação de uma política de retentativas automáticas (`@Retryable`) para tarefas que falham. Uma tarefa tenta ser reprocessada até 3 vezes antes de ser marcada como `FALHOU`.
    * **Relatório:** Criação de um endpoint específico (`/tarefas/relatorio`) para sumarizar a quantidade de tarefas por status.

## Como Executar o Projeto

### Pré-requisitos
* Java 17 (ou superior)
* Apache Maven 3.8+

### Passo a Passo
1.  **Clone o repositório:**
    ```bash
    git clone Tigrelo/BrTec
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd BrTec
    ```

3.  **Execute a aplicação com o Maven:**
    ```bash
    mvn spring-boot:run
    ```

4.  A aplicação estará disponível em `http://localhost:8080`.
5.  O console do banco de dados H2 pode ser acessado em `http://localhost:8080/h2-console` (use o JDBC URL `jdbc:h2:mem:testdb` e usuário `sa`).

## Endpoints da API

Uma coleção do Insomnia para facilitar os testes pode ser encontrada no arquivo `[NOME_DO_SEU_ARQUIVO_INSOMNIA].json` na raiz deste repositório.

### Tarefas (CRUD)

* **`POST /tarefas`**: Cria uma nova tarefa.
    * **Body (JSON):**
        ```json
        {
          "nome": "Minha Nova Tarefa"
        }
        ```
    * **Resposta:** `200 OK` com os dados da tarefa criada.

* **`GET /tarefas`**: Lista todas as tarefas existentes.
    * **Resposta:** `200 OK` com um array de tarefas.

* **`GET /tarefas/{id}`**: Busca uma tarefa específica pelo seu ID.
    * **Exemplo:** `GET /tarefas/1`
    * **Resposta:** `200 OK` com os dados da tarefa ou `404 Not Found` se não existir.

* **`PUT /tarefas/{id}`**: Atualiza uma tarefa existente.
    * **Exemplo:** `PUT /tarefas/1`
    * **Body (JSON):**
        ```json
        {
          "nome": "Nome da Tarefa Atualizado",
          "status": "CONCLUIDA"
        }
        ```
    * **Resposta:** `200 OK` com os dados atualizados ou `404 Not Found`.

* **`DELETE /tarefas/{id}`**: Deleta uma tarefa existente.
    * **Exemplo:** `DELETE /tarefas/1`
    * **Resposta:** `204 No Content` em caso de sucesso ou `404 Not Found`.

### Relatórios

* **`GET /tarefas/relatorio`**: Gera um relatório com a contagem de tarefas por status.
    * **Resposta:** `200 OK` com um objeto JSON.
    * **Exemplo de Resposta:**
        ```json
        {
          "CONCLUIDA": 15,
          "EM_PROCESSAMENTO": 3,
          "FALHOU": 2,
          "PENDENTE": 1
        }
        ```

## Autor

**Isaac Ferreira Rodrigues**