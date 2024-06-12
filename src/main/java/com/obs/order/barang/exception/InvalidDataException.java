package com.obs.order.barang.exception;

public class InvalidDataException extends RuntimeException{
    private static final long serialVersionUID = 648904525044685070L;

    public InvalidDataException(String message) {
        super(message);
    }

}
