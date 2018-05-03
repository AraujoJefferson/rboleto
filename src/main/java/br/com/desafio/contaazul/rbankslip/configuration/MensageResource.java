package br.com.desafio.contaazul.rbankslip.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

import static br.com.desafio.contaazul.rbankslip.util.ConstantApplication.ENCODE;

@Configuration
public class MensageResource {
    private static Locale locale = Locale.getDefault();

    private static MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:mensagem");
        messageSource.setDefaultEncoding(String.valueOf(ENCODE));
        return messageSource;
    }

    public static String getMensagem(String mensagem, String... values) {
        MessageSource message = messageSource();
        return message.getMessage(mensagem, values, locale);
    }

}