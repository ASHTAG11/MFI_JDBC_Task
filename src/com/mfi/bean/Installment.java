package com.mfi.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class Installment {

    private int installmentID;
    private String loanID;
    private int installementNo;
    private java.sql.Date dueDate;

    public int getInstallmentID() {
        return installmentID;
    }

    public void setInstallmentID(int installmentID) {
        this.installmentID = installmentID;
    }

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }

    public int getInstallementNo() {
        return installementNo;
    }

    public void setInstallementNo(int installementNo) {
        this.installementNo = installementNo;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(BigDecimal dueAmt) {
        this.dueAmt = dueAmt;
    }

    public BigDecimal getPaidAmt() {
        return paidAmt;
    }

    public void setPaidAmt(BigDecimal paidAmt) {
        this.paidAmt = paidAmt;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private java.math.BigDecimal dueAmt;
    private java.math.BigDecimal paidAmt;
    private java.sql.Date paidDate;
    private String status;
}
