package br.com.desafio.contaazul.rboleto.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BoletoCalculado {
    private UUID id;
    private String due_date;
    private String total_in_cents;
    private String customer;
    private String fine;
    private String status;

    public BoletoCalculado(Boleto boleto) {
        this.id = boleto.getId();
        this.due_date = boleto.getDue_date();
        this.total_in_cents = boleto.getTotal_in_cents();
        this.customer = boleto.getCustomer();
        this.status = boleto.getStatus();
        trataJuros();
    }

    private void trataJuros() {
        Date dataBoleto = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            dataBoleto = sdf.parse(due_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dataAtual = new Date();
        dataAtual.setHours(0);
        dataAtual.setMinutes(0);
        dataAtual.setSeconds(0);
        long dias = retornaDias(dataAtual, dataBoleto);

        if (dias >= 0 && dias <= 10) {
            this.fine = new BigDecimal(total_in_cents).multiply(new BigDecimal("0.05")).setScale(0).toString();
        } else if (dias >= 10) {
            this.fine = new BigDecimal(total_in_cents).multiply(new BigDecimal("0.1")).setScale(0).toString();
        } else {
            this.fine = "0,0";
        }
    }

    private long retornaDias(Date dataIni, Date dataFim) {
        long dt = (dataFim.getTime() - dataIni.getTime()) + 3600000; // 1 hora para compensar horário de verão
        return dt / 86400000L; // passaram-se 67111 dias
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
