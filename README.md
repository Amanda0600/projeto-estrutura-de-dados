# Projeto de Estrutura de Dados — Fila de Atendimento de Clínica de Estética

**Aluna:** Amanda Oiveira
**Disciplina:** Estrutura de Dados
**Tema:** Sistema de fila de atendimento de uma clínica de estética

## Visão geral

Repositório com as implementações do projeto em duas linguagens:

| Pasta | Conteúdo | Situação |
|---|---|---|
| [`java/`](java/README.md) | Sistema de console completo: Array, Enum e Pilha integrados em um menu. 

## Estrutura

```
projeto-estrutura-dados/
├── java/
│   ├── src/
│   │   ├── Main.java
│   │   ├── modelos/
│   │   ├── estruturas/   (Pilha, Fila, Lista)
│   │   └── util/
│   └── README.md
├── c/
│   ├── src/ (main.c, ponteiros.c, alocacao_dinamica.c, unioes.c)
│   └── README.md
└── README.md
```

## Como executar a versão Java

```bash
cd java
mkdir -p out
javac -encoding UTF-8 -d out src/Main.java src/modelos/*.java src/estruturas/*.java src/util/*.java
java -cp out Main
```
