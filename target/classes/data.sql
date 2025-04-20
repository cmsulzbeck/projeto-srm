-- Inserção de moedas
INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Ouro Real', 'OR', 1.0, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'OR');

INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Tibar', 'TB', 0.5, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'TB');

INSERT INTO MOEDA (nome, codigo, taxa_cambio, data_atualizacao)
SELECT 'Real Brasileiro', 'BRL', 0.1, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM MOEDA WHERE codigo = 'BRL');

-- Inserção de reinos
INSERT INTO REINOS (nome, descricao)
SELECT 'Reino do Norte', 'Reino frio e montanhoso'
WHERE NOT EXISTS (SELECT 1 FROM REINOS WHERE nome = 'Reino do Norte');

INSERT INTO REINOS (nome, descricao)
SELECT 'Reino do Sul', 'Reino quente e desértico'
WHERE NOT EXISTS (SELECT 1 FROM REINOS WHERE nome = 'Reino do Sul');

INSERT INTO REINOS (nome, descricao)
SELECT 'Reino do Leste', 'Reino de florestas e rios'
WHERE NOT EXISTS (SELECT 1 FROM REINOS WHERE nome = 'Reino do Leste');

INSERT INTO REINOS (nome, descricao)
SELECT 'Reino do Oeste', 'Reino de planícies e campos'
WHERE NOT EXISTS (SELECT 1 FROM REINOS WHERE nome = 'Reino do Oeste');

INSERT INTO REINOS (nome, descricao)
SELECT 'Reino Central', 'Reino principal e mais rico'
WHERE NOT EXISTS (SELECT 1 FROM REINOS WHERE nome = 'Reino Central');

-- Inserção de produtos
INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque, moeda_id, reino_id)
SELECT 'Espada Longa', 'Espada de aço de alta qualidade', 100.00, 10, 
       (SELECT id FROM MOEDA WHERE codigo = 'OR'),
       (SELECT id FROM REINOS WHERE nome = 'Reino do Norte')
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Espada Longa');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque, moeda_id, reino_id)
SELECT 'Escudo de Madeira', 'Escudo resistente de carvalho', 50.00, 20,
       (SELECT id FROM MOEDA WHERE codigo = 'OR'),
       (SELECT id FROM REINOS WHERE nome = 'Reino do Sul')
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Escudo de Madeira');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque, moeda_id, reino_id)
SELECT 'Arco Longo', 'Arco de caça de alta precisão', 75.00, 15,
       (SELECT id FROM MOEDA WHERE codigo = 'OR'),
       (SELECT id FROM REINOS WHERE nome = 'Reino do Leste')
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Arco Longo');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque, moeda_id, reino_id)
SELECT 'Poção de Cura', 'Poção que restaura 50 pontos de vida', 25.00, 30,
       (SELECT id FROM MOEDA WHERE codigo = 'OR'),
       (SELECT id FROM REINOS WHERE nome = 'Reino do Oeste')
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Poção de Cura');

INSERT INTO PRODUTOS (nome, descricao, preco, quantidade_estoque, moeda_id, reino_id)
SELECT 'Anel Mágico', 'Anel que aumenta a força do usuário', 200.00, 5,
       (SELECT id FROM MOEDA WHERE codigo = 'OR'),
       (SELECT id FROM REINOS WHERE nome = 'Reino Central')
WHERE NOT EXISTS (SELECT 1 FROM PRODUTOS WHERE nome = 'Anel Mágico');

-- Inserção de taxas de câmbio para produtos
INSERT INTO TAXAS_CAMBIO_PRODUTO (produto_id, moeda_origem_id, moeda_destino_id, taxa, data_atualizacao)
SELECT 
    (SELECT id FROM PRODUTOS WHERE nome = 'Espada Longa'),
    (SELECT id FROM MOEDA WHERE codigo = 'OR'),
    (SELECT id FROM MOEDA WHERE codigo = 'TB'),
    0.5,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM TAXAS_CAMBIO_PRODUTO 
    WHERE produto_id = (SELECT id FROM PRODUTOS WHERE nome = 'Espada Longa')
    AND moeda_origem_id = (SELECT id FROM MOEDA WHERE codigo = 'OR')
    AND moeda_destino_id = (SELECT id FROM MOEDA WHERE codigo = 'TB')
);

INSERT INTO TAXAS_CAMBIO_PRODUTO (produto_id, moeda_origem_id, moeda_destino_id, taxa, data_atualizacao)
SELECT 
    (SELECT id FROM PRODUTOS WHERE nome = 'Escudo de Madeira'),
    (SELECT id FROM MOEDA WHERE codigo = 'OR'),
    (SELECT id FROM MOEDA WHERE codigo = 'TB'),
    0.4,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM TAXAS_CAMBIO_PRODUTO 
    WHERE produto_id = (SELECT id FROM PRODUTOS WHERE nome = 'Escudo de Madeira')
    AND moeda_origem_id = (SELECT id FROM MOEDA WHERE codigo = 'OR')
    AND moeda_destino_id = (SELECT id FROM MOEDA WHERE codigo = 'TB')
);

-- Inserção de fórmulas de conversão
INSERT INTO FORMULAS_CONVERSAO (produto_id, reino_id, formula)
SELECT 
    (SELECT id FROM PRODUTOS WHERE nome = 'Espada Longa'),
    (SELECT id FROM REINOS WHERE nome = 'Reino do Norte'),
    'preco * 1.2'
WHERE NOT EXISTS (
    SELECT 1 FROM FORMULAS_CONVERSAO 
    WHERE produto_id = (SELECT id FROM PRODUTOS WHERE nome = 'Espada Longa')
    AND reino_id = (SELECT id FROM REINOS WHERE nome = 'Reino do Norte')
);

INSERT INTO FORMULAS_CONVERSAO (produto_id, reino_id, formula)
SELECT 
    (SELECT id FROM PRODUTOS WHERE nome = 'Escudo de Madeira'),
    (SELECT id FROM REINOS WHERE nome = 'Reino do Sul'),
    'preco * 1.1'
WHERE NOT EXISTS (
    SELECT 1 FROM FORMULAS_CONVERSAO 
    WHERE produto_id = (SELECT id FROM PRODUTOS WHERE nome = 'Escudo de Madeira')
    AND reino_id = (SELECT id FROM REINOS WHERE nome = 'Reino do Sul')
);

-- Inserção de transações
INSERT INTO TRANSACOES (tipo, valor, data, descricao, produto_id, moeda_origem_id, moeda_destino_id, reino_id)
SELECT 
    'COMPRA',
    100.00,
    CURRENT_TIMESTAMP,
    'Compra de Espada Longa',
    (SELECT id FROM PRODUTOS WHERE nome = 'Espada Longa'),
    (SELECT id FROM MOEDA WHERE codigo = 'OR'),
    (SELECT id FROM MOEDA WHERE codigo = 'OR'),
    (SELECT id FROM REINOS WHERE nome = 'Reino do Norte')
WHERE NOT EXISTS (
    SELECT 1 FROM TRANSACOES 
    WHERE produto_id = (SELECT id FROM PRODUTOS WHERE nome = 'Espada Longa')
    AND tipo = 'COMPRA'
    AND valor = 100.00
);

INSERT INTO TRANSACOES (tipo, valor, data, descricao, produto_id, moeda_origem_id, moeda_destino_id, reino_id)
SELECT 
    'VENDA',
    50.00,
    CURRENT_TIMESTAMP,
    'Venda de Escudo de Madeira',
    (SELECT id FROM PRODUTOS WHERE nome = 'Escudo de Madeira'),
    (SELECT id FROM MOEDA WHERE codigo = 'OR'),
    (SELECT id FROM MOEDA WHERE codigo = 'OR'),
    (SELECT id FROM REINOS WHERE nome = 'Reino do Sul')
WHERE NOT EXISTS (
    SELECT 1 FROM TRANSACOES 
    WHERE produto_id = (SELECT id FROM PRODUTOS WHERE nome = 'Escudo de Madeira')
    AND tipo = 'VENDA'
    AND valor = 50.00
); 