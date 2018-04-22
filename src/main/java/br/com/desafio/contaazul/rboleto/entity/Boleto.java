package br.com.desafio.contaazul.rboleto.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "BOLETO")
public class Boleto {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "uuid", columnDefinition = "BINARY(16)")
    private UUID id;
    //"due_date"​ : ​ "2018-01-01"​ ,
    @Column(name = "DUE_DATE")
    private String due_date;
    //"total_in_cents"​ : ​ "100000"​ ,
    @Column(name = "TOTAL_IN_CENT")
    private String total_in_cents;
    //"customer"​ : ​ "Trillian Company"​ ,
    @Column(name = "CUSTOMER")
    private String customer;
    //"status"​ : ​ "PENDING"
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
