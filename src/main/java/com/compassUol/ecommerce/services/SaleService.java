package com.compassUol.ecommerce.services;

import com.compassUol.ecommerce.dtos.SaleDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface SaleService {

    SaleDTO saveSale(SaleDTO saleDTO);

    List<SaleDTO> getAllSales();

    SaleDTO getSaleById(Long id);

    SaleDTO updateSale(Long id, SaleDTO saleDTO);

    void deleteSale(Long id);

    List<SaleDTO> getSalesByDate(LocalDate date);

    List<SaleDTO> getMonthlyReport(int year, int month);
}