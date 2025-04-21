# Sistema de Reinos Medievais (SRM)

## Descrição
Sistema para gerenciamento de transações comerciais entre reinos medievais, com suporte a múltiplas moedas e produtos.

## Funcionalidades

### Conversão de Moedas
- Conversão entre Ouro Real (OR) e Tibar (TB)
- Taxas de câmbio específicas por produto
- Cache de taxas de câmbio para melhor performance
- Validações robustas para taxas de câmbio

### Produtos
- Cadastro e gerenciamento de produtos
- Taxas de conversão específicas por produto
- Fórmulas de conversão personalizadas por reino

### Transações
- Registro de todas as transações comerciais
- Histórico completo com filtros avançados
- Consulta por produto, reino, moeda e período
- Persistência resiliente com transações atômicas

### API RESTful
- Documentação completa com Swagger/OpenAPI
- Endpoints para:
  - Consulta de taxas de câmbio
  - Conversão de moedas
  - Conversão de produtos
  - Registro de transações
  - Consulta de histórico

## Tecnologias
- Spring Boot
- Spring Data JPA
- H2 Database
- Swagger/OpenAPI
- Caffeine Cache

## Configuração
1. Clone o repositório
2. Execute `mvn clean install`
3. Execute `mvn spring-boot:run`
4. Acesse a documentação da API em `http://localhost:8080/swagger-ui.html`

## Banco de Dados
O sistema utiliza H2 Database em memória com inicialização automática de dados.
- Console H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: `sa`
- Senha: `password`

### Script de Criação das Tabelas
```sql
-- Tabela de Moedas
CREATE TABLE moeda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(3) NOT NULL,
    taxa_cambio DECIMAL(19,4) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);

-- Tabela de Reinos
CREATE TABLE reino (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

-- Tabela de Produtos
CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(19,4) NOT NULL,
    quantidade_estoque INT NOT NULL,
    moeda_id BIGINT NOT NULL,
    FOREIGN KEY (moeda_id) REFERENCES moeda(id)
);

-- Tabela de Transações
CREATE TABLE transacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    valor DECIMAL(19,4) NOT NULL,
    data TIMESTAMP NOT NULL,
    descricao VARCHAR(255),
    produto_id BIGINT NOT NULL,
    moeda_origem_id BIGINT NOT NULL,
    moeda_destino_id BIGINT NOT NULL,
    reino_id BIGINT NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (moeda_origem_id) REFERENCES moeda(id),
    FOREIGN KEY (moeda_destino_id) REFERENCES moeda(id),
    FOREIGN KEY (reino_id) REFERENCES reino(id)
);

-- Tabela de Fórmulas de Conversão
CREATE TABLE formula_conversao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    reino_id BIGINT NOT NULL,
    formula VARCHAR(255) NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (reino_id) REFERENCES reino(id)
);

-- Tabela de Taxas de Câmbio por Produto
CREATE TABLE taxa_cambio_produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    moeda_id BIGINT NOT NULL,
    taxa DECIMAL(19,4) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (moeda_id) REFERENCES moeda(id)
);

-- Índices para melhor performance
CREATE INDEX idx_moeda_codigo ON moeda(codigo);
CREATE INDEX idx_produto_moeda ON produto(moeda_id);
CREATE INDEX idx_transacao_produto ON transacao(produto_id);
CREATE INDEX idx_transacao_moeda_origem ON transacao(moeda_origem_id);
CREATE INDEX idx_transacao_moeda_destino ON transacao(moeda_destino_id);
CREATE INDEX idx_transacao_reino ON transacao(reino_id);
CREATE INDEX idx_formula_produto ON formula_conversao(produto_id);
CREATE INDEX idx_formula_reino ON formula_conversao(reino_id);
CREATE INDEX idx_taxa_produto ON taxa_cambio_produto(produto_id);
CREATE INDEX idx_taxa_moeda ON taxa_cambio_produto(moeda_id);
```

### Diagrama ER
O diagrama ER do banco de dados está disponível em `docs/er-diagram.png`. Para gerar o diagrama:

1. Usando Docker (recomendado):
```bash
cd docs
chmod +x generate-diagram-docker.sh
./generate-diagram-docker.sh
```

2. Localmente (requer PlantUML e Graphviz):
```bash
cd docs
plantuml er-diagram.puml
```

![Diagrama ER do Banco de Dados](docs/er-diagram.png)

## Cache
O sistema utiliza Caffeine Cache para melhorar a performance das consultas de taxas de câmbio:
- Cache de taxas por 1 hora
- Limite de 100 entradas
- Invalidação automática ao atualizar taxas

## Exemplos de Uso

### Consultar Taxa de Câmbio
```bash
curl -X GET "http://localhost:8080/api/conversoes/moedas?moedaOrigemId=1&moedaDestinoId=2&valor=100.00"
```

### Converter Produto
```bash
curl -X GET "http://localhost:8080/api/conversoes/produtos?produtoId=1&moedaOrigemId=1&moedaDestinoId=2&valor=100.00"
```

### Atualizar Taxa de Câmbio
```bash
curl -X POST "http://localhost:8080/api/conversoes/taxas?produtoId=1&moedaOrigemId=1&moedaDestinoId=2&taxa=2.5"
```

### Consultar Histórico de Transações
```bash
curl -X GET "http://localhost:8080/api/conversoes/transacoes/historico?produtoId=1&reinoId=1&dataInicio=2024-01-01T00:00:00&dataFim=2024-12-31T23:59:59"
```

### Criar Nova Transação
```bash
curl -X POST "http://localhost:8080/api/conversoes/transacoes" \
  -H "Content-Type: application/json" \
  -d '{
    "produtoId": 1,
    "reinoId": 1,
    "moedaOrigemId": 1,
    "moedaDestinoId": 2,
    "valor": 100.00
  }'
``` 