package com.compassUol.ecommerce.exceptions;

public class ProductNotActiveException extends RuntimeException {
    public ProductNotActiveException(String message) {
        super(message);
    }
}
