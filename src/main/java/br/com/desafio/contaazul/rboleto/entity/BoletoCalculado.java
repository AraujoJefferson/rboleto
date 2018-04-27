package br.com.desafio.contaazul.rboleto.entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class BoletoCalculado {
    public static final String LT10 = "0.05";
    public static final String GT10 = "0.1";
    public static final String DEFAULT = "0,0";
    private UUID id;
    private String due_date;
    private String total_in_cents;
    private String customer;
    private String fine;
    private String status;

    public BoletoCalculado(Boleto boleto) {
        this.id = boleto.getId();
        this.due_date = boleto.getDueDate();
        this.total_in_cents = boleto.getTotalInCents();
        this.customer = boleto.getCustomer();
        this.status = boleto.getStatus();
        trataJuros();
    }

    private void trataJuros() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fromDateTime = LocalDateTime.from(LocalDate.parse(due_date, formatter).atStartOfDay());
        long days = Duration.between(LocalDateTime.now().toLocalDate().atStartOfDay(), fromDateTime).toDays();

        if (days > 0 && days <= 10) {
            this.fine = new BigDecimal(total_in_cents).multiply(new BigDecimal(LT10)).setScale(0).toString();
        } else if (days > 10) {
            this.fine = new BigDecimal(total_in_cents).multiply(new BigDecimal(GT10)).setScale(0).toString();
        } else {
            this.fine = DEFAULT;
        }
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTotal_in_cents() {
        return total_in_cents;
    }

    public void setTotal_in_cents(String total_in_cents) {
        this.total_in_cents = total_in_cents;
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
