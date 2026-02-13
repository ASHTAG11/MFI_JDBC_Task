package com.mfi.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class Loan {

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }

    public String getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(String borrowerID) {
        this.borrowerID = borrowerID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public String getRepaymentFrequency() {
        return repaymentFrequency;
    }

    public void setRepaymentFrequency(String repaymentFrequency) {
        this.repaymentFrequency = repaymentFrequency;
    }

    public Date getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(Date disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public BigDecimal getOutstandingPrinciple() {
        return outstandingPrinciple;
    }

    public void setOutstandingPrinciple(BigDecimal outstandingPrinciple) {
        this.outstandingPrinciple = outstandingPrinciple;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String loanID;
    private String borrowerID;
    private String productName;
    private java.math.BigDecimal principalAmount;
    private java.math.BigDecimal annualInterestRate;
    private int termMonths;
    private String repaymentFrequency;
    private java.sql.Date disbursementDate;
    private java.math.BigDecimal outstandingPrinciple;
    private  String status;
}
