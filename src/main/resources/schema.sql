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
    quantidade_estoque INTEGER NOT NULL
);

-- Criação da tabela transacoes
CREATE TABLE IF NOT EXISTS TRANSACOES (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_total DECIMAL(19,2) NOT NULL,
    data_transacao TIMESTAMP NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES PRODUTOS(id)
); 