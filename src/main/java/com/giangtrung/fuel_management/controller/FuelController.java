package com.giangtrung.fuel_management.controller;

import com.giangtrung.fuel_management.dto.ApiResponse;
import com.giangtrung.fuel_management.dto.FuelDto;
import com.giangtrung.fuel_management.entity.DateRange;
import com.giangtrung.fuel_management.entity.Fuel;
import com.giangtrung.fuel_management.repository.FuelRepository;
import com.giangtrung.fuel_management.service.FuelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fuels")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FuelController {

   FuelService fuelService;

    @GetMapping("/date")
    public ApiResponse<Map<String, List<FuelDto>>> getFuelList(@RequestBody DateRange dateRange) {
        Map<String, List<FuelDto>> result = fuelService.getFuelList(dateRange.getStartDate(), dateRange.getEndDate());
        return new ApiResponse<>(1000, "Success", result);
    }

    @GetMapping("/recent")
    public ApiResponse<Map<String, List<FuelDto>>> getFuelRecently(@RequestBody Map<String, Integer> fuelQuantities) {
        Map<String, List<FuelDto>> result = fuelService.getFuelRecently(fuelQuantities);
        return new ApiResponse<>(1000, "Success", result);
    }


}
