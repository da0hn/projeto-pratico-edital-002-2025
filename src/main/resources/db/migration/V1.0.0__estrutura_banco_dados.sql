CREATE TABLE IF NOT EXISTS cidade
(
    cid_id   SERIAL PRIMARY KEY,
    cid_nome VARCHAR(200) NOT NULL,
    cid_uf   CHAR(2)      NOT NULL
);

CREATE TABLE IF NOT EXISTS endereco
(
    end_id              SERIAL PRIMARY KEY,
    end_tipo_logradouro VARCHAR(50) NOT NULL,
    end_logradouro      VARCHAR(200) NOT NULL,
    end_numero          INT,
    end_bairro          VARCHAR(100),
    cid_id              INT          NOT NULL,
    CONSTRAINT fk_endereco_cidade FOREIGN KEY (cid_id) REFERENCES cidade (cid_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS pessoa
(
    pes_id              SERIAL PRIMARY KEY,
    pes_nome            VARCHAR(200) NOT NULL,
    pes_data_nascimento DATE,
    pes_sexo            VARCHAR(9),
    pes_mae             VARCHAR(200),
    pes_pai             VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS foto_pessoa
(
    fp_id     SERIAL PRIMARY KEY,
    pes_id    INT NOT NULL,
    fp_data   DATE,
    fp_bucket VARCHAR(50),
    fp_hash   VARCHAR(50),
    CONSTRAINT fk_foto_pessoa_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS pessoa_endereco
(
    pes_end_id SERIAL PRIMARY KEY,
    pes_id     INT NOT NULL,
    end_id     INT NOT NULL,
    CONSTRAINT fk_pessoa_endereco_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_pessoa_endereco_endereco FOREIGN KEY (end_id) REFERENCES endereco (end_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS servidor_temporario
(
    pes_id           INT NOT NULL,
    st_data_admissao DATE,
    st_data_demissao DATE,
    CONSTRAINT fk_servidor_temp_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS servidor_efetivo
(
    pes_id       INT NOT NULL,
    se_matricula VARCHAR(20),
    CONSTRAINT fk_servidor_efetivo_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS unidade
(
    unid_id    SERIAL PRIMARY KEY,
    unid_nome  VARCHAR(200),
    unid_sigla VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS lotacao
(
    lot_id           SERIAL PRIMARY KEY,
    pes_id           INT  NOT NULL,
    unid_id          INT  NOT NULL,
    lot_data_lotacao DATE NOT NULL,
    lot_data_remocao DATE NOT NULL,
    lot_portaria     VARCHAR(100),
    CONSTRAINT fk_lotacao_pessoa FOREIGN KEY (pes_id) REFERENCES pessoa (pes_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_lotacao_unidade FOREIGN KEY (unid_id) REFERENCES unidade (unid_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS unidade_endereco
(
    unid_end_id SERIAL PRIMARY KEY,
    unid_id     INT NOT NULL,
    end_id      INT NOT NULL,
    CONSTRAINT fk_unidade_endereco_unidade FOREIGN KEY (unid_id) REFERENCES unidade (unid_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_unidade_endereco_endereco FOREIGN KEY (end_id) REFERENCES endereco (end_id) ON DELETE CASCADE ON UPDATE CASCADE
);
