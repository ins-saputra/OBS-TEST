package com.obs.order.barang.exception;

public class InsufficientStockException extends RuntimeException{

    private static final long serialVersionUID = -5319522372334557418L;

    public InsufficientStockException(String message){
        super(message);
    }
}
