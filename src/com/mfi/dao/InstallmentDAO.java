package com.mfi.dao;

import com.mfi.bean.Installment;
import com.mfi.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

public class InstallmentDAO {


    public int generateInstallmentID() {

        int id = 1;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT MAX(Installment_ID) FROM installment_tbl";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                id = rs.getInt(1) + 1;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }


    public boolean insertInstallment(Installment ins) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "INSERT INTO installment_tbl VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, ins.getInstallmentID());
            ps.setString(2, ins.getLoanID());
            ps.setInt(3, ins.getInstallementNo());
            ps.setDate(4, ins.getDueDate());
            ps.setBigDecimal(5, ins.getDueAmt());
            ps.setBigDecimal(6, ins.getPaidAmt());
            ps.setDate(7, ins.getPaidDate());
            ps.setString(8, ins.getStatus());

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



    public Installment findInstallment(int installmentID) {

        Installment ins = null;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM installment_tbl WHERE Installment_ID = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, installmentID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                ins = new Installment();

                ins.setInstallmentID(
                        rs.getInt("Installment_ID"));

                ins.setLoanID(
                        rs.getString("Loan_ID"));

                ins.setInstallementNo(
                        rs.getInt("Installment_No"));

                ins.setDueDate(
                        rs.getDate("Due_Date"));

                ins.setDueAmt(
                        rs.getBigDecimal("Due_Amt"));

                ins.setPaidAmt(
                        rs.getBigDecimal("Paid_Amt"));

                ins.setPaidDate(
                        rs.getDate("Paid_Date"));

                ins.setStatus(
                        rs.getString("status_"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ins;
    }




    public List<Installment> findInstallmentsByLoan(String loanID) {

        List<Installment> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM installment_tbl WHERE Loan_ID = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, loanID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Installment ins = new Installment();

                ins.setInstallmentID(
                        rs.getInt("Installment_ID"));

                ins.setLoanID(
                        rs.getString("Loan_ID"));

                ins.setInstallementNo(
                        rs.getInt("Installment_No"));

                ins.setDueDate(
                        rs.getDate("Due_Date"));

                ins.setDueAmt(
                        rs.getBigDecimal("Due_Amt"));

                ins.setPaidAmt(
                        rs.getBigDecimal("Paid_Amt"));

                ins.setPaidDate(
                        rs.getDate("Paid_Date"));

                ins.setStatus(
                        rs.getString("Status"));

                list.add(ins);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }




    public List<Installment> findPendingInstallmentsByLoan(String loanID) {

        List<Installment> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM installment_tbl " +
                            "WHERE Loan_ID = ? AND " +
                            "(status_ = 'PENDING' OR status_ = 'PARTIALLY_PAID')";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, loanID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Installment ins = new Installment();

                ins.setInstallmentID(
                        rs.getInt("Installment_ID"));

                ins.setLoanID(
                        rs.getString("Loan_ID"));

                ins.setInstallementNo(
                        rs.getInt("Installment_No"));

                ins.setDueDate(
                        rs.getDate("Due_Date"));

                ins.setDueAmt(
                        rs.getBigDecimal("Due_Amt"));

                ins.setPaidAmt(
                        rs.getBigDecimal("Paid_Amt"));

                ins.setPaidDate(
                        rs.getDate("Paid_Date"));

                ins.setStatus(
                        rs.getString("status_"));

                list.add(ins);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }




    public boolean updateInstallmentPayment(int installmentID,
                                            BigDecimal newPaidAmount,
                                            Date paidDate,
                                            String newStatus) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "UPDATE installment_tbl " +
                            "SET Paid_Amt = ?, Paid_Date = ?, status_ = ? " +
                            "WHERE Installment_ID = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setBigDecimal(1, newPaidAmount);
            ps.setDate(2, paidDate);
            ps.setString(3, newStatus);
            ps.setInt(4, installmentID);

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




    public List<Installment> findOverdueInstallments(Date refDate) {

        List<Installment> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM installment_tbl " +
                            "WHERE Due_Date < ? AND status_ <> 'PAID'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setDate(1, refDate);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Installment ins = new Installment();

                ins.setInstallmentID(
                        rs.getInt("Installment_ID"));

                ins.setLoanID(
                        rs.getString("Loan_ID"));

                ins.setInstallementNo(
                        rs.getInt("Installment_No"));

                ins.setDueDate(
                        rs.getDate("Due_Date"));

                ins.setDueAmt(
                        rs.getBigDecimal("Due_Amt"));

                ins.setPaidAmt(
                        rs.getBigDecimal("Paid_Amt"));

                ins.setPaidDate(
                        rs.getDate("Paid_Date"));

                ins.setStatus(
                        rs.getString("status_"));

                list.add(ins);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
