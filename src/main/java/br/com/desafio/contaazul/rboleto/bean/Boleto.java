package br.com.desafio.contaazul.rboleto.bean;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BOLETO")
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    //"due_date"​ : ​ "2018-01-01"​ ,
    @Column(name = "DUE_DATE")
    private Date due_date;
    //"total_in_cents"​ : ​ "100000"​ ,
    @Column(name = "TOTAL_IN_CENT")
    private int total_in_cent;
    //"customer"​ : ​ "Trillian Company"​ ,
    @Column(name = "CUSTOMER")
    private String customer;
    //"status"​ : ​ "PENDING"
    @Column(name = "STATUS")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public int getTotal_in_cent() {
        return total_in_cent;
    }

    public void setTotal_in_cent(int total_in_cent) {
        this.total_in_cent = total_in_cent;
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
