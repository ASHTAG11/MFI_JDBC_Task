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
                    "SELECT * FROM loan_tbl WHERE Loan_ID = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, loanID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                loan = new Loan();

                loan.setLoanID(
                        rs.getString("Loan_ID"));

                loan.setBorrowerID(
                        rs.getString("Borrower_ID"));

                loan.setProductName(
                        rs.getString("Product_Name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("Principal_Amt"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("Annual_Interest_Rate"));

                loan.setTermMonths(
                        rs.getInt("Term_Months"));

                loan.setRepaymentFrequency(
                        rs.getString("Repayment_Frequency"));

                loan.setDisbursementDate(
                        rs.getDate("Disbursement_Date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("Outstanding_Principal"));

                loan.setStatus(
                        rs.getString("Status_"));
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
                        rs.getString("Loan_ID"));

                loan.setBorrowerID(
                        rs.getString("Borrower_ID"));

                loan.setProductName(
                        rs.getString("Product_Name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("Principal_Amt"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("Annual_Interest_Rate"));

                loan.setTermMonths(
                        rs.getInt("Term_Months"));

                loan.setRepaymentFrequency(
                        rs.getString("Repayment_Frequency"));

                loan.setDisbursementDate(
                        rs.getDate("Disbursement_Date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("Outstanding_Principal"));

                loan.setStatus(
                        rs.getString("Status_"));

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
                    "SELECT * FROM loan_tbl WHERE Status_ = 'ACTIVE'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Loan loan = new Loan();

                loan.setLoanID(
                        rs.getString("Loan_ID"));

                loan.setBorrowerID(
                        rs.getString("Borrower_ID"));

                loan.setProductName(
                        rs.getString("Product_Name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("Principal_Amt"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("Annual_Interest_Rate"));

                loan.setTermMonths(
                        rs.getInt("Term_Months"));

                loan.setRepaymentFrequency(
                        rs.getString("Repayment_Frequency"));

                loan.setDisbursementDate(
                        rs.getDate("Disbursement_Date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("Outstanding_Principal"));

                loan.setStatus(
                        rs.getString("Status_"));

                list.add(loan);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }




    public boolean insertLoan(Loan loan) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "INSERT INTO loan_tbl VALUES (?,?,?,?,?,?,?,?,?,?)";

            // Generate new Loan ID
            String newLoanId = getNextLoanId(con);
            loan.setLoanID(newLoanId);


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
            System.out.println(" ");
            System.out.println(loan.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }




    public boolean updateLoanStatus(String loanID,
                                    String status) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "UPDATE loan_tbl SET Status_ = ? WHERE Loan_ID = ?";

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




    public boolean updateOutstandingPrincipal(String loanID,
                                              BigDecimal newOutstanding) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "UPDATE loan_tbl SET Outstanding_Principal = ? WHERE Loan_ID = ?";

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




    public List<Loan> findActiveLoansByBorrower(String borrowerID) {

        List<Loan> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM loan_tbl WHERE Borrower_ID = ? AND Status_ = 'ACTIVE'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, borrowerID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Loan loan = new Loan();

                loan.setLoanID(
                        rs.getString("Loan_ID"));

                loan.setBorrowerID(
                        rs.getString("Borrower_ID"));

                loan.setProductName(
                        rs.getString("Product_Name"));

                loan.setPrincipalAmount(
                        rs.getBigDecimal("Principal_Amt"));

                loan.setAnnualInterestRate(
                        rs.getBigDecimal("Annual_Interest_Rate"));

                loan.setTermMonths(
                        rs.getInt("Term_Months"));

                loan.setRepaymentFrequency(
                        rs.getString("Repayment_Frequency"));

                loan.setDisbursementDate(
                        rs.getDate("Disbursement_Date"));

                loan.setOutstandingPrinciple(
                        rs.getBigDecimal("Outstanding_Principal"));

                loan.setStatus(
                        rs.getString("Status_"));

                list.add(loan);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public String getNextLoanId(Connection con) throws Exception {

        String sql = "SELECT Loan_ID FROM loan_tbl ORDER BY Loan_ID DESC LIMIT 1";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String lastId = rs.getString("Loan_ID"); // LN2025-010

            int num = Integer.parseInt(lastId.substring(7));
            num++;

            return "LN2025-" + String.format("%03d", num);
        }

        return "LN2025-001";
    }

}
