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
    @NotNull(message = "Type is required")
    private String typeFuel;
    @NotNull(message = "Added date is required")
    private LocalDate createdDate;
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    @NotNull(message = "price is required")
    private Double price;
}
