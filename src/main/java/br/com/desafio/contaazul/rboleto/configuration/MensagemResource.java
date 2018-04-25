package br.com.desafio.contaazul.rboleto.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MensagemResource {
    private static Locale locale = Locale.getDefault();

    private static MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:mensagem");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    public static String getMensagem(String mensagem, String... values) {
        MessageSource message = messageSource();
       return message.getMessage(mensagem, values, locale);
    }
}