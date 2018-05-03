package br.com.desafio.contaazul.rbankslip.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

import static br.com.desafio.contaazul.rbankslip.util.BankslipConstant.*;

@Entity
@Table(name = TABLE_NAME_BANKSLIP)
public class Bankslip {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = FIELD_NAME_UUID, columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    @Column(name = FIELD_NAME_DUE_DATE)
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @NotNull
    @Column(name = FIELD_NAME_TOTAL_IN_CENT)
    @Min(0)
    private Long totalInCents;

    @NotBlank
    @Column(name = FIELD_NAME_CUSTOMER)
    private String customer;

    @NotBlank
    @Column(name = FIELD_NAME_STATUS)
    private String status;


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID i) {
        this.id = i;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
