# Sistema de Conversão de Moedas - Reino SRM

Este é um sistema de conversão de moedas desenvolvido para o reino de SRM, permitindo a conversão entre Ouro Real e Tibar, considerando diferentes produtos e suas taxas específicas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- OpenAPI (Swagger)
- Maven
- MapStruct
- Lombok

## Padrões de Projeto Utilizados

- Factory Pattern: Para criação consistente de objetos
- Strategy Pattern: Para diferentes estratégias de conversão
- Builder Pattern: Para construção de objetos complexos
- Service Locator Pattern: Para gerenciamento de estratégias
- Mapper Pattern: Para conversão entre DTOs e entidades

## Configuração do Ambiente

1. Clone o repositório
2. Certifique-se de ter o Java 17 instalado
3. Execute o projeto usando Maven:
   ```bash
   mvn spring-boot:run
   ```

## Acesso à API

A API estará disponível em: `http://localhost:8080`

### Documentação Swagger

A documentação da API pode ser acessada em:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Endpoints Principais

### Moedas

- `POST /api/moedas` - Criar uma nova moeda
- `PUT /api/moedas/{codigo}/taxa` - Atualizar taxa de câmbio
- `GET /api/moedas/{codigo}` - Buscar moeda por código
- `GET /api/moedas` - Listar todas as moedas

### Conversões

- `POST /api/conversoes` - Realizar conversão de moeda

## Script SQL para Criação das Tabelas

```sql
CREATE TABLE moeda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(10) NOT NULL UNIQUE,
    taxa_cambio DECIMAL(10,4) NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);

CREATE TABLE produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    preco_base DECIMAL(10,2) NOT NULL,
    fator_conversao DECIMAL(10,4) NOT NULL,
    tipo_conversao VARCHAR(50) NOT NULL,
    moeda_id BIGINT NOT NULL,
    FOREIGN KEY (moeda_id) REFERENCES moeda(id)
);

CREATE TABLE transacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    valor_original DECIMAL(10,2) NOT NULL,
    valor_convertido DECIMAL(10,2) NOT NULL,
    moeda_origem_id BIGINT NOT NULL,
    moeda_destino_id BIGINT NOT NULL,
    taxa_cambio_aplicada DECIMAL(10,4) NOT NULL,
    data_transacao TIMESTAMP NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (moeda_origem_id) REFERENCES moeda(id),
    FOREIGN KEY (moeda_destino_id) REFERENCES moeda(id)
);
```

## Exemplo de Uso

1. Criar moedas:
```json
POST /api/moedas
{
    "nome": "Ouro Real",
    "codigo": "OR",
    "taxaCambio": 1.0000
}
```

2. Atualizar taxa de câmbio:
```
PUT /api/moedas/OR/taxa?novaTaxa=2.5000
```

3. Realizar conversão:
```json
POST /api/conversoes
{
    "moedaOrigem": "OR",
    "moedaDestino": "TB",
    "valorOriginal": 100.00,
    "produto": "Peles"
}
```

## Considerações

- A taxa de câmbio entre Ouro Real e Tibar é de 1:2.5 por padrão
- Cada produto pode ter um fator de conversão específico
- Todas as transações são registradas para histórico
- O sistema utiliza transações para garantir a consistência dos dados
- Diferentes estratégias de conversão podem ser aplicadas baseadas no tipo do produto 