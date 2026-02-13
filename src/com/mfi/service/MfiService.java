package com.mfi.service;

import com.mfi.bean.Borrower;
import com.mfi.bean.Loan;
import com.mfi.bean.Installment;

import com.mfi.dao.BorrowerDAO;
import com.mfi.dao.LoanDAO;
import com.mfi.dao.InstallmentDAO;

import com.mfi.util.DBUtil;
import com.mfi.util.ValidationException;
import com.mfi.util.RepaymentProcessingException;
import com.mfi.util.ActiveLoanExistsException;

import java.sql.Connection;
import java.sql.Date;

import java.util.List;

import java.math.BigDecimal;

public class MfiService {

    private BorrowerDAO borrowerDAO = new BorrowerDAO();
    private LoanDAO loanDAO = new LoanDAO();
    private InstallmentDAO installmentDAO = new InstallmentDAO();


    public Borrower viewBorrowerDetails(String borrowerID) {

        if (borrowerID == null || borrowerID.trim().isEmpty()) {
            return null;
        }

        return borrowerDAO.findBorrower(borrowerID);
    }


    public List<Borrower> viewAllBorrowers() {

        return borrowerDAO.viewAllBorrowers();
    }


    public boolean registerNewBorrower(Borrower b)
            throws ValidationException {

        if (b.getBorrowerID() == null || b.getBorrowerID().isEmpty()
                || b.getFullName() == null || b.getFullName().isEmpty()
                || b.getPrimaryPhone() == null || b.getPrimaryPhone().isEmpty()
                || b.getVillageOrArea() == null || b.getVillageOrArea().isEmpty()) {

            throw new ValidationException();
        }

        // Check duplicate
        Borrower exist =
                borrowerDAO.findBorrower(b.getBorrowerID());

        if (exist != null) {
            return false;
        }

        // Default ACTIVE
        b.setStatus("ACTIVE");

        return borrowerDAO.insertBorrower(b);
    }


    public Loan viewLoanDetails(String loanID) {

        if (loanID == null || loanID.trim().isEmpty()) {
            return null;
        }

        return loanDAO.findLoan(loanID);
    }


    public List<Loan> viewAllLoans() {

        return loanDAO.viewAllLoans();
    }


    public boolean createLoanAccount(Loan loan)
            throws ValidationException {

        if (loan.getBorrowerID() == null || loan.getBorrowerID().isEmpty()
                || loan.getBorrowerID() == null || loan.getBorrowerID().isEmpty()
                || loan.getProductName() == null || loan.getProductName().isEmpty()
                || loan.getPrincipalAmount().compareTo(BigDecimal.ZERO) <= 0
                || loan.getAnnualInterestRate().compareTo(BigDecimal.ZERO) <= 0
                || loan.getTermMonths() <= 0) {

            throw new ValidationException();
        }

        // Check borrower
        Borrower b = borrowerDAO.findBorrower(loan.getBorrowerID());

        if (b == null || !"ACTIVE".equals(b.getStatus())) {
            return false;
        }

        Connection con = null;

        try {

            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            // Set default values
            loan.setOutstandingPrinciple(
                    loan.getPrincipalAmount());

            loan.setStatus("ACTIVE");

            if (loan.getDisbursementDate() == null) {
                loan.setDisbursementDate(
                        new Date(System.currentTimeMillis()));
            }

            boolean ok = loanDAO.insertLoan(loan);
            String generatedLoanId = loan.getLoanID();


            if (!ok) {
                con.rollback();
                return false;
            }

            BigDecimal emi =
                    loan.getPrincipalAmount()
                            .divide(new BigDecimal(loan.getTermMonths()),
                                    2, BigDecimal.ROUND_HALF_UP);

            for (int i = 1; i <= loan.getTermMonths(); i++) {

                Installment ins = new Installment();

                ins.setInstallmentID(
                        installmentDAO.generateInstallmentID());

                ins.setLoanID(generatedLoanId);


                ins.setInstallementNo(i);

                ins.setDueDate(
                        new Date(System.currentTimeMillis()
                                + (long) i * 30 * 24 * 60 * 60 * 1000));

                ins.setDueAmt(emi);

                ins.setPaidAmt(BigDecimal.ZERO);

                ins.setPaidDate(null);

                ins.setStatus("PENDING");

                installmentDAO.insertInstallment(ins);
            }

            con.commit();
            return true;

        } catch (Exception e) {

            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {

            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<Installment> listInstallmentsByLoan(String loanID) {

        return installmentDAO.findInstallmentsByLoan(loanID);
    }


    public boolean postRepayment(int installmentID,
                                 BigDecimal paymentAmount)
            throws ValidationException,
            RepaymentProcessingException {

        if (installmentID <= 0
                || paymentAmount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new ValidationException();
        }

        Installment ins =
                installmentDAO.findInstallment(installmentID);

        if (ins == null) {
            return false;
        }

        if ("PAID".equals(ins.getStatus())) {
            throw new RepaymentProcessingException();
        }

        Loan loan =
                loanDAO.findLoan(ins.getLoanID());

        if (loan == null || !"ACTIVE".equals(loan.getStatus())) {
            throw new RepaymentProcessingException();
        }

        BigDecimal newPaid =
                ins.getPaidAmt().add(paymentAmount);

        if (newPaid.compareTo(ins.getDueAmt()) > 0) {
            newPaid = ins.getDueAmt();
        }

        String newStatus;

        if (newPaid.compareTo(ins.getDueAmt()) == 0) {
            newStatus = "PAID";
        } else {
            newStatus = "PARTIALLY_PAID";
        }

        BigDecimal newOutstanding =
                loan.getOutstandingPrinciple()
                        .subtract(paymentAmount);

        if (newOutstanding.compareTo(BigDecimal.ZERO) < 0) {
            newOutstanding = BigDecimal.ZERO;
        }

        Connection con = null;

        try {

            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            installmentDAO.updateInstallmentPayment(
                    installmentID,
                    newPaid,
                    new Date(System.currentTimeMillis()),
                    newStatus
            );

            loanDAO.updateOutstandingPrincipal(
                    loan.getLoanID(),
                    newOutstanding
            );

            // Check if all paid
            List<Installment> pending =
                    installmentDAO.findPendingInstallmentsByLoan(
                            loan.getLoanID());

            if (pending.isEmpty()) {

                loanDAO.updateLoanStatus(
                        loan.getLoanID(),
                        "CLOSED");
            }

            con.commit();
            return true;

        } catch (Exception e) {

            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {

            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<Installment> listOverdueInstallments(Date refDate) {

        return installmentDAO.findOverdueInstallments(refDate);
    }


    public boolean removeBorrower(String borrowerID)
            throws ValidationException,
            ActiveLoanExistsException {

        if (borrowerID == null || borrowerID.isEmpty()) {

            throw new ValidationException();
        }

        List<Loan> active =
                loanDAO.findActiveLoansByBorrower(borrowerID);

        if (!active.isEmpty()) {

            throw new ActiveLoanExistsException();
        }

        return borrowerDAO.deleteBorrower(borrowerID);
    }
}
