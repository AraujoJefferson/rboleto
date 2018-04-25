package br.com.desafio.contaazul.rboleto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "BOLETO")
public class Boleto {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    private UUID id;

    @NotBlank
    @Column(name = "DUE_DATE")
    private String due_date;

    @NotBlank
    @Column(name = "TOTAL_IN_CENT")
    @Min(0)
    private String total_in_cents;

    @NotBlank
    @Column(name = "CUSTOMER")
    private String customer;

    @NotBlank
    @Column(name = "STATUS")
    private String status;


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID i) {
        this.id = i;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
