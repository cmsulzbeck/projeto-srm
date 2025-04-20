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