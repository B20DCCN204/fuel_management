package com.giangtrung.fuel_management.mapper;

import com.giangtrung.fuel_management.dto.FuelDto;
import com.giangtrung.fuel_management.entity.Fuel;
import com.giangtrung.fuel_management.entity.Type;
import com.giangtrung.fuel_management.repository.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FuelMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TypeRepository typeRepository;

    // Convert from fuel entity to fuelDto
    public FuelDto toFuelDto(Fuel fuel){
        if(fuel == null){
            log.error("Input parameter 'fuel' is null");
            throw new NullPointerException("Input parameter 'fuel' is null");
        }
        log.debug("Mapping Fuel to FuelDto: {}", fuel);
        FuelDto fuelDto = modelMapper.map(fuel, FuelDto.class);
        fuelDto.setTypeFuel(fuel.getType().getId());
        log.debug("Mapped FuelDto: {}", fuelDto);
        return fuelDto;
    }

    // Convert from fuelDto to fuel entity
    public Fuel toFuel(FuelDto fuelDto){
        if(fuelDto == null){
            log.error("Input parameter 'fuelDto' is null");
            throw new NullPointerException("Input parameter 'fuelDto' is null");
        }
        log.debug("Mapping FuelDto to Fuel: {}", fuelDto);
        Type type = typeRepository.findById(fuelDto.getTypeFuel()).orElseThrow(
                () ->{
                    log.error("Type not found for id: {}", fuelDto.getTypeFuel());
                    return new RuntimeException("Type not found");
                }
        );
        Fuel fuel = modelMapper.map(fuelDto, Fuel.class);
        fuel.setType(type);
        log.debug("Mapped Fuel: {}", fuel);
        return fuel;
    }
}
