package com.mfi.util;

public class RepaymentProcessingException extends Exception {

    @Override
    public String toString() {
        return "Repayment cannot be processed.";
    }
}
