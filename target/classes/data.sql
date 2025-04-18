-- Inserindo moedas padrão (apenas se não existirem)
INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Dólar Americano', 'USD', 1.0000000000, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'USD');

INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Real Brasileiro', 'BRL', 5.2000000000, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'BRL');

INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Euro', 'EUR', 0.9200000000, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'EUR');

INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Libra Esterlina', 'GBP', 0.7900000000, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'GBP');

INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Iene Japonês', 'JPY', 151.5000000000, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'JPY');

-- Inserindo produtos (apenas se não existirem)
INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque)
SELECT 'Notebook Dell', 'Notebook Dell Inspiron 15', 4500.00, 10
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Notebook Dell');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque)
SELECT 'Smartphone Samsung', 'Galaxy S21', 3500.00, 15
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Smartphone Samsung');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque)
SELECT 'Monitor LG', 'Monitor 27 polegadas 4K', 2000.00, 8
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Monitor LG');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque)
SELECT 'Teclado Mecânico', 'Teclado RGB', 500.00, 20
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Teclado Mecânico');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque)
SELECT 'Mouse Gamer', 'Mouse com 6 botões', 300.00, 25
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Mouse Gamer');

-- Inserindo transações (apenas se não existirem)
INSERT INTO TRANSACOES (produto_id, quantidade, valor_total, data_transacao, tipo)
SELECT 1, 2, 9000.00, '2024-01-01 10:00:00', 'ENTRADA'
WHERE NOT EXISTS (SELECT 1 FROM TRANSACOES WHERE produto_id = 1 AND data_transacao = '2024-01-01 10:00:00');

INSERT INTO TRANSACOES (produto_id, quantidade, valor_total, data_transacao, tipo)
SELECT 2, 5, 17500.00, '2024-01-02 11:00:00', 'ENTRADA'
WHERE NOT EXISTS (SELECT 1 FROM TRANSACOES WHERE produto_id = 2 AND data_transacao = '2024-01-02 11:00:00');

INSERT INTO TRANSACOES (produto_id, quantidade, valor_total, data_transacao, tipo)
SELECT 1, 1, 4500.00, '2024-01-03 14:00:00', 'SAIDA'
WHERE NOT EXISTS (SELECT 1 FROM TRANSACOES WHERE produto_id = 1 AND data_transacao = '2024-01-03 14:00:00');

INSERT INTO TRANSACOES (produto_id, quantidade, valor_total, data_transacao, tipo)
SELECT 3, 3, 6000.00, '2024-01-04 09:00:00', 'ENTRADA'
WHERE NOT EXISTS (SELECT 1 FROM TRANSACOES WHERE produto_id = 3 AND data_transacao = '2024-01-04 09:00:00');

INSERT INTO TRANSACOES (produto_id, quantidade, valor_total, data_transacao, tipo)
SELECT 2, 2, 7000.00, '2024-01-05 16:00:00', 'SAIDA'
WHERE NOT EXISTS (SELECT 1 FROM TRANSACOES WHERE produto_id = 2 AND data_transacao = '2024-01-05 16:00:00'); 