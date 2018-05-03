package br.com.desafio.contaazul.rbankslip.entity;

import br.com.desafio.contaazul.rbankslip.util.ConstantApplication;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class BankslipCalculate {

    private String fine;
    private Bankslip bankslip;

    public BankslipCalculate(Bankslip bankslip) {
        this.bankslip = bankslip;
        trataJuros();
    }

    private void trataJuros() {
        String dueDateString = new SimpleDateFormat(ConstantApplication.YYYY_MM_DD).format(getDueDate());
        LocalDateTime fromDateTime = LocalDate.parse(dueDateString).atStartOfDay();
        long days = Duration.between(LocalDateTime.now().toLocalDate().atStartOfDay(), fromDateTime).toDays();

        if (days > 0 && days <= 10) {
            this.fine = new BigDecimal(getTotalInCents()).multiply(new BigDecimal(ConstantApplication.LT10)).setScale(0).toString();
        } else if (days > 10) {
            this.fine = new BigDecimal(getTotalInCents()).multiply(new BigDecimal(ConstantApplication.GT10)).setScale(0).toString();
        } else {
            this.fine = ConstantApplication.DEFAULT;
        }
    }


    public UUID getId() {
        return bankslip.getId();
    }

    public Date getDueDate() {
        return bankslip.getDueDate();
    }

    public Long getTotalInCents() {
        return bankslip.getTotalInCents();
    }

    public String getCustomer() {
        return bankslip.getCustomer();
    }

    public String getFine() {
        return fine;
    }

    public String getStatus() {
        return bankslip.getStatus();
    }

}
