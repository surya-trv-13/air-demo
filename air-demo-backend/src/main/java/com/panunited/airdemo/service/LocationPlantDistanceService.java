package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.DistanceTimeOptimizationRequest;
import com.panunited.airdemo.dto.DistanceTimeOptimizationResponse;
import com.panunited.airdemo.dto.LocationPlantDistanceTimeResponse;
import com.panunited.airdemo.mapper.LocationPlantDistanceTimeMapper;
import com.panunited.airdemo.models.LocationPlantDistanceTime;
import com.panunited.airdemo.models.Order;
import com.panunited.airdemo.repositories.LocationPlantDistanceTimeRepository;
import com.panunited.airdemo.utils.DistanceUtils;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class LocationPlantDistanceService {
    private final CalculateDistanceTimeService calculateDistanceTimeService;
    private final LocationPlantDistanceTimeRepository locationPlantDistanceTimeRepository;
    private final LocationPlantDistanceTimeMapper locationPlantDistanceTimeMapper;
    private final EntityManager entityManager;

    public void deletePreviousLocationPlantDistanceTime (Long orderId) {
        locationPlantDistanceTimeRepository.deleteAllByOrderId(orderId);
    }

    public List<LocationPlantDistanceTime> saveLocationPlantDistanceTime(List<LocationPlantDistanceTimeResponse> locationPlantDistanceTimeResponses,
                                                                         Long orderId) throws Exception {
        List<LocationPlantDistanceTime> locationPlantDistanceTimeList =
                locationPlantDistanceTimeResponses.stream()
                        .map(item -> {
                            LocationPlantDistanceTime locationPlantDistanceTime =
                                    locationPlantDistanceTimeMapper.locationPlantDistanceTimeResponsesToLocationPlantDistanceTime(item);
                            Order order = entityManager.getReference(Order.class, orderId);
                            locationPlantDistanceTime.setOrderId(order);
                            return locationPlantDistanceTime;
                        })
                        .filter(locationPlantDistanceTime ->
                                locationPlantDistanceTime.getDistance() != 0 && locationPlantDistanceTime.getTravellingTime() != 0)
                        .collect(Collectors.toList());

        if (locationPlantDistanceTimeList != null && !locationPlantDistanceTimeList.isEmpty()) {
            return locationPlantDistanceTimeRepository.saveAll(locationPlantDistanceTimeList);
        }

        return null;
    }

    public List<LocationPlantDistanceTime> getAndSaveLocationPlantDistanceTime(Long orderId,
                                                                               Double locationLatitude,
                                                                               Double locationLongitude,
                                                                               Map<String, Long> plantCodeAndPlantId,
                                                                               Integer hour) {
        List<LocationPlantDistanceTime> locationPlantDistanceTimeList = new ArrayList<>();
        DistanceTimeOptimizationRequest request = new DistanceTimeOptimizationRequest();
        request.setSiteCoordinate(List.of(String.valueOf(locationLatitude) + ", " + String.valueOf(locationLongitude)));
        request.setPlantCode(plantCodeAndPlantId.keySet().stream().collect(Collectors.toList()));
        request.setHour(hour);
        List<DistanceTimeOptimizationResponse> response = calculateDistanceTimeService.requestDistanceTimeOptimization(request);
        if (response != null) {
            for (DistanceTimeOptimizationResponse distanceTimeOfEachPlant : response) {
                if (distanceTimeOfEachPlant.getErrorMessage() == null &&
                        distanceTimeOfEachPlant.getDistance() != 0 &&
                        distanceTimeOfEachPlant.getTravellingTime() != 0) {
                    LocationPlantDistanceTime locationPlantDistanceTime = new LocationPlantDistanceTime();
                    Order order = entityManager.getReference(Order.class, orderId);
                    locationPlantDistanceTime.setOrderId(order);
                    locationPlantDistanceTime.setPlantId(plantCodeAndPlantId.get(distanceTimeOfEachPlant.getPlantCode()));
                    locationPlantDistanceTime.setDistance(DistanceUtils.convertMetersToKilometers(distanceTimeOfEachPlant.getDistance()));
                    locationPlantDistanceTime.setTravellingTime((double)distanceTimeOfEachPlant.getTravellingTime());
                    locationPlantDistanceTimeList.add(locationPlantDistanceTime);
                }
            }
        }

        if (locationPlantDistanceTimeList != null && !locationPlantDistanceTimeList.isEmpty()) {
            return locationPlantDistanceTimeRepository.saveAll(locationPlantDistanceTimeList);
        }

        return null;
    }
}
