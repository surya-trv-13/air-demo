package com.panunited.airdemo.mapper;

import com.panunited.airdemo.dto.LocationPlantDistanceTimeResponse;
import com.panunited.airdemo.models.LocationPlantDistanceTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface LocationPlantDistanceTimeMapper {
    LocationPlantDistanceTime locationPlantDistanceTimeResponsesToLocationPlantDistanceTime(LocationPlantDistanceTimeResponse locationPlantDistanceTimeResponse);
}
