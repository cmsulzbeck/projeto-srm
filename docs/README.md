# Documentação do Projeto

## Diagrama ER

O diagrama ER do projeto foi criado usando PlantUML. Para visualizar ou gerar o diagrama, siga as instruções abaixo:

### Pré-requisitos
- Java JDK instalado
- Graphviz instalado
- PlantUML instalado

### Como gerar o diagrama

1. Instale o PlantUML:
```bash
# Windows (usando Chocolatey)
choco install plantuml

# Linux
sudo apt-get install plantuml

# macOS
brew install plantuml
```

2. Instale o Graphviz:
```bash
# Windows (usando Chocolatey)
choco install graphviz

# Linux
sudo apt-get install graphviz

# macOS
brew install graphviz
```

3. Gere o diagrama:
```bash
plantuml er-diagram.puml
```

O arquivo `er-diagram.png` será gerado no mesmo diretório.

### Estrutura do Diagrama

O diagrama ER representa as seguintes entidades:

1. **MOEDA**
   - Armazena informações sobre as moedas do sistema
   - Campos: id, nome, codigo, taxa_cambio, data_atualizacao

2. **PRODUTO**
   - Representa os produtos disponíveis para transação
   - Campos: id, nome, descricao, preco, quantidade_estoque, moeda_id

3. **REINO**
   - Representa os reinos do sistema
   - Campos: id, nome, descricao

4. **TRANSACAO**
   - Registra todas as transações realizadas
   - Campos: id, tipo, valor, data, descricao, produto_id, moeda_origem_id, moeda_destino_id, reino_id

5. **FORMULA_CONVERSAO**
   - Armazena fórmulas personalizadas de conversão por produto e reino
   - Campos: id, produto_id, reino_id, formula

6. **TAXA_CAMBIO_PRODUTO**
   - Mantém as taxas de câmbio específicas por produto
   - Campos: id, produto_id, moeda_id, taxa, data_atualizacao

### Relacionamentos

- Uma MOEDA pode ter vários PRODUTOS
- Uma MOEDA pode ser origem ou destino em várias TRANSACOES
- Um PRODUTO pode ter várias TRANSACOES
- Um REINO pode ter várias TRANSACOES
- Um REINO pode ter várias FORMULAS_CONVERSAO
- Um PRODUTO pode ter várias FORMULAS_CONVERSAO
- Um PRODUTO pode ter várias TAXAS_CAMBIO_PRODUTO
- Uma MOEDA pode ter várias TAXAS_CAMBIO_PRODUTO 