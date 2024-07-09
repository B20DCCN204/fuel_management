package com.giangtrung.fuel_management.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class FuelDto {
    public FuelDto() {
    }

    private Integer quantity;

    private LocalDate createdDate;

    private Double Price;
    private String typeId;

    public FuelDto(Integer quantity, LocalDate createdDate, Double price, String typeId) {
        this.quantity = quantity;
        this.createdDate = createdDate;
        Price = price;
        this.typeId = typeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
