package com.giangtrung.fuel_management.service;

import com.giangtrung.fuel_management.dto.FuelDto;
import com.giangtrung.fuel_management.entity.Fuel;
import com.giangtrung.fuel_management.exception.AppException;
import com.giangtrung.fuel_management.exception.ErrorCode;
import com.giangtrung.fuel_management.repository.FuelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FuelService {
    FuelRepository fuelRepository;

    public Map<String, List<FuelDto>> getFuelList(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new AppException(ErrorCode.DATE_NOT_NULL);
        }
        if (startDate.isAfter(endDate)) {
            throw new AppException(ErrorCode.START_BEFORE_END);
        }
        List<Fuel> fuels = fuelRepository.findFuelByDate(startDate, endDate);
        Map<String, List<FuelDto>> fuelDtosGroupedByTypeId = new HashMap<>();
        for (Fuel fuel1 : fuels) {
            FuelDto dto = new FuelDto();
            dto.setQuantity(fuel1.getQuantity());
            dto.setCreatedDate(fuel1.getCreatedDate());
            dto.setPrice(fuel1.getPrice());
            dto.setTypeId((fuel1.getType().getId()));
            FuelDto apply = dto;
            fuelDtosGroupedByTypeId.computeIfAbsent(apply.getTypeId(), k -> new ArrayList<>()).add(apply);
        }
        return fuelDtosGroupedByTypeId;
    }

    public Map<String, List<FuelDto>> getFuelRecently(Map<String, Integer> fuelQuantities) {
        List<Fuel> fuels = fuelRepository.findAll(); // Adjust based on your data source
        Map<String, List<FuelDto>> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : fuelQuantities.entrySet()) {
            String typeId = entry.getKey();
            Integer quantityNeeded = entry.getValue();

            List<Fuel> sortedFuels = fuels.stream()
                    .filter(fuel -> fuel.getType().getId().equals(typeId))
                    .sorted((f1, f2) -> f2.getCreatedDate().compareTo(f1.getCreatedDate())) // Sort by date descending
                    .collect(Collectors.toList());
            int totalAvailable = sortedFuels.stream().mapToInt(Fuel::getQuantity).sum();
            if (totalAvailable < quantityNeeded) {
                throw new IllegalArgumentException(ErrorCode.NOT_ENOUGH_FUEL.getMessage());
            }

            List<FuelDto> fuelDtos = new ArrayList<>();
            for (Fuel fuel : sortedFuels) {
                if (quantityNeeded <= 0) {
                    break;
                }
                FuelDto dto = new FuelDto();
                dto.setTypeId(fuel.getType().getId());
                dto.setCreatedDate(fuel.getCreatedDate());
                dto.setPrice(fuel.getPrice());

                int quantityToTake = Math.min(quantityNeeded, fuel.getQuantity());
                dto.setQuantity(quantityToTake);

                fuelDtos.add(dto);
                quantityNeeded -= quantityToTake;
            }

            result.put(typeId, fuelDtos);
        }

        return result;
    }



}