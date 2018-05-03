package br.com.desafio.contaazul.rbankslip.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ConstantApplication {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final Charset ENCODE = StandardCharsets.UTF_8;
    public static final String UUID_PATTERN ="^[0-9A-Fa-f]{8}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{4}-[0-9A-Fa-f]{12}$";

    private ConstantApplication(){}
}
