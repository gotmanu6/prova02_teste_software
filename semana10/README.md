# Semana 10 — TDD nos quatro projetos

São quatro projetos Maven (Java 17 + JUnit 5). Em cada um, a implementação foi construída guiada pelos testes, no ciclo **RED → GREEN → REFACTOR**, até todos os testes passarem com **cobertura total** de linhas e de desvios (*branches*).

| Projeto | Classe implementada | Testes fornecidos | Testes adicionados | Total |
|---|---|---|---|---|
| `bubblesort-tdd` | `BubbleSort.ordenar()` | 3 | 6 | 9 |
| `conta-bancaria-tdd-maven` | `ContaBancaria.depositar()` e `sacar()` | 4 | 9 | 13 |
| `estoque-tdd` | `Produto.adicionarEstoque()` e `retirarEstoque()` | 6 | 8 | 14 |
| `carrinho-compras-tdd` | `Carrinho` (classe inteira) | 0 | 18 | 18 |

Os testes fornecidos pelo professor **não foram alterados**. Os novos estão marcados nas classes de teste como "Testes adicionais".

## Como executar

Dentro da pasta de cada projeto:

```bash
mvn test     # roda os testes e gera o relatório de cobertura em target/site/jacoco/index.html
mvn verify   # roda os testes e falha se a cobertura de linhas ou de desvios for menor que 100%
```

O plugin **JaCoCo** foi adicionado aos quatro `pom.xml`. A regra `check` exige 100% (`COVEREDRATIO = 1.00`) para `LINE` e `BRANCH`.

## Ciclos de TDD

### 1. Bubble Sort

| Ciclo | Teste (RED) | Implementação (GREEN) | Refatoração |
|---|---|---|---|
| 1 | `deveOrdenarVetorDesordenado` falha, porque `ordenar()` está vazio | Dois laços aninhados que trocam vizinhos fora de ordem | A troca foi extraída para o método `trocar()` |
| 2 | `deveManterVetorJaOrdenado` | Já passava | Parada antecipada: se uma passada não faz nenhuma troca, o método termina |
| 3 | `deveOrdenarVetorComValoresRepetidos` | Já passava, porque a comparação `>` (estrita) não troca valores iguais | — |
| 4 | Casos de borda: vetor decrescente, 2 elementos, negativos, 1 elemento, vazio, todos iguais | Passaram sem mudança | O laço externo só vai até `n - 1`, por isso o vetor vazio não dá erro |

### 2. Conta bancária

| Ciclo | Teste (RED) | Implementação (GREEN) |
|---|---|---|
| 1 | `deveDepositarValorNaConta` | `saldo += valor` |
| 2 | `deveAcumularDepositos` | Já passava |
| 3 | `deveSacarValorDaConta` | `saldo -= valor` |
| 4 | `naoDevePermitirSaqueMaiorQueSaldo` | `if (valor > saldo) return` |
| 5 | Depósito negativo ou zero | `if (valor <= 0) return false` |
| 6 | Saque negativo ou zero, saque de todo o saldo, saque com conta zerada | Condição `valor <= 0 \|\| valor > saldo` |

**Refatoração:** `depositar` e `sacar` passaram a devolver `boolean`, informando se a operação aconteceu. Os testes originais continuam compilando, porque eles ignoram o retorno. Operações inválidas são **ignoradas sem lançar exceção**, porque o teste fornecido `naoDevePermitirSaqueMaiorQueSaldo` espera que o saldo continue igual e não captura exceção.

### 3. Estoque

| Requisito | Teste | Implementação |
|---|---|---|
| R1 — Estoque inicial zero | `produtoDeveIniciarComEstoqueZero` | Já passava (construtor) |
| R2 — Adicionar | `deveAdicionarProdutoAoEstoque` | `this.quantidade += quantidade` |
| R3 — Acumular | `deveAcumularQuantidadeEmEstoque` | Já passava |
| R4 — Retirar | `deveRetirarProdutoDoEstoque` | `this.quantidade -= quantidade` |
| R5 — Sem estoque negativo | `naoDeveRetirarQuantidadeMaiorQueEstoque` | `if (quantidade > this.quantidade) return false` |
| R6 — Sem entrada negativa ou zero | `naoDeveAdicionarQuantidadeNegativa`, `naoDeveAdicionarQuantidadeZero` | `if (quantidade <= 0) return false` |
| Extras | Retirada negativa ou zero, retirar tudo, estoque vazio, `getNome()` | `quantidade <= 0 \|\| quantidade > this.quantidade` |

### 4. Carrinho de compras

| Requisito | Testes | Implementação |
|---|---|---|
| R1 — Carrinho vazio | `carrinhoVazioDeveTerTotalZero` | `getTotal()` retornando a soma de uma lista vazia |
| R2 — Adicionar um produto | `deveAdicionarUmProduto` | `adicionar(Produto)` guarda em uma `List<Produto>` |
| R3 — Vários produtos | `deveSomarVariosProdutos`, `deveAceitarProdutoGratuito` | `getSubtotal()` soma os preços |
| R4 — Remover | `deveRemoverProduto`, `removerProdutoInexistenteNaoAlteraOCarrinho`, `removerDeCarrinhoVazioRetornaFalso`, `removerProdutoRepetidoRemoveApenasUmaUnidade` | `remover(String nome)` remove a primeira ocorrência e devolve `boolean` |
| R5 — Preço inválido | `naoDeveAdicionarProdutoComPrecoNegativo`, `naoDeveAdicionarProdutoNulo` | **Decisão:** lançar `IllegalArgumentException`, para o erro não passar despercebido |
| R6 — Desconto de 10% a partir de R$ 500,00 | `deveAplicarDescontoDe10PorCentoAcimaDe500`, fronteiras **R$ 499,99 / 500,00 / 500,01**, soma de produtos, desconto que some ao remover | `getDesconto()` com `subtotal >= 500` e `getTotal() = subtotal - desconto` |

**Refatoração:** o valor mínimo (`500.00`) e o percentual (`0.10`) viraram constantes. `getProdutos()` devolve uma lista que não pode ser modificada, para ninguém alterar o carrinho por fora (há um teste para isso).

## Colaboradora

| Nome | Curso |
|---|---|
| Emanuelly | Engenharia de Software — UniCesumar (6º semestre) |
