package com.compassUol.ecommerce.mapper;

import com.compassUol.ecommerce.dtos.SaleDTO;
import com.compassUol.ecommerce.dtos.SaleItemDTO;
import com.compassUol.ecommerce.models.Sale;
import com.compassUol.ecommerce.models.SaleItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleMapper {

    public SaleDTO convertToDTO(Sale sale) {
        List<SaleItemDTO> itemDTOs = sale.getItems().stream()
                .map(this::convertToItemDTO)
                .toList();
        return new SaleDTO(
                sale.getId(),
                sale.getSaleDate(),
                itemDTOs,
                sale.getTotalAmount()
        );
    }

    public SaleItemDTO convertToItemDTO(SaleItem item) {
        return new SaleItemDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice()
        );
    }

    public Sale convertToEntity(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setTotalAmount(saleDTO.totalAmount());
        sale.setItems(
                saleDTO.items().stream()
                        .map(this::convertToItemEntity)
                        .toList()
        );
        return sale;
    }

    public SaleItem convertToItemEntity(SaleItemDTO itemDTO) {
        SaleItem item = new SaleItem();
        item.setQuantity(itemDTO.quantity());
        item.setPrice(itemDTO.price());
        return item;
    }
}