package com.giangtrung.fuel_management.service.impl;

import com.giangtrung.fuel_management.dto.FuelDto;
import com.giangtrung.fuel_management.dto.request.DateRangeRequest;
import com.giangtrung.fuel_management.dto.response.ConsumeResponse;
import com.giangtrung.fuel_management.entity.Fuel;
import com.giangtrung.fuel_management.entity.Type;
import com.giangtrung.fuel_management.exception.InsufficientQuantityException;
import com.giangtrung.fuel_management.mapper.FuelMapper;
import com.giangtrung.fuel_management.repository.FuelRepository;
import com.giangtrung.fuel_management.repository.TypeRepository;
import com.giangtrung.fuel_management.service.FuelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FuelServiceImpl implements FuelService {
    @Autowired
    private FuelRepository fuelRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private FuelMapper fuelMapper;

    /**
     * Saves a new fuel record.
     *
     * @param fuelDto the fuel data transfer object containing fuel details.
     * @return the saved FuelDto.
     */
    @Override
    @Transactional
    public FuelDto saveFuel(FuelDto fuelDto) {
        log.info("Starting saveFuel with FuelDto: {}", fuelDto);
        Fuel fuel = fuelRepository.save(fuelMapper.toFuel(fuelDto));
        FuelDto result = fuelMapper.toFuelDto(fuel);
        log.info("Finished saveFuel with result: {}", result);
        return result;
    }

    /**
     * Retrieves fuels within a specific date range.
     *
     * @param dateRangeRequest the date range request containing start and end dates.
     * @return a map of type IDs to lists of FuelDto within the date range.
     */
    @Override
    public Map<String, List<FuelDto>> getFuelsByDateRange(DateRangeRequest dateRangeRequest) {
        log.info("Starting getFuelsByDateRange with Date range: start {}, end {}", dateRangeRequest.getStartDate(), dateRangeRequest.getEndDate());
        Map<String, List<FuelDto>> result = new HashMap<>();

        // get list fuel by date range
        List<Fuel> fuels = fuelRepository.findByDateRange(dateRangeRequest.getStartDate(), dateRangeRequest.getEndDate());
        List<Type> types = typeRepository.findAll();
        log.debug("Found {} fuels and {} types", fuels.size(), types.size());

        List<FuelDto> fuelDtos = new ArrayList<>();

        // filter via type
        for(Type type : types){
            fuelDtos = fuels.stream()
                    .filter(it -> it.getType().equals(type))
                    .map(it -> fuelMapper.toFuelDto(it))
                    .collect(Collectors.toList());
            result.put(type.getId(), fuelDtos);
        }

        log.info("Finished getFuelsByDateRange with result: {}", result);
        return result;
    }

    /**
     * Retrieves fuels based on consumption quantities.
     *
     * @param consume a map where the key is the type ID and the value is the required quantity.
     * @return a map of type IDs to ConsumeResponse containing the list of FuelDto and total price.
     * @throws InsufficientQuantityException if the required quantity is more than available quantity.
     */
    @Override
    public Map<String, ConsumeResponse> getFuelsByConsume(Map<String, Integer> consume) {
        log.info("Starting getFuelsByConsume with consume: {}", consume);
        Map<String, ConsumeResponse> result = new HashMap<>();

        String typeId;
        int requiredQuantity = 0;

        // Check consumes ("typeId" : requiredQuantity)
        for(Map.Entry<String, Integer> entry : consume.entrySet()){
            typeId = entry.getKey();
            requiredQuantity = entry.getValue();
            log.debug("Processing typeId: {} with requiredQuantity: {}", typeId, requiredQuantity);

            List<Fuel> fuels = fuelRepository.findByTypeIdOrderByCreatedDateDesc(typeId);

            result.put(typeId, createConsumeResponse(fuels, typeId, requiredQuantity));
        }
        log.info("Finished getFuelsByConsume with result: {}", result);
        return result;
    }
    private ConsumeResponse createConsumeResponse(List<Fuel> fuels, String typeId, int requiredQuantity){
        log.debug("Starting getConsumeResponse for typeId: {} with requiredQuantity: {}", typeId, requiredQuantity);
        List<FuelDto> consumedFuels = new ArrayList<>();
        double totalPrice = 0;
        int tmpQuantity = 0;

        for(Fuel fuel : fuels){
            int remainingQuantity = requiredQuantity - tmpQuantity;
            int availableQuantity = fuel.getQuantity();

            if(remainingQuantity <= 0) break;

            if(availableQuantity > 0){
                int takenQuantity = Math.min(fuel.getQuantity(), remainingQuantity);
                FuelDto fuelDto = fuelMapper.toFuelDto(fuel);
                fuelDto.setQuantity(takenQuantity);

                consumedFuels.add(fuelDto);
                tmpQuantity += takenQuantity;
                totalPrice += takenQuantity * fuel.getPrice();
            }
        }

        if(tmpQuantity < requiredQuantity){
            log.error("Insufficient quantity for typeId: {}", typeId);
            throw new InsufficientQuantityException("Not enough quantity available for type ID: " + typeId);
        }

        ConsumeResponse consumeResponse = new ConsumeResponse();
        consumeResponse.setFuels(consumedFuels);
        consumeResponse.setTotalPrice(totalPrice);
        log.debug("Finished getConsumeResponse with result: {}", consumeResponse);
        return consumeResponse;
    }
}
