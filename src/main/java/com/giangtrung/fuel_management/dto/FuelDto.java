package com.giangtrung.fuel_management.dto;

import com.giangtrung.fuel_management.entity.Type;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuelDto {
    private Long id;
    @NotNull(message = "Type must be not null")
    private String typeFuel;
    @NotNull(message = "Added date must be not null")
    private LocalDate createdDate;
    @NotNull(message = "Quantity must be not null")
    private Integer quantity;
    @NotNull(message = "price must be not null")
    private Double price;
}
