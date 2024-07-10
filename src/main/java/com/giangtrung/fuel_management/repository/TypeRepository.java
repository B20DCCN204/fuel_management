package com.giangtrung.fuel_management.repository;

import com.giangtrung.fuel_management.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TypeRepository extends JpaRepository<Type, String> {
}
