# Processo Seletivo - PSS 02/2025/SEPLAG (Analista de TI - Perfil Junior, Pleno e Sênior)

* Inscrição: 8697
* Nome: Gabriel José Curvo Honda
* Perfil: Desenvolvedor Java Pleno

## Considerações sobre a implementação

* A aplicação foi desenvolvida utilizando o Java 17, Spring Boot 3.4.4 e última versão disponível do PostgreSQL.
* A estrutura do banco de dados foi criada utilizando o Flyway, que é uma ferramenta de migração de banco de dados.
* A aplicação foi desenvolvida utilizando o padrão MVC porém a organização dos pacotes segue os conceitos da Arquitetura Hexagonal.
* A autenticação foi implementada utilizando o Spring Security com JWT.
* A documentação da API foi gerada utilizando o Swagger.
* A aplicação possui dois arquivos de configuração do `docker compose`.
  * O `dev.yaml` deve ser utilizado para desenvolvimento local e ele será utilizado pelo `Spring Boot` quando a variável de ambiente `DOCKER_COMPOSE_ENABLED` for `true`.
  * O `compose.yaml` deve ser utilizado para validar o que foi implementado, ele cria a imagem do container da api 
    `sistema-gestao-institucional:latest` quando o parâmetro `--build` é informado no comando `docker-compose up`.
* 

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

|           Aplicação            |                               URL                               | Porta Externa |              Usuário              |           Senha           |
|:------------------------------:|:---------------------------------------------------------------:|:-------------:|:---------------------------------:|:-------------------------:|
|           PostgreSQL           | `jdbc:postgresql://localhost:5482/sistema_gestao_institucional` |     5482      | user_sistema_gestao_institucional |         12345678          |
|          MinIO Client          |                     `http://localhost:9001`                     |     9001      |      minioadmin (accessKey)       | minioadmin123 (secretKey) |
|            PgAdmin             |                     `http://localhost:8888`                     |     8888      |    administrador@admin.com.br     |         12345678          |
| Endpoint para geração do token |              `http://localhost:8080/v1/auth/token`              |     8080      |    administrador@admin.com.br     |         12345678          |
| Endpoint para refresh do token |          `http://localhost:8080/v1/auth/refresh-token`          |     8080      |                 -                 |             -             |
|            Swagger             |          `http://localhost:8080/swagger-ui/index.html`          |     8080      |                 -                 |             -             |

