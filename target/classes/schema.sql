-- Criação da tabela reinos
CREATE TABLE IF NOT EXISTS REINOS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255)
);

-- Criação da tabela moeda
CREATE TABLE IF NOT EXISTS MOEDA (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(3) NOT NULL,
    taxa_cambio DECIMAL(19,2) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);

-- Criação da tabela produtos
CREATE TABLE IF NOT EXISTS PRODUTOS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(19,2) NOT NULL,
    quantidade_estoque INTEGER NOT NULL,
    moeda_id BIGINT NOT NULL,
    reino_id BIGINT NOT NULL,
    FOREIGN KEY (moeda_id) REFERENCES MOEDA(id),
    FOREIGN KEY (reino_id) REFERENCES REINOS(id)
);

-- Criação da tabela taxas_cambio_produto
CREATE TABLE IF NOT EXISTS TAXAS_CAMBIO_PRODUTO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    moeda_origem_id BIGINT NOT NULL,
    moeda_destino_id BIGINT NOT NULL,
    taxa DECIMAL(19,2) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES PRODUTOS(id),
    FOREIGN KEY (moeda_origem_id) REFERENCES MOEDA(id),
    FOREIGN KEY (moeda_destino_id) REFERENCES MOEDA(id)
);

-- Criação da tabela formulas_conversao
CREATE TABLE IF NOT EXISTS FORMULAS_CONVERSAO (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    reino_id BIGINT NOT NULL,
    formula VARCHAR(255) NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES PRODUTOS(id),
    FOREIGN KEY (reino_id) REFERENCES REINOS(id)
);

-- Criação da tabela transacoes
CREATE TABLE IF NOT EXISTS TRANSACOES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(10) NOT NULL,
    valor DECIMAL(19,2) NOT NULL,
    data TIMESTAMP NOT NULL,
    descricao VARCHAR(255),
    produto_id BIGINT NOT NULL,
    moeda_origem_id BIGINT NOT NULL,
    moeda_destino_id BIGINT NOT NULL,
    reino_id BIGINT NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES PRODUTOS(id),
    FOREIGN KEY (moeda_origem_id) REFERENCES MOEDA(id),
    FOREIGN KEY (moeda_destino_id) REFERENCES MOEDA(id),
    FOREIGN KEY (reino_id) REFERENCES REINOS(id)
); 