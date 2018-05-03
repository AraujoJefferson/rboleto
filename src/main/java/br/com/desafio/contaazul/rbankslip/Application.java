package br.com.desafio.contaazul.rbankslip;

import br.com.desafio.contaazul.rbankslip.configuration.MensageResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Inicializa o projeto
 */
@ComponentScan
@EnableAutoConfiguration
public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        //Sonnar gera um falso positivo neste ponto
        //Neste ponto não farei o tratamento
        if(SpringApplication.run(Application.class).isActive()){
            logger.info(MensageResource.getMensagem("success"));
        }
    }
}

