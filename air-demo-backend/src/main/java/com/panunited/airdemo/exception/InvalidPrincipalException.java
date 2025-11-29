package com.panunited.airdemo.exception;

@SuppressWarnings("serial")
public class InvalidPrincipalException extends Exception {
    public InvalidPrincipalException(String errorMessage) {
        super(errorMessage);
    }
}