package com.compassUol.ecommerce.repositories;

import com.compassUol.ecommerce.models.Sale;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s WHERE s.saleDate BETWEEN :startOfDay AND :endOfDay")
    List<Sale> findByCreatedAtBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT s FROM Sale s WHERE YEAR(s.saleDate) = :year AND MONTH(s.saleDate) = :month")
    List<Sale> findBySaleDateYearAndMonth(@Param("year") int year, @Param("month") int month);
}