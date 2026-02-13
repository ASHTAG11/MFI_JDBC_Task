package com.mfi.dao;

import com.mfi.bean.Loan;
import com.mfi.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

public class LoanDAO {


    public Loan findLoan(String loanID) {

        Loan loan = null;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM loan_tbl WHERE loan_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, loanID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                loan = new Loan();

                loan.setLoanID(
                        rs.getString("loan_id"));

                loan.setBorrowerID(
                        rs.getString("borrower_id"));

                loan.setProductName(
                        rs.getString("product_name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("principal_amount"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("annual_interest_rate"));

                loan.setTermMonths(
                        rs.getInt("term_months"));

                loan.setRepaymentFrequency(
                        rs.getString("repayment_frequency"));

                loan.setDisbursementDate(
                        rs.getDate("disbursement_date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("outstanding_principal"));

                loan.setStatus(
                        rs.getString("status"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loan;
    }




    public List<Loan> viewAllLoans() {

        List<Loan> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql = "SELECT * FROM loan_tbl";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Loan loan = new Loan();

                loan.setLoanID(
                        rs.getString("loan_id"));

                loan.setBorrowerID(
                        rs.getString("borrower_id"));

                loan.setProductName(
                        rs.getString("product_name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("principal_amount"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("annual_interest_rate"));

                loan.setTermMonths(
                        rs.getInt("term_months"));

                loan.setRepaymentFrequency(
                        rs.getString("repayment_frequency"));

                loan.setDisbursementDate(
                        rs.getDate("disbursement_date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("outstanding_principal"));

                loan.setStatus(
                        rs.getString("status"));

                list.add(loan);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<Loan> viewActiveLoans() {

        List<Loan> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM loan_tbl WHERE status = 'ACTIVE'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Loan loan = new Loan();

                loan.setLoanID(
                        rs.getString("loan_id"));

                loan.setBorrowerID(
                        rs.getString("borrower_id"));

                loan.setProductName(
                        rs.getString("product_name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("principal_amount"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("annual_interest_rate"));

                loan.setTermMonths(
                        rs.getInt("term_months"));

                loan.setRepaymentFrequency(
                        rs.getString("repayment_frequency"));

                loan.setDisbursementDate(
                        rs.getDate("disbursement_date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("outstanding_principal"));

                loan.setStatus(
                        rs.getString("status"));

                list.add(loan);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    /* ================= Insert Loan ================= */

    public boolean insertLoan(Loan loan) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "INSERT INTO loan_tbl VALUES (?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, loan.getLoanID());
            ps.setString(2, loan.getBorrowerID());
            ps.setString(3, loan.getProductName());
            ps.setBigDecimal(4, loan.getPrincipalAmount());
            ps.setBigDecimal(5, loan.getAnnualInterestRate());
            ps.setInt(6, loan.getTermMonths());
            ps.setString(7, loan.getRepaymentFrequency());
            ps.setDate(8, loan.getDisbursementDate());
            ps.setBigDecimal(9, loan.getOutstandingPrinciple());
            ps.setString(10, loan.getStatus());

            int x = ps.executeUpdate();

            if (x > 0) {
                result = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /* ================= Update Loan Status ================= */

    public boolean updateLoanStatus(String loanID,
                                    String status) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "UPDATE loan_tbl SET status = ? WHERE loan_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setString(2, loanID);

            int x = ps.executeUpdate();

            if (x > 0) {
                result = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /* ================= Update Outstanding Principal ================= */

    public boolean updateOutstandingPrincipal(String loanID,
                                              BigDecimal newOutstanding) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "UPDATE loan_tbl SET outstanding_principal = ? WHERE loan_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setBigDecimal(1, newOutstanding);
            ps.setString(2, loanID);

            int x = ps.executeUpdate();

            if (x > 0) {
                result = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /* ================= Find Active Loans By Borrower ================= */

    public List<Loan> findActiveLoansByBorrower(String borrowerID) {

        List<Loan> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM loan_tbl WHERE borrower_id = ? AND status = 'ACTIVE'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, borrowerID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Loan loan = new Loan();

                loan.setLoanID(
                        rs.getString("loan_id"));

                loan.setBorrowerID(
                        rs.getString("borrower_id"));

                loan.setProductName(
                        rs.getString("product_name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("principal_amount"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("annual_interest_rate"));

                loan.setTermMonths(
                        rs.getInt("term_months"));

                loan.setRepaymentFrequency(
                        rs.getString("repayment_frequency"));

                loan.setDisbursementDate(
                        rs.getDate("disbursement_date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("outstanding_principal"));

                loan.setStatus(
                        rs.getString("status"));

                list.add(loan);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
