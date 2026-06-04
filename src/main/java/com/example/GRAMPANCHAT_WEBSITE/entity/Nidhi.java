package com.example.GRAMPANCHAT_WEBSITE.entity;

import jakarta.persistence.*;

@Entity
public class Nidhi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workName;
    private String schemeName;
    private Double approvedFund;
    private Double expense;
    private String status;

    public Long getId() {
        return id;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Double getApprovedFund() {
        return approvedFund;
    }

    public void setApprovedFund(Double approvedFund) {
        this.approvedFund = approvedFund;
    }

    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}