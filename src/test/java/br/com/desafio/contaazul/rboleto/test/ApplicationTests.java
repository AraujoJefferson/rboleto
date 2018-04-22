package br.com.desafio.contaazul.rboleto.test;


import br.com.desafio.contaazul.rboleto.Application;
import br.com.desafio.contaazul.rboleto.business.BoletoRepository;
import br.com.desafio.contaazul.rboleto.configuration.MensagemResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MensagemResource mensagemResource;

    @Autowired
    private BoletoRepository boletoRepository;

    @Before
    public void deleteAllBeforeTests() throws Exception {
        boletoRepository.deleteAll();
    }

    @Test
    public void cadastrarBoleto() throws Exception {
        mockMvc.perform(post("/rest/bankslips").contentType(MediaType.APPLICATION_JSON)
                .content("{\"due_date\":\"2018-04-23\"," +
                        "\"total_in_cents\":\"1000\"," +
                        "\"customer\":\"Jefferson P Araujo\"," +
                        "\"status\":\"PENDING\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(mensagemResource.getMensagem("boleto.cadastro.ok.201"))).andReturn();
    }

    @Test
    public void listarBoletos() throws Exception {
        mockMvc.perform(post("/rest/bankslips").contentType(MediaType.APPLICATION_JSON)
                .content("{\"due_date\":\"2018-04-24\"," +
                        "\"total_in_cents\":\"1000\"," +
                        "\"customer\":\"Jefferson P Araujo\"," +
                        "\"status\":\"PENDING\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rest/bankslips")).andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": \"2\",\n" +
                        "        \"due_date\": \"2018-04-24\",\n" +
                        "        \"total_in_cents\": \"1000\",\n" +
                        "        \"customer\": \"Jefferson P Araujo\",\n" +
                        "        \"status\": \"PENDING\"\n" +
                        "    }\n" +
                        "]"))
        ;
    }

    @Test
    public void verDetalhesBoleto() throws Exception {
        cadastrarBoleto();
        mockMvc.perform(get("/rest/bankslips/1")).andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":\"1\",\"due_date\":\"2018-04-23\",\"total_in_cents\":\"1000\"" +
                                ",\"customer\":\"Jefferson P Araujo\",\"fine\":\"50\",\"status\":\"PENDING\"}"));
    }

    @Test
    public void pagarBoletoExistente() throws Exception {
        mockMvc.perform(post("/rest/bankslips").contentType(MediaType.APPLICATION_JSON)
                .content("{\"due_date\":\"2018-04-24\"," +
                        "\"total_in_cents\":\"1000\"," +
                        "\"customer\":\"Jefferson P Araujo\"," +
                        "\"status\":\"PENDING\"}"))
                .andExpect(status().isCreated());
        mockMvc.perform(put("/rest/bankslips/1/pay"))
                .andExpect(status().isOk())
                .andExpect(content().string(mensagemResource.getMensagem("boleto.pagar.ok.200"))).andReturn();
    }

    @Test
    public void pagarBoletoNaoExistente() throws Exception {
        mockMvc.perform(put("/rest/bankslips/19/pay"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(mensagemResource.getMensagem("boleto.pagar.nao.existe.404"))).andReturn();
    }
}
