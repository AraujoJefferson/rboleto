package br.com.desafio.contaazul.rboleto.controller;

import br.com.desafio.contaazul.rboleto.entity.Boleto;
import br.com.desafio.contaazul.rboleto.business.BoletoBusiness;
import br.com.desafio.contaazul.rboleto.enumerate.BoletoStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/rest")
public class BoletoController {

    private BoletoBusiness business = new BoletoBusiness();

    @RequestMapping(method = RequestMethod.POST, path = "/bankslips")
    public Object cadastrarBoleto(@RequestParam(value = "due_date", required = true) String due_date,
                                          @RequestParam(value = "total_in_cents", required = true) String total_in_cents,
                                          @RequestParam(value = "customer", required = true) String customer,
                                          @RequestParam(value = "status", required = true) String status,
                                          HttpServletResponse response) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
        Boleto boleto = new Boleto();
        try {
            boleto.setDue_date(simpleDateFormat.parse(due_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boleto.setTotal_in_cents(Integer.parseInt(total_in_cents));
        boleto.setCustomer(customer);
        if (BoletoStatusEnum.get(status) == null) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bankslip not provided in the request body");
                return "";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boleto.setStatus(status);
        try {
            response.sendError(HttpServletResponse.SC_CREATED,"Bankslip created");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bankslips")
    public Object listarBoletos() {
        return business.listarBoletos();
    }
}
