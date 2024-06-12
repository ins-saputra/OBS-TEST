package com.obs.order.barang.exception;

public class OrderNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -2547655489582371324L;

    public OrderNotFoundException(String message){
        super(message);
    }
}
