package br.edu.exemplo;

import br.edu.exemplo.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Exercício 2 — Integração HTTP com MockMvc.
 * Fluxo exercitado: Controller → Service → Repository → H2, sem servidor HTTP real
 * e sem mocks dos componentes da aplicação.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProdutoControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProdutoRepository repository;

    @Test
    @DisplayName("CT04 - POST /produtos cadastra o produto e devolve o JSON com ID")
    void deveCadastrarProdutoPelaApi() throws Exception {
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Mouse\",\"preco\":80.0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Mouse"))
                .andExpect(jsonPath("$.preco").value(80.0));
    }

    @Test
    @DisplayName("CT05 - GET /produtos/{id} devolve o produto cadastrado via POST")
    void deveBuscarProdutoPelaApi() throws Exception {
        MvcResult resultado = mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Mouse\",\"preco\":80.0}"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode corpo = objectMapper.readTree(resultado.getResponse().getContentAsString());
        long id = corpo.get("id").asLong();

        MvcResult consulta = mockMvc.perform(get("/produtos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Mouse"))
                .andExpect(jsonPath("$.preco").value(80.0))
                .andReturn();

        JsonNode produto = objectMapper.readTree(consulta.getResponse().getContentAsString());
        assertEquals(id, produto.get("id").asLong(), "O GET deve devolver o mesmo ID criado no POST");
    }

    // ---------- Caso complementar ----------

    @Test
    @DisplayName("CT09 - O produto cadastrado pela API fica gravado no banco")
    void cadastroPelaApiDeveGravarNoBanco() throws Exception {
        long antes = repository.count();

        MvcResult resultado = mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Teclado\",\"preco\":150.0}"))
                .andExpect(status().isOk())
                .andReturn();

        long id = objectMapper.readTree(resultado.getResponse().getContentAsString()).get("id").asLong();

        assertEquals(antes + 1, repository.count());
        assertTrue(repository.findById(id).isPresent());
        assertEquals("Teclado", repository.findById(id).orElseThrow().getNome());
    }
}
