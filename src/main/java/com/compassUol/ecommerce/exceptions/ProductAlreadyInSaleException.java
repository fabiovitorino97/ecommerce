package com.compassUol.ecommerce.exceptions;

public class ProductAlreadyInSaleException extends RuntimeException {
    public ProductAlreadyInSaleException(String message) {
        super(message);
    }
}
