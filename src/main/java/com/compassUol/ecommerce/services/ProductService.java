package com.compassUol.ecommerce.services;

import com.compassUol.ecommerce.dtos.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    ProductDTO register(ProductDTO productDTO);

    ProductDTO update(Long id, ProductDTO productDTO);

    void deactivate(Long id);
}
