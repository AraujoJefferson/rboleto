package br.com.desafio.contaazul.rbankslip.exception;

public class BankslipInvalidException extends RuntimeException {
    public BankslipInvalidException() {
        super();
    }

    public BankslipInvalidException(String message) {
        super(message);
    }

    public BankslipInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankslipInvalidException(Throwable cause) {
        super(cause);
    }
}
