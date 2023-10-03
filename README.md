# desafio-softplan

## CRUD RESTful simples voltado para a entidade "Produto" usando Java 17 com Spring Boot

### Workspace no Postman
Segue link para o Workspace público criado no Postman onde constam requisições a todos os endereçamentos criados documentadas e já devidamente formatadas: https://www.postman.com/pedromottaquessada/workspace/desafio-softplan/collection/30167569-a69db1e0-3c06-43fd-9c72-604dd65175cb?action=share&creator=30167569

### Banco de dados
Foi utilizado o banco H2, responsável por persistir os dados em memória durante a execução da aplicação.<br>
O banco é composto por 2 tabelas. Os id's de ambas são auto-incrementais. Segue mais informações a respeito de cada uma:<br>

<b>Produtos</b><br>
Informação: armazena os produtos<br>
Atributos:

|        nome        |  tipo   | NOT NULL | UNIQUE |
|:------------------:|:-------:|:--------:|:------:|
|       id(PK)       | Bigint  |   Sim    |  Sim   |
|        nome        | Varchar |   Sim    |  Sim   |
| quantidade_estoque | Bigint  |   Sim    |  Não   |
|       preco        |  Float  |   Sim    |  Não   |<br><br>

<b>Usuarios</b><br>
Informação: armazena os usuários<br>
Atributos:

|   nome   |  tipo   | NOT NULL | UNIQUE |
|:--------:|:-------:|:--------:|:------:|
|  id(PK)  | Bigint  |   Sim    |  Sim   |
| username | Varchar |   Sim    |  Sim   |
|  senha   | Varchar |   Sim    |  Não   |
|   adm    |   Bit   |   Sim    |  Não   |<br><br>
    
No momento da inicialização da aplicação o arquivo data.sql (localizado na pasta resources do projeto) é executado. O intuito de sua execução é a inserção de dados iniciais no banco. Segue mais informações a respeito de cada dado inserido:<br>

<b>Produtos inseridos</b><br>

| id |  nome  | quantidade_estoque | preco |
|:--:|:------:|:------------------:|:-----:|
| 1  | Panela |         6          | 78.99 |<br><br>

<b>Usuários inseridos</b><br>

| id | username |  senha   |  adm  |
|:--:|:--------:|:--------:|:-----:|
| 1  |  admin   | senha123 | true  |
| 2  |   user   | senha123 | false |<br><br>

### Validação de usuário
Como forma de controlar o acesso as informações foi desenvolvido um esquema para validação de usuários.<br>
Para acessar os enderaçamentos da API é necessário passar no cabeçalho da requisição a chave "Authorization" preenchida com o valor das credenciais de acesso do usuário que está realizando a requisição criptografadas em Base64. Esse valor é "YWRtaW46c2VuaGExMjM=" para o usuário adm previamente cadastrado durante a inicialização da aplicação e "dXNlcjpzZW5oYTEyMw==" para o usuário não adm previamente cadastrado.<br>
No Workspace do Postman compartilhado no início do documento as requisições já estão configuradas com o modelo de autorização "Basic Auth" e preenchidas com username e senha do usuário adm previamente cadastrados durante a inicialização da aplicação. Caso desejar trocar o usuário responsável pela requisição basta abrir a seção "Authorization" e preencher os campos "username" e "password" com as informações desejadas.

### Endereçamentos
A API construída é composta por 6 endpoints:

|      endereçamento       |  tipo  | exclusivo para adm's |   corpo da requisição (em JSON)   |                                          corpo da resposta (em JSON)                                          |
|:------------------------:|:------:|:--------------------:|:---------------------------------:|:-------------------------------------------------------------------------------------------------------------:|
|     /getAllProdutos      |  GET   |         não          |               vazio               |                                 lista de todos produtos registrados no banco                                  |
|   /getProdutoById/{id}   |  GET   |         não          |               vazio               |                             produto cujo id seja igual ao parâmetro id informado                              |
| /getProdutoByNome/{nome} |  GET   |         não          |               vazio               | lista de todos produtos registrados no banco cujo nome contenha o parâmetro nome informado (case insensitive) |
|       /addProduto        |  POST  |         sim          |  produto a ser inserido no banco  |                                           produto inserido no banco                                           |
|      /updateProduto      |  POST  |         sim          | produto a ser atualizado no banco |                                          produto atualizado no banco                                          |
| /deleteProdutoById/{id}  | DELETE |         sim          |               vazio               |                                                     vazio                                                     |

### Retornos
Em caso de sucesso, o corpo da resposta vai variar para cada um dos endereçamentos (informação abordada no tópico anterior) e seu código será OK (200).<br>
Em caso de falha, o corpo da resposta virá nulo e seu cabeçalho virá com a chave "MensagemErro" preenchida com o valor referente a mensagem do erro ocorrido. Já seu código irá variar de acordo com falha ocorrida, caso tenham sido passadas credenciais de acesso inválidas o código será FORBIDDEN (403), caso a requisição seja exclusiva para adm's e o usuário que a realizou não tenha esse nível de permissão o código será UNAUTHORIZED (401), caso tenha sido passado o ID de um produto que não consta no banco de dados o código será NOT_FOUND (404) e para outras falhas o código será INTERNAL_SERVER_ERROR (500).