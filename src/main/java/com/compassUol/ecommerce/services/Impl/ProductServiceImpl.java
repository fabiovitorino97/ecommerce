package com.compassUol.ecommerce.services.Impl;

import com.compassUol.ecommerce.dtos.ProductDTO;

import com.compassUol.ecommerce.exceptions.IncorrectProductPriceException;
import com.compassUol.ecommerce.exceptions.ProductNotFoundException;
import com.compassUol.ecommerce.mapper.ProductMapper;
import com.compassUol.ecommerce.models.Product;
import com.compassUol.ecommerce.repositories.ProductRepository;
import com.compassUol.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> findAll() {
        return productRepository.findByActiveTrue()
                .stream().map(productMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return productMapper.convertToDTO(product);
    }

    public ProductDTO register(ProductDTO productDTO) {
        if(productDTO.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IncorrectProductPriceException("Product price must be positive");
        }

        Product product = productMapper.convertToEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.convertToDTO(product);
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        existingProduct.setName(productDTO.name());
        existingProduct.setPrice(productDTO.price());
        existingProduct.setStock(productDTO.stock());
        existingProduct = productRepository.save(existingProduct);

        return productMapper.convertToDTO(existingProduct);
    }

    public void deactivate(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }
}
