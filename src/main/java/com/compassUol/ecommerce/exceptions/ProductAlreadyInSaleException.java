package com.compassUol.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductAlreadyInSaleException extends RuntimeException {
    public ProductAlreadyInSaleException(String message) {
        super(message);
    }
}
