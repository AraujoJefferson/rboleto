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
    @JsonProperty("id")
    private UUID id;

    @NotBlank
    @Column(name = "DUE_DATE")
    @JsonProperty("due_date")
    private String dueDate;

    @NotBlank
    @Column(name = "TOTAL_IN_CENT")
    @Min(0)
    @JsonProperty("total_in_cents")
    private String totalInCents;

    @NotBlank
    @Column(name = "CUSTOMER")
    @JsonProperty("customer")
    private String customer;

    @NotBlank
    @Column(name = "STATUS")
    @JsonProperty("status")
    private String status;


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID i) {
        this.id = i;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(String totalInCents) {
        this.totalInCents = totalInCents;
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
