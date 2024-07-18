package com.compassUol.ecommerce.services.Impl;

import com.compassUol.ecommerce.dtos.SaleDTO;
import com.compassUol.ecommerce.exceptions.InsufficientStockException;
import com.compassUol.ecommerce.exceptions.ProductNotActiveException;
import com.compassUol.ecommerce.exceptions.ProductNotFoundException;
import com.compassUol.ecommerce.exceptions.SaleNotFoundException;
import com.compassUol.ecommerce.mapper.SaleMapper;
import com.compassUol.ecommerce.models.Product;
import com.compassUol.ecommerce.models.Sale;
import com.compassUol.ecommerce.models.SaleItem;
import com.compassUol.ecommerce.models.User;
import com.compassUol.ecommerce.repositories.ProductRepository;
import com.compassUol.ecommerce.repositories.SaleRepository;
import com.compassUol.ecommerce.repositories.UserRepository;
import com.compassUol.ecommerce.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaleMapper saleMapper;

    @CacheEvict(value = "sales", allEntries = true)
    public SaleDTO saveSale(SaleDTO saleDTO) {
        Sale sale = new Sale();

        final BigDecimalWrapper totalAmountWrapper = new BigDecimalWrapper();

        List<SaleItem> items = saleDTO.items().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (!product.isActive()) {
                throw new ProductNotActiveException("Product is not active for sale");
            }

            if (product.getStock() < itemDTO.quantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - itemDTO.quantity());

            SaleItem item = new SaleItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity());
            item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.quantity())));

            totalAmountWrapper.add(item.getPrice());

            return item;
        }).collect(Collectors.toList());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = ((UserDetails) authentication.getPrincipal()).getUsername();

        User currentUser = userRepository.findByUsername(currentUserName);

        sale.setItems(items);
        sale.setTotalAmount(totalAmountWrapper.getTotalAmount());
        sale.setUser(currentUser);
        sale = saleRepository.save(sale);

        return saleMapper.convertToDTO(sale);
    }

    @Cacheable(value = "sales")
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        return sales.stream()
                .map(saleMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "sales", key = "#id")
    public SaleDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));
        return saleMapper.convertToDTO(sale);
    }

    @CachePut(value = "sales", key = "#id")
    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("Sale not found"));

        for (SaleItem oldItem : existingSale.getItems()) {
            Product product = oldItem.getProduct();
            product.setStock(product.getStock() + oldItem.getQuantity());
            productRepository.save(product);
        }

        List<SaleItem> updatedItems = saleDTO.items().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (!product.isActive()) {
                throw new ProductNotActiveException("Product is not active for sale");
            }

            if (product.getStock() < itemDTO.quantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }
            product.setStock(product.getStock() - itemDTO.quantity());
            productRepository.save(product);

            SaleItem item = new SaleItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.quantity());
            item.setPrice(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.quantity())));
            return item;
        }).collect(Collectors.toList());

        existingSale.setItems(updatedItems);
        existingSale.setTotalAmount(updatedItems.stream()
                .map(SaleItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        saleRepository.save(existingSale);
        return saleMapper.convertToDTO(existingSale);
    }

    @CacheEvict(value = "sales", allEntries = true)
    public void deleteSale(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new SaleNotFoundException("Sale not found");
        }
        saleRepository.deleteById(id);
    }

    @Cacheable(value = "sales", key = "#date")
    public List<SaleDTO> getSalesByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        List<Sale> sales = saleRepository.findByCreatedAtBetween(startOfDay, endOfDay);
        if (sales.isEmpty()) throw new SaleNotFoundException("No sales was found");
        return sales.stream()
                .map(saleMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "sales", key = "'monthlyReport-' + #year + '-' + #month")
    public List<SaleDTO> getMonthlyReport(int year, int month) {
        List<Sale> sales = saleRepository.findBySaleDateYearAndMonth(year, month);
        if (sales.isEmpty()) throw new SaleNotFoundException("No sales was found");
        return sales.stream()
                .map(saleMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    private static class BigDecimalWrapper {
        private BigDecimal totalAmount;

        public BigDecimalWrapper() {
            this.totalAmount = BigDecimal.ZERO;
        }

        public void add(BigDecimal amount) {
            this.totalAmount = this.totalAmount.add(amount);
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }
    }
}