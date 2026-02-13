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
                    "SELECT * FROM borrower_tbl WHERE Borrower_ID = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, borrowerID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                borrower = new Borrower();

                borrower.setBorrowerID(
                        rs.getString("Borrower_ID"));

                borrower.setFullName(
                        rs.getString("Full_Name"));

                borrower.setGender(
                        rs.getString("Gender"));

                borrower.setDateOfBirth(
                        rs.getDate("DOB"));

                borrower.setPrimaryPhone(
                        rs.getString("Primary_Phone"));

                borrower.setVillageOrArea(
                        rs.getString("Village_Area"));

                borrower.setRiskCategory(
                        rs.getString("Risk_Category"));

                borrower.setStatus(
                        rs.getString("Status_"));
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
                        rs.getString("Borrower_ID"));

                b.setFullName(
                        rs.getString("Full_Name"));

                b.setGender(
                        rs.getString("Gender"));

                b.setDateOfBirth(
                        rs.getDate("DOB"));

                b.setPrimaryPhone(
                        rs.getString("Primary_Phone"));

                b.setVillageOrArea(
                        rs.getString("Village_Area"));

                b.setRiskCategory(
                        rs.getString("Risk_Category"));

                b.setStatus(
                        rs.getString("Status_"));

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
            System.out.println(" ");
            System.out.println(b.toString());

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
                    "UPDATE borrower_tbl SET Status_ = ? WHERE Borrower_ID = ?";

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
                    "DELETE FROM borrower_tbl WHERE Borrower_ID = ?";

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
