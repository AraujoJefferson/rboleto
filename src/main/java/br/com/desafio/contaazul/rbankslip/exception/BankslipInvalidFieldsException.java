package br.com.desafio.contaazul.rbankslip.exception;

public class BankslipInvalidFieldsException extends RuntimeException {
    public BankslipInvalidFieldsException() {
        super();
    }

    public BankslipInvalidFieldsException(String message) {
        super(message);
    }

    public BankslipInvalidFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankslipInvalidFieldsException(Throwable cause) {
        super(cause);
    }
}
