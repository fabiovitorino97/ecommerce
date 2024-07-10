package com.compassUol.ecommerce.exceptions;

public class IncorrectProductPriceException extends RuntimeException {
    public IncorrectProductPriceException(String message) {
        super(message);
    }
}
