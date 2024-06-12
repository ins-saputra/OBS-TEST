package com.obs.order.barang.exception;

public class InventoryNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 2611637536912190693L;

    public InventoryNotFoundException(String message){
        super(message);
    }
}
