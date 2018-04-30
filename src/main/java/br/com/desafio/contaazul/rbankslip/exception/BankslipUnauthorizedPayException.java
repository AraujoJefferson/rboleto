package br.com.desafio.contaazul.rbankslip.exception;

public class BankslipUnauthorizedPayException extends RuntimeException {
    public BankslipUnauthorizedPayException() {
        super();
    }

    public BankslipUnauthorizedPayException(String message) {
        super(message);
    }

    public BankslipUnauthorizedPayException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankslipUnauthorizedPayException(Throwable cause) {
        super(cause);
    }
}
