# Semana 11 — Testes de integração com Spring Boot

**Tecnologias:** Java 17, Maven, Spring Boot 3.3, Spring Data JPA, H2 (em memória), JUnit 5 e MockMvc.

Os métodos `TODO` das classes de teste foram completados. **Nenhum mock foi usado:** os testes sobem o contexto real do Spring (`@SpringBootTest`), e o `ProdutoService` e o `ProdutoRepository` são os componentes verdadeiros, gravando no banco H2.

## Como executar

```bash
cd semana11/exercicios_integracao_springboot
mvn test
```

## Casos de teste

### Exercício 1 — Service + Repository + H2 (`ProdutoServiceIntegrationTest`)

| Caso | Método | Entrada | Verificação |
|---|---|---|---|
| CT01 — Cadastro válido | `deveCadastrarERecuperarProduto` | `Teclado`, `150.0` | O produto recebe ID e, buscado com `repository.findById()`, tem nome e preço corretos |
| CT02 — Nome inválido | `naoDevePersistirProdutoComNomeEmBranco` | nome `"   "`, `80.0` | `IllegalArgumentException("Nome obrigatório")` e `repository.count()` não muda |
| CT03 — Atualização (desafio) | `deveAtualizarPrecoPersistido` | `Mouse` por `80.0`, atualizado para `95.0` | A consulta posterior no repositório retorna `95.0` |
| CT06 — Preço inválido (extra) | `naoDevePersistirProdutoComPrecoInvalido` | preço `0.0` e `-10.0` | `IllegalArgumentException` e nada é gravado |
| CT07 — Atualização inválida (extra) | `naoDeveAtualizarParaPrecoInvalido` | atualizar para `-5.0` | Exceção, e o preço gravado continua `80.0` |
| CT08 — ID inexistente (extra) | `deveFalharAoBuscarProdutoInexistente` | ID `999999` | `IllegalArgumentException("Produto não encontrado")` |

### Exercício 2 — HTTP com MockMvc (`ProdutoControllerIntegrationTest`)

| Caso | Método | Requisição | Verificação |
|---|---|---|---|
| CT04 — Cadastro via API | `deveCadastrarProdutoPelaApi` | `POST /produtos` com `{"nome":"Mouse","preco":80.0}` | HTTP 200; JSON com `id` numérico, `nome` = `Mouse` e `preco` = `80.0` |
| CT05 — Consulta via API | `deveBuscarProdutoPelaApi` | POST, extrair o `id` com `ObjectMapper`/`JsonNode`, depois `GET /produtos/{id}` | HTTP 200; o JSON da consulta tem o mesmo `id`, `nome` e `preco` do cadastro |
| CT09 — Gravação via API (extra) | `cadastroPelaApiDeveGravarNoBanco` | `POST /produtos` com `Teclado` | `repository.count()` aumenta 1 e o registro existe no banco |

## Evidência da execução

> Cole aqui a saída do `mvn test` (ou um print do terminal ou da IDE) depois de rodar no seu computador.

```
[INFO] Tests run: ..., Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Discussão

**Por que `repository.findById()` é uma evidência diferente de olhar só o objeto retornado pelo serviço?**
O objeto retornado por `service.cadastrar()` é apenas o que o serviço devolveu em memória. Ele poderia estar certo mesmo se nada tivesse sido gravado. Buscar de novo pelo repositório prova que o dado passou pela camada JPA e pode ser lido do banco com o ID gerado. Isso testa a integração entre Service, Repository, mapeamento da entidade e H2, e não só a lógica do serviço.

**O que o `@Transactional` faz ao fim de cada teste?**
Cada método de teste roda dentro de uma transação, e o Spring faz *rollback* no final. Assim, o que um teste grava não aparece no seguinte, e os testes ficam independentes entre si e podem rodar em qualquer ordem. É por isso que o CT02 e o CT09 podem confiar no `count()` medido no início do próprio teste.

**Quais componentes são exercitados no Exercício 2?**
O fluxo completo da aplicação: o `DispatcherServlet` do Spring MVC, a conversão de JSON (Jackson), o `ProdutoController`, o `ProdutoService` com as validações, o `ProdutoRepository` (Spring Data JPA/Hibernate) e o banco H2.

**Por que o MockMvc não precisa de um servidor HTTP real?**
O MockMvc monta a requisição e a entrega direto ao `DispatcherServlet`, dentro da mesma JVM e da mesma thread do teste, sem abrir porta nem usar rede. Os filtros, o roteamento, a serialização e o controller funcionam igual a uma requisição real. O que fica de fora é só o servidor (Tomcat) e a camada de rede.

**Qual é a diferença para um teste unitário do controller com dependências simuladas?**
Em um teste unitário (por exemplo, `@WebMvcTest` com `@MockBean ProdutoService`), o serviço seria falso e devolveria o que o teste mandasse. Esse teste só verificaria o mapeamento HTTP do controller. Aqui nada é simulado: se a validação do serviço, o mapeamento JPA ou o banco tiverem problema, o teste falha. Ele é mais lento, mas dá confiança de que as camadas funcionam juntas.

## Colaboradora

| Nome | Curso |
|---|---|
| Emanuelly | Engenharia de Software — UniCesumar (6º semestre) |
