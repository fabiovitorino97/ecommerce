package com.compassUol.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectProductPriceException extends RuntimeException {
    public IncorrectProductPriceException(String message) {
        super(message);
    }
}
