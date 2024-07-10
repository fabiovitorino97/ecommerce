package com.compassUol.ecommerce.dtos;

import java.math.BigDecimal;

public record SaleItemDTO(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {
}