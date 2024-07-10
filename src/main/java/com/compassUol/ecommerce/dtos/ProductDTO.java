package com.compassUol.ecommerce.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(
        Long id,
        String name,
        BigDecimal price,
        Integer stock,
        Boolean active,
        LocalDateTime createdAt
) {
}
