package br.com.desafio.contaazul.rbankslip.exception;

public class BankslipDoesNotExistException extends RuntimeException {
    public BankslipDoesNotExistException() {
        super();
    }

    public BankslipDoesNotExistException(String message) {
        super(message);
    }

    public BankslipDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankslipDoesNotExistException(Throwable cause) {
        super(cause);
    }
}
