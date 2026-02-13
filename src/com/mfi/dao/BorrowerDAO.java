package com.mfi.dao;

import com.mfi.bean.Borrower;
import com.mfi.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO {


    public Borrower findBorrower(String borrowerID) {

        Borrower borrower = null;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "SELECT * FROM borrower_tbl WHERE borrower_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, borrowerID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                borrower = new Borrower();

                borrower.setBorrowerID(
                        rs.getString("borrower_id"));

                borrower.setFullName(
                        rs.getString("full_name"));

                borrower.setGender(
                        rs.getString("gender"));

                borrower.setDateOfBirth(
                        rs.getDate("date_of_birth"));

                borrower.setPrimaryPhone(
                        rs.getString("primary_phone"));

                borrower.setVillageOrArea(
                        rs.getString("village_area"));

                borrower.setRiskCategory(
                        rs.getString("risk_category"));

                borrower.setStatus(
                        rs.getString("status"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return borrower;
    }



    public List<Borrower> viewAllBorrowers() {

        List<Borrower> list = new ArrayList<>();

        try {

            Connection con = DBUtil.getDBConnection();

            String sql = "SELECT * FROM borrower_tbl";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Borrower b = new Borrower();

                b.setBorrowerID(
                        rs.getString("borrower_id"));

                b.setFullName(
                        rs.getString("full_name"));

                b.setGender(
                        rs.getString("gender"));

                b.setDateOfBirth(
                        rs.getDate("date_of_birth"));

                b.setPrimaryPhone(
                        rs.getString("primary_phone"));

                b.setVillageOrArea(
                        rs.getString("village_area"));

                b.setRiskCategory(
                        rs.getString("risk_category"));

                b.setStatus(
                        rs.getString("status"));

                list.add(b);
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }




    public boolean insertBorrower(Borrower b) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "INSERT INTO borrower_tbl VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, b.getBorrowerID());
            ps.setString(2, b.getFullName());
            ps.setString(3, b.getGender());
            ps.setDate(4, b.getDateOfBirth());
            ps.setString(5, b.getPrimaryPhone());
            ps.setString(6, b.getVillageOrArea());
            ps.setString(7, b.getRiskCategory());
            ps.setString(8, b.getStatus());

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



    public boolean updateBorrowerStatus(String borrowerID,
                                        String status) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "UPDATE borrower_tbl SET status = ? WHERE borrower_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setString(2, borrowerID);

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



    public boolean deleteBorrower(String borrowerID) {

        boolean result = false;

        try {

            Connection con = DBUtil.getDBConnection();

            String sql =
                    "DELETE FROM borrower_tbl WHERE borrower_id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, borrowerID);

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
}
