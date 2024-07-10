package com.giangtrung.fuel_management.dto.response;

import com.giangtrung.fuel_management.dto.FuelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumeResponse {
    private List<FuelDto> fuels;
    private Double totalPrice;
}
