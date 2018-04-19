package br.com.desafio.contaazul.rboleto.cadastro;

import br.com.desafio.contaazul.rboleto.bean.Boleto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class BoletoController {

    @RequestMapping(method= RequestMethod.POST, path = "/bankslips")
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        //return new Greeting(counter.incrementAndGet(), String.format(template, name));
        return new Boleto();
    }
}
