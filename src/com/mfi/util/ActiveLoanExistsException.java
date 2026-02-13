package com.mfi.util;

public class ActiveLoanExistsException extends Exception {

    public String toString() {
        return "Active loan exists. Please clear loans first.";
    }
}
