# RBoleto
API REST para geração de boletos que será consumido por um módulo de um sistema de gestão financeira de microempresas.

- [Pré-requisitos](#pré-requisitos)
- [Instruções](#instruções)
- [Endpoints](#endpoints)


## Pré-requisitos
- Java 1.8.0_162
- Maven 3.3.9
- Intellij IDEA
- Docker 17.05.0-ce
- SonnarQube 5.6 (Via Docker)

## Instruções

Todos os comandos deverão ser executado via terminal.
Abaixo iremos subir uma ferramenta para auxiliar na análise do código, SonnarQube, diretamente do docker:

``` docker run -p 9000:9000 jeffersonaraujop/sonar:latest ```

Fazer o checkout via GitHub e na raiz do projeto executar o comando abaixo:

``` mvn clean install ```

Com a execução realizada com sucesso, executar na raiz da aplicação:

```java -jar target/rboleto-1.0-SNAPSHOT.jar```

A partir de agora você poderá acessar os serviços pelos [endpoints](#endpoints).

Para utilizar a última versão estável:

``` docker run -p 8080:8080 jeffersonaraujop/rboleto:latest ```

Para acessar o SonnarQube:
- http://localhost:9000/


## Endpoints
Endpoints necessário para consumir os serviços da aplicação, caso tenha o Postman instalado poderá baixar e importar as chamadas [aqui](https://github.com/AraujoJefferson/rboleto/blob/master/ContaAzul-RBoleto.postman_collection.json):
- Criar boleto
	> **POST** http://localhost:8080/rest/bankslips

    Segue abaixo, exemplo com os campos para realizar a chamada **JSON** via **POST**:

	```
    {
      "due_date":"2018-01-01",
      "total_in_cents": "100000",
      "customer":"Trillian Company",
      "status":"PENDING"
    }
    ```
- Lista de boletos
	> **GET** http://localhost:8080/rest/bankslips/
- Detalhes do boleto
	> **GET** http://localhost:8080/rest/bankslips/***{id}***
- Pagar um boleto
	> **PUT** http://localhost:8080/rest/bankslips/***{id}***/pay
- Cancelar um boleto
	> **DELETE** http://localhost:8080/rest/bankslips/***{id}***/cancel