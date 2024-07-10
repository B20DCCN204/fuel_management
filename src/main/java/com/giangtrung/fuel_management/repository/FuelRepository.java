package com.giangtrung.fuel_management.repository;

import com.giangtrung.fuel_management.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FuelRepository extends JpaRepository<Fuel, Long> {
    @Query("SELECT f FROM Fuel f WHERE DATE(f.createdDate) BETWEEN :startDate AND :endDate")
    List<Fuel> findByDateRange(@Param("startDate") LocalDate startDate ,@Param("endDate") LocalDate endDate);

    List<Fuel> findByTypeIdOrderByCreatedDateDesc(String typeid);
}
