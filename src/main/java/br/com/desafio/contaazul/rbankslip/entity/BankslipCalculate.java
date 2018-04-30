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

    private UUID id;
    private Date dueDate;
    private Long totalInCents;
    private String customer;
    private String fine;
    private String status;

    public BankslipCalculate(Bankslip bankslip) {
        this.id = bankslip.getId();
        this.dueDate = bankslip.getDueDate();
        this.totalInCents = bankslip.getTotalInCents();
        this.customer = bankslip.getCustomer();
        this.status = bankslip.getStatus();
        trataJuros();
    }

    private void trataJuros() {
        String dueDateString = new SimpleDateFormat(ConstantApplication.YYYY_MM_DD).format(getDueDate());
        LocalDateTime fromDateTime = LocalDate.parse(dueDateString).atStartOfDay();
        long days = Duration.between(LocalDateTime.now().toLocalDate().atStartOfDay(), fromDateTime).toDays();

        if (days > 0 && days <= 10) {
            this.fine = new BigDecimal(totalInCents).multiply(new BigDecimal(ConstantApplication.LT10)).setScale(0).toString();
        } else if (days > 10) {
            this.fine = new BigDecimal(totalInCents).multiply(new BigDecimal(ConstantApplication.GT10)).setScale(0).toString();
        } else {
            this.fine = ConstantApplication.DEFAULT;
        }
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(Long totalInCents) {
        this.totalInCents = totalInCents;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
