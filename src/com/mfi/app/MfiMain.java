package com.mfi.app;

import com.mfi.bean.Borrower;
import com.mfi.bean.Loan;
import com.mfi.service.MfiService;
import com.mfi.util.ValidationException;

import java.util.Scanner;

public class MfiMain {

    private static MfiService service = new MfiService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("--- Microfinance Loan & Repayment Console ---");

        String bid = "BR" + (System.currentTimeMillis() % 100000);



        try {

            Borrower b = new Borrower();

           // b.setBorrowerID("BR2001");

            b.setBorrowerID(bid);

            b.setFullName("Nalini Devi");
            b.setGender("FEMALE");
            b.setDateOfBirth(java.sql.Date.valueOf("1965-03-15"));
            b.setPrimaryPhone("9998887771");
            b.setVillageOrArea("Sunrise Colony");
            b.setRiskCategory("LOW");
            b.setStatus("ACTIVE");

            boolean ok = service.registerNewBorrower(b);

            System.out.println(ok
                    ? "BORROWER REGISTERED"
                    : "BORROWER REGISTRATION FAILED");

        } catch (ValidationException e) {

            System.out.println("Validation Error: " + e.toString());

        } catch (Exception e) {

            System.out.println("System Error: " + e.getMessage());
        }


        /* ================= DEMO 2: Create Loan ================= */

        try {

            Loan loan = new Loan();

            //loan.setLoanID("LN2025-010");
            loan.setBorrowerID(bid);
            loan.setProductName("Small Business Loan");

            loan.setPrincipalAmount(
                    new java.math.BigDecimal("40000.00")
            );

            loan.setAnnualInterestRate(
                    new java.math.BigDecimal("18.00")
            );

            loan.setTermMonths(12);
            loan.setRepaymentFrequency("MONTHLY");

            loan.setDisbursementDate(
                    new java.sql.Date(System.currentTimeMillis())
            );

            loan.setOutstandingPrinciple(
                    new java.math.BigDecimal("40000.00")
            );

            loan.setStatus("ACTIVE");

            boolean ok = service.createLoanAccount(loan);

            System.out.println(ok
                    ? "LOAN CREATED"
                    : "LOAN CREATION FAILED");

        } catch (ValidationException e) {

            System.out.println("Validation Error: " + e.toString());

        } catch (Exception e) {

            System.out.println("System Error: " + e.getMessage());
        }


        sc.close();
    }
}
