package br.com.desafio.contaazul.rbankslip.test;


import br.com.desafio.contaazul.rbankslip.Application;
import br.com.desafio.contaazul.rbankslip.business.BankslipRepository;
import br.com.desafio.contaazul.rbankslip.configuration.MensageResource;
import br.com.desafio.contaazul.rbankslip.entity.Bankslip;
import br.com.desafio.contaazul.rbankslip.enumerate.BankslipStatusEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MensageResource mensageResource;

    @Autowired
    private BankslipRepository bankslipRepository;

    @Before
    public void deleteAllBeforeTests() throws Exception {
        bankslipRepository.deleteAll();
    }

    @Test
    public void cadastrarBoleto() throws Exception {
        mockMvc.perform(post("/rest/bankslips").contentType(MediaType.APPLICATION_JSON)
                .content("{\"due_date\":\"2018-04-23\"," +
                        "\"total_in_cents\":\"1000\"," +
                        "\"customer\":\"Jefferson P Araujo\"," +
                        "\"status\":\"PENDING\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(mensageResource.getMensagem("bankslips.save.ok.201"))).andReturn();
    }

    @Test
    public void listarBoletos() throws Exception {
        UUID id = geraUUID();

        mockMvc.perform(get("/rest/bankslips")).andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {" +
                        "        \"id\": \"" + id.toString() + "\"," +
                        "        \"due_date\": \"2018-04-24\"," +
                        "        \"total_in_cents\": \"1000\"," +
                        "        \"customer\": \"Jefferson P Araujo\"" +
                        "    }" +
                        "]"))
        ;
    }

    private UUID geraUUID() throws ParseException {
        Bankslip bankslip = geraBoleto();
        return bankslip.getId();
    }

    private Bankslip geraBoleto() throws ParseException {
        Bankslip bankslip = new Bankslip();
        bankslip.setTotalInCents(1000L);
        bankslip.setStatus(BankslipStatusEnum.PENDING.getCodigo());
        bankslip.setCustomer("Jefferson P Araujo");
        bankslip.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-24"));
        bankslipRepository.save(bankslip);
        return bankslip;
    }

    @Test
    public void verDetalhesBoleto() throws Exception {
        UUID id = geraUUID();
        mockMvc.perform(get("/rest/bankslips/" + id.toString())).andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"id\":\"" + id.toString() + "\",\"due_date\":\"2018-04-24\",\"total_in_cents\":\"1000\"" +
                                ",\"customer\":\"Jefferson P Araujo\",\"fine\":\"0,0\",\"status\":\"PENDING\"}"));
    }

    @Test
    public void pagarBoletoExistente() throws Exception {
        UUID id = geraUUID();
        mockMvc.perform(put("/rest/bankslips/" + id.toString() + "/pay"))
                .andExpect(status().isOk())
                .andExpect(content().string(mensageResource.getMensagem("bankslips.pay.ok.200"))).andReturn();
    }

    @Test
    public void pagarBoletoNaoExistente() throws Exception {
        mockMvc.perform(put("/rest/bankslips/cb5a9987-af23-4e5a-ac2c-8c190bbdcc19/pay"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(mensageResource.getMensagem("bankslips.not.exist.404"))).andReturn();
    }

    @Test
    public void pagarBoletoNaoAutorizado() throws Exception {
        Bankslip bankslip = geraBoleto();
        bankslip.setStatus(BankslipStatusEnum.PAID.getCodigo());
        bankslipRepository.save(bankslip);
        mockMvc.perform(put("/rest/bankslips/" + bankslip.getId() + "/pay"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(mensageResource.getMensagem("bankslips.pay.401"))).andReturn();
    }


    @Test
    public void cancelarBoletoExistente() throws Exception {
        UUID id = geraUUID();
        mockMvc.perform(delete("/rest/bankslips/" + id.toString() + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(content().string(mensageResource.getMensagem("bankslips.cancel.ok.200"))).andReturn();
    }

    @Test
    public void cancelarBoletoNaoExistente() throws Exception {
        mockMvc.perform(delete("/rest/bankslips/cb5a9987-af23-4e5a-ac2c-8c190bbdcc19/cancel"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(mensageResource.getMensagem("bankslips.not.exist.404"))).andReturn();
    }

    @Test
    public void cancelarBoletoNaoAutorizado() throws Exception {
        Bankslip bankslip = geraBoleto();
        bankslip.setStatus(BankslipStatusEnum.CANCELED.getCodigo());
        bankslipRepository.save(bankslip);
        mockMvc.perform(delete("/rest/bankslips/" + bankslip.getId() + "/cancel"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(mensageResource.getMensagem("bankslips.cancel.401"))).andReturn();
    }
}
