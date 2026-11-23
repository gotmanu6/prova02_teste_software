# Semana 14 — Teste de usuário final (Mercado Livre)

Enunciado: [TESTE USUARIO FINAL.pdf](https://github.com/JoaoChoma/teste_software_2026/blob/main/aulas/SEMANA14/TESTE%20USUARIO%20FINAL.pdf)

| Arquivo | Conteúdo |
|---|---|
| `README.md` | Plano do teste e relatório de resultados (este arquivo) |
| `planilha_observacao.csv` | Planilha para o avaliador preencher durante o teste (abre no Excel ou no Google Sheets) |
| `questionario_SUS.md` | As 10 perguntas do SUS e como calcular a nota |

---

## 1. Objetivo

Avaliar a facilidade de uso, a eficiência e a clareza da interface do **Mercado Livre** nas etapas de busca, comparação e simulação de compra de um produto, aplicando conceitos de IHC e de testes de usabilidade.

## 2. Cenário de uso

> Você está procurando um fone de ouvido sem fio para uso diário e quer comprar pelo Mercado Livre. Seu objetivo é encontrar um modelo com boa avaliação, comparar opções e simular a compra até o carrinho, **sem finalizar o pagamento**.

## 3. Tarefas

| Nº | Tarefa | Considerada concluída quando |
|---|---|---|
| T1 | Acessar https://www.mercadolivre.com.br | A página inicial carrega |
| T2 | Pesquisar "fone de ouvido Bluetooth" | Aparece a lista de resultados |
| T3a | Filtrar apenas produtos com entrega **Full** | O filtro Full aparece aplicado |
| T3b | Ordenar pelos **melhores avaliados** | A ordenação aparece aplicada |
| T4 | Escolher um produto e abrir os detalhes | A página do produto abre |
| T5 | Adicionar o item ao carrinho | O carrinho mostra o item |
| T6 | Simular o início da compra | A tela de entrega ou pagamento aparece (**parar aqui**) |
| T7 | Voltar à tela inicial | A página inicial aparece de novo |

## 4. Papéis

| Papel | Quem |
|---|---|
| Usuário final (executa as tarefas) | _nome_ |
| Avaliador (observa e anota tempo, erros e comentários) | _nome_ |
| Instrutor | Prof. João Choma Neto |

**Ferramentas:** navegador (_qual: Chrome / Firefox / Edge_), cronômetro do celular, `planilha_observacao.csv` e `questionario_SUS.md`.

## 5. Como conduzir

1. O avaliador lê o cenário (seção 2) para o usuário e não dá dicas durante o teste.
2. O usuário fala em voz alta o que está pensando (*think aloud*).
3. O avaliador marca o tempo de cada tarefa e anota cliques errados, hesitações e comentários na planilha.
4. Se o usuário travar por mais de 1 minuto numa tarefa, o avaliador pode ajudar. Nesse caso, a tarefa é marcada como **N** (sem sucesso sem ajuda).
5. No final, o usuário responde o questionário SUS.

## 6. Métricas

| Métrica | Como medir |
|---|---|
| Tempo de execução | Soma dos tempos das tarefas, em segundos |
| Taxa de sucesso | Usuários que concluíram o fluxo sem ajuda ÷ total de usuários |
| Erros cometidos | Cliques incorretos, perda do caminho, confusão nos filtros |
| Satisfação | Nota SUS, de 0 a 100 |

## 7. Critérios de sucesso

- 90% dos usuários conseguem adicionar o produto ao carrinho.
- Tempo médio para concluir o teste de até 4 minutos (240 s).

---

## 8. Resultados

> Preencha depois de executar o teste. Data: ___/___/2026

### 8.1 Planilha de observação

| Tarefa | Tempo (s) | Sucesso (S/N) | Erros | Observações |
|---|---|---|---|---|
| Acessar site | | | | |
| Buscar produto | | | | |
| Aplicar filtros (Full + melhores avaliados) | | | | |
| Abrir detalhes | | | | |
| Adicionar ao carrinho | | | | |
| Simular compra | | | | |
| Retornar à tela inicial | | | | |
| **Total** | | | | |

### 8.2 Resumo

| Métrica | Resultado | Critério | Atingiu? |
|---|---|---|---|
| Adicionou ao carrinho sem ajuda | ___ de ___ usuários (___%) | ≥ 90% | |
| Tempo total | ___ s | ≤ 240 s | |
| Total de erros | ___ | — | |
| Nota SUS | ___ / 100 | ≥ 68 (média de mercado) | |

### 8.3 Problemas de usabilidade encontrados

| # | Onde | Problema observado | Gravidade (baixa/média/alta) | Sugestão de melhoria |
|---|---|---|---|---|
| 1 | | | | |
| 2 | | | | |
| 3 | | | | |

*Pontos para observar com atenção:* onde fica o filtro **Full** (barra lateral, ou "Mais filtros" no celular), o nome exato da opção de ordenação (o menu "Ordenar por" tem opções como "Mais relevantes" e "Menor preço"; anote se "melhores avaliados" existe ou se o usuário precisou improvisar), anúncios patrocinados misturados aos resultados, exigência de login para adicionar ao carrinho, e pop-ups de CEP ou de cookies.

### 8.4 Conclusão

_Escreva de 3 a 5 linhas: o fluxo atingiu os critérios de sucesso? Quais foram os maiores obstáculos? O que você mudaria na interface?_

---

## Colaboradora

| Nome | Curso |
|---|---|
| Emanuelly | Engenharia de Software — UniCesumar (6º semestre) |
