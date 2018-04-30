package br.com.desafio.contaazul.rbankslip;

import br.com.desafio.contaazul.rbankslip.business.BankslipRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx =
        SpringApplication.run(Application.class);
        BankslipRepository repository = ctx.getBean(BankslipRepository.class);
        repository.findAll();
    }
}

