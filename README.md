# Processo Seletivo - PSS 02/2025/SEPLAG (Analista de TI - Perfil Junior, Pleno e Sênior)

* Inscrição: 8697
* Nome: GABRIEL JOSE CURVO HONDA

## Configuração do Ambiente

Para executar o projeto, é necessário ter o [Docker](https://docs.docker.com/engine/install/) e o [Docker Compose](https://docs.docker.com/compose/install/) devidamente instalados e configurados na 
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

