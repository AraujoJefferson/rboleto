package br.com.desafio.contaazul.rbankslip.exception;

public class BankslipUnauthorizedCancelException extends RuntimeException {
    public BankslipUnauthorizedCancelException() {
        super();
    }

    public BankslipUnauthorizedCancelException(String message) {
        super(message);
    }

    public BankslipUnauthorizedCancelException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankslipUnauthorizedCancelException(Throwable cause) {
        super(cause);
    }
}
