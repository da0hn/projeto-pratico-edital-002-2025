# Processo Seletivo - PSS 02/2025/SEPLAG (Analista de TI - Perfil Junior, Pleno e Sênior)

* Inscrição: 8697
* Nome: Gabriel José Curvo Honda
* Perfil: Desenvolvedor Java Pleno

## Considerações sobre a implementação

* A aplicação foi desenvolvida utilizando o Java 17, Spring Boot 3.4.4 e última versão disponível do PostgreSQL.
* A estrutura do banco de dados foi criada utilizando o Flyway, que é uma ferramenta de migração de banco de dados.
* A aplicação foi desenvolvida utilizando o padrão MVC porém a organização dos pacotes segue os conceitos da Arquitetura Hexagonal.
* A autenticação foi implementada utilizando o Spring Security com JWT.
  * Ao subir a aplicação será criado um usuário default para que seja possível chamar os outros endpoints ou criar novos usuários. 
  * Username: `administrador@admin.com.br`, Senha: `12345678`
* A documentação da API foi gerada utilizando o [Swagger](http://localhost:8080/swagger-ui/index.html).
* A aplicação possui dois arquivos de configuração do `docker compose`.
  * O `dev.yaml` deve ser utilizado para desenvolvimento local e ele será utilizado pelo `Spring Boot` quando a variável de ambiente `DOCKER_COMPOSE_ENABLED` for `true`.
  * O `compose.yaml` deve ser utilizado para validar o que foi implementado, ele cria a imagem do container da api 
    `sistema-gestao-institucional:latest` quando o parâmetro `--build` é informado no comando `docker-compose up`.
* Os volumes devem ser criados antes de subir a aplicação, na seção abaixo `Configuração do Ambiente` estão os comandos para criar os volumes.
* Foi utilizado a estratégia de `multi-stage build` para que não seja necessário instalar as dependências do `Maven` e `Java` na máquina local.

## Requisitos

Os requisitos abaixo devem ser implementados em uma aplicação web e estão disponíveis no edital.

### Requisitos Gerais

* Implementar mecanismo de autorização e autenticação, bem como não
  permitir acesso ao endpoint a partir de domínios diversos do qual estará
  hospedado o serviço;
* A solução de autenticação deverá expirar a cada 5 minutos e oferecer a
  possibilidade de renovação do período;
* Implementar pelo menos os verbos post, put, get;
* Conter recursos de paginação em todas as consultas;
* Os dados produzidos deverão ser armazenados no servidor de banco
  de dados previamente criado em container;
* Orquestrar a solução final utilizando Docker Compose de modo que
  inclua todos os contêineres utilizados.

### Requisitos Específicos

* Criar um CRUD para Servidor Efetivo, Servidor Temporário, Unidade e
  Lotação. Deverá ser contemplado a inclusão e edição dos dados das
  tabelas relacionadas;
* Criar um endpoint que permita consultar os servidores efetivos lotados
  em determinada unidade parametrizando a consulta pelo atributo unid_id;
  Retornar os seguintes campos: Nome, idade, unidade de lotação e fotografia;
* Criar um endpoint que permita consultar o endereço funcional (da unidade
  onde o servidor é lotado) a partir de uma parte do nome do servidor efetivo;
* Realizar o upload de uma ou mais fotografias enviando-as para o Min.IO;
* A recuperação das imagens deverá ser através de links temporários gerados pela biblioteca do Min.IO com tempo de expiração de 5 minutos.

### Definição do banco de dados

![Diagrama do banco de dados](.github/diagrama-db.png)

## Configuração do Ambiente

Para executar o projeto, é necessário ter o [Docker](https://docs.docker.com/engine/install/) e
o [Docker Compose](https://docs.docker.com/compose/install/) devidamente instalados e configurados na
máquina.
O projeto utiliza o Docker para criar um ambiente isolado e o `Docker Compose` para orquestrar os serviços necessários.

É necessário criar os volumes para o banco de dados PostgreSQL e o MinIO, que são utilizados pela aplicação abaixo está o comando para criar os
volumes:

* Criar o volume para o container do PostgreSQL:

```docker
 docker volume create --name=sistema-gestao-institucional-db-volume --driver local --opt type=none --opt device=/CAMINHO_PARA_TEU_VOLUME/database --opt o=bind
```

* Criar o volume para o container do MinIO:

```docker
 docker volume create --name=sistema-gestao-institucional-minio-data --driver local --opt type=none --opt device=/CAMINHO_PARA_TEU_VOLUME/minio --opt o=bind
```

* Criar o volume para o container do pgadmin:

```docker
 docker volume create --name=sistema-gestao-institucional-pgadmin-data --driver local --opt type=none --opt device=/CAMINHO_PARA_TEU_VOLUME/pgadmin --opt o=bind
```

* Para executar a aplicação com o Docker Compose, execute o seguinte comando na raiz do projeto:

```docker
docker-compose up -d --build
```

Ele irá baixar as imagens necessárias, criar os containers e iniciar a aplicação em segundo plano.

## Tabela de aplicações, URLs e Credenciais

|           Aplicação            |                               URL                               | Porta Externa |              Usuário              |           Senha           |
|:------------------------------:|:---------------------------------------------------------------:|:-------------:|:---------------------------------:|:-------------------------:|
|           PostgreSQL           | `jdbc:postgresql://localhost:5482/sistema_gestao_institucional` |     5482      | user_sistema_gestao_institucional |         12345678          |
|          MinIO Client          |                     `http://localhost:9001`                     |     9001      |      minioadmin (accessKey)       | minioadmin123 (secretKey) |
|            PgAdmin             |                     `http://localhost:8888`                     |     8888      |    administrador@admin.com.br     |         12345678          |
| Endpoint para geração do token |              `http://localhost:8080/v1/auth/token`              |     8080      |    administrador@admin.com.br     |         12345678          |
| Endpoint para refresh do token |          `http://localhost:8080/v1/auth/refresh-token`          |     8080      |                 -                 |             -             |
|            Swagger             |          `http://localhost:8080/swagger-ui/index.html`          |     8080      |                 -                 |             -             |

## Tabela de variáveis de ambiente

|  Nome da variável de ambiente  |                                                     Descrição                                                      |                           Valor padrão                           |
|:------------------------------:|:------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------:|
|     DOCKER_COMPOSE_ENABLED     | Habilita o uso do Docker Compose para executar a aplicação. Se `true`, o Docker Compose (dev.yaml) será utilizado. |                              false                               |
|            DB_HOST             | Endereço do host do banco de dados PostgreSQL, por padrão irá apontar para o container criado pelo docker compose  |                             database                             |
|            DB_PORT             |       Porta do banco de dados PostgreSQL, por padrão irá apontar para o container criado pelo docker compose       |                               5432                               |
|          DB_USERNAME           |                       Usuário do banco de dados PostgreSQL que será utilizado pela aplicação                       |                user_sistema_gestao_institucional                 |
|          DB_PASSWORD           |                        Senha do banco de dados PostgreSQL que será utilizado pela aplicação                        |                             12345678                             |
|           MINIO_HOST           |           Endereço do host do MinIO, por padrão irá apontar para o container criado pelo docker compose            |                              minio                               |
|           MINIO_PORT           |                 Porta do MinIO, por padrão irá apontar para o container criado pelo docker compose                 |                               9000                               |
|        MINIO_ACCESS_KEY        |                                              Chave de acesso do MinIO                                              |                            minioadmin                            |
|        MINIO_SECRET_KEY        |                                               Chave secreta do MinIO                                               |                          minioadmin123                           |
|       MINIO_BUCKET_NAME        |                                    Nome do bucket que será utilizado pelo MinIO                                    |                   sistema-gestao-institucional                   |
| MINIO_PRESIGNED_URL_EXPIRATION |                                    Tempo de expiração da url gerada pelo MinIO                                     |                          5m (5 minutos)                          |
|         JWT_SECRET_KEY         |                                     Chave secreta utilizada para assinar o JWT                                     | 6f27a8212e780877821336520f8ba1baa189f4ab8cd3f30a0e2c84f0e6bfecb7 |
|         JWT_EXPIRATION         |                                          Tempo de expiração do JWT gerado                                          |                        300000 (5 minutos)                        |
|     JWT_REFRESH_EXPIRATION     |                                        Tempo de expiração do refresh token                                         |                         3600000 (1 hora)                         |
