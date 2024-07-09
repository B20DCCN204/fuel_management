package com.giangtrung.fuel_management.repository;

import com.giangtrung.fuel_management.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long>{
    @Query("SELECT f FROM Fuel f WHERE f.createdDate BETWEEN ?1 AND ?2")
    List<Fuel> findFuelByDate(LocalDate startDate, LocalDate endDate);
}
