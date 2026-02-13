package com.mfi.util;

public class ValidationException extends Exception {

    @Override
    public String toString() {
        return "Invalid Input. Please check your data.";
    }
}
