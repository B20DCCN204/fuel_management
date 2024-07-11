package com.giangtrung.fuel_management.service;

import com.giangtrung.fuel_management.dto.FuelDto;
import com.giangtrung.fuel_management.dto.request.DateRangeRequest;
import com.giangtrung.fuel_management.dto.response.ConsumeResponse;

import java.util.List;
import java.util.Map;

public interface FuelService {
    FuelDto saveFuel(FuelDto fuelDto);
    Map<String, List<FuelDto>> getFuelsByDateRange(DateRangeRequest dateRangeRequest);
    Map<String, ConsumeResponse> getFuelsByConsume(Map<String, Integer> consume);
}
