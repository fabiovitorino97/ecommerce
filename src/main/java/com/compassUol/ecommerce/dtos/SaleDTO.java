package com.compassUol.ecommerce.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleDTO(
        Long id,
        LocalDateTime saleDate,
        List<SaleItemDTO> items,
        BigDecimal totalAmount
) {
}