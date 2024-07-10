package com.compassUol.ecommerce.mapper;

import com.compassUol.ecommerce.dtos.ProductDTO;
import com.compassUol.ecommerce.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.isActive(),
                product.getCreatedAt()
        );
    }

    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setStock(productDTO.stock());
        product.setActive(product.isActive());

        return product;
    }
}
