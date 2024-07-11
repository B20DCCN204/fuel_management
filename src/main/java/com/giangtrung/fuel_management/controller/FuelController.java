package com.giangtrung.fuel_management.controller;

import com.giangtrung.fuel_management.dto.FuelDto;
import com.giangtrung.fuel_management.dto.request.DateRangeRequest;
import com.giangtrung.fuel_management.dto.response.ConsumeResponse;
import com.giangtrung.fuel_management.dto.response.ResponseData;
import com.giangtrung.fuel_management.service.FuelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fuel")
@Slf4j
@Validated
public class FuelController {
    @Autowired
    private FuelService fuelService;

    // Add a new fuel record
    @PostMapping()
    public ResponseData<FuelDto> addFuel(@Valid @RequestBody FuelDto fuelDto){
        log.info("Received request to add fuel: {}", fuelDto);
        FuelDto result = fuelService.saveFuel(fuelDto);
        log.info("Fuel added successfully: {}", result);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Fuel added successfully", result);
    }

    // Get fuels within a specific date range
    @GetMapping("/fuels-by-date-range")
    public ResponseData<Map<String, List<FuelDto>>> getFuelsByDateRange(@Valid @RequestBody DateRangeRequest dateRange){
        log.info("Received request to get fuels by date range: start {}, end {}", dateRange.getStartDate(), dateRange.getEndDate());
        Map<String, List<FuelDto>> result = fuelService.getFuelsByDateRange(dateRange);
        log.info("Fuels retrieved successfully for date range: start {}, end {}", dateRange.getStartDate(), dateRange.getEndDate());
        return new ResponseData<>(HttpStatus.OK.value(), "Get fuels by date range successfully", result);
    }

    // Get fuels base on consumption quantities
    @GetMapping("/fuels-by-consume-quantity")
    public ResponseData<Map<String, ConsumeResponse>> getFuelsByConsume(@RequestBody Map<String, Integer> consume){
        log.info("Received request to get fuels by consume: {}", consume);
        Map<String, ConsumeResponse> result = fuelService.getFuelsByConsume(consume);
        log.info("Fuels retrieved successfully for consume: {}", consume);
        return new ResponseData<>(HttpStatus.OK.value(), "Get fuels by consume quantity successfully", result);
    }

}
