#!/bin/bash

# Verifica se o PlantUML está instalado
if ! command -v plantuml &> /dev/null; then
    echo "PlantUML não está instalado. Por favor, instale-o primeiro."
    echo "Consulte a documentação em docs/README.md para instruções de instalação."
    exit 1
fi

# Verifica se o Graphviz está instalado
if ! command -v dot &> /dev/null; then
    echo "Graphviz não está instalado. Por favor, instale-o primeiro."
    echo "Consulte a documentação em docs/README.md para instruções de instalação."
    exit 1
fi

# Gera o diagrama
echo "Gerando diagrama ER..."
plantuml er-diagram.puml

if [ $? -eq 0 ]; then
    echo "Diagrama gerado com sucesso: er-diagram.png"
else
    echo "Erro ao gerar o diagrama."
    exit 1
fi 