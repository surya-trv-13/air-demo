package com.panunited.airdemo.validation;

import com.panunited.airdemo.dto.OrderAssociateProductItem;
import com.panunited.airdemo.dto.OrderBooking;
import com.panunited.airdemo.dto.OrderPlantItem;
import com.panunited.airdemo.dto.SaveBookingRequest;
import com.panunited.airdemo.enums.PlantType;
import com.panunited.airdemo.exception.InvalidRequestException;
import com.panunited.airdemo.exception.ResourceNotFoundException;
import com.panunited.airdemo.models.*;
import com.panunited.airdemo.repositories.*;
import com.panunited.airdemo.utils.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class OrderValidation {
    public static final int BATCHING_PLANT_MAX_CAPACITY = 5;
    private final CustomerRepository customerRepository;
    private final ProjectRepository projectRepository;
    private final LocationRepository locationRepository;
    private final ProductRepository productRepository;
    private final DischargeMethodRepository dischargeMethodRepository;
    private final StructureRepository structureRepository;
    private final OrderRepository orderRepository;
    private final MergeOrderRepository mergeOrderRepository;
    private final PlantRepository plantRepository;
    private final PlantAdditionalOffTimeRepository plantAdditionalOffTimeRepository;
    private final BatchingPlantRepository batchingPlantRepository;
    private final StrengthRepository strengthRepository;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final PumpRepository pumpRepository;
    private final StoneRepository stoneRepository;
    private final MaterialRepository materialRepository;

    public Customer validateAndGetCustomer(Long customerId) throws Exception {
        return customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Invalid Customer: customer (id = " + customerId + ") is not found."));
    }

    public Project validateAndGetProject(Long projectId) throws Exception {
        return projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Invalid Project: project (id = " + projectId + ") is not found."));
    }

    public Product validateAndGetProduct(Long productId) throws Exception {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Invalid Product: product (id = " + productId + ") is not found."));
    }

    public Location validateAndGetLocation(Long locationId) throws Exception {
        return locationRepository.findById(locationId).orElseThrow(() -> new ResourceNotFoundException("Invalid Location: location (id = " + locationId + ") is not found."));
    }

    public Order validateAndGetOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Invalid Order: order (id = " + orderId + ") is not found."));
        return order;
    }

    public Plant validateAndGetPlant(Long plantId) throws Exception {
        return plantRepository.findById(plantId).orElseThrow(() -> new ResourceNotFoundException("Invalid Plant: plant (id = " + plantId + ") is not found."));
    }

    public MergeOrder validateAndGetMergeOrder(Long mergeOrderId) throws Exception {
        Optional<MergeOrder> mergeOrder = mergeOrderRepository.findById(mergeOrderId);
        if (mergeOrder.isEmpty()) {
            throw new ResourceNotFoundException("Invalid Merge Order: mergeOrder (id = " + mergeOrder + ") is not found.");
        }
        return mergeOrder.get();
    }

    public void validateActivePlant(Long plantId) throws Exception {
        Optional<Boolean> activeStatus = plantRepository.findActiveStatusByPlantId(plantId);
        if (activeStatus.isEmpty()) {
            throw new ResourceNotFoundException("Invalid Plant: plant (id = " + plantId + ") is not found.");
        } else if (!activeStatus.get()) {
            throw new InvalidRequestException("Invalid Plant : plant (id = " + plantId + ") is not active.");
        }
    }

    public void validateActiveBP(Long plantId, Long bpId) throws Exception {
        Optional<Boolean> activeStatus = batchingPlantRepository.findActiveStatusByPlantIdAndBP(plantId, bpId);
        if (activeStatus.isEmpty()) {
            throw new ResourceNotFoundException("Invalid BP: bp (id = " + bpId + ") is not found under plant (id = " + plantId + ") chosen.");
        } else if (!activeStatus.get()) {
            throw new InvalidRequestException("Invalid BP: bp (id = " + bpId + ") under plant (id = " + plantId + ") is not active.");
        }
        Integer batchingPlantCapacity = deliveryOrderRepository.findActiveDeliveryOrderCountByBatchingPlantId(bpId);
        if (batchingPlantCapacity == BATCHING_PLANT_MAX_CAPACITY) {
            throw new InvalidRequestException("Target BP already has " + BATCHING_PLANT_MAX_CAPACITY + " DOs assigned.");
        }
    }

    public void validateDischargeMethodExistence(Long dischargeMethodId) throws Exception {
        if (!dischargeMethodRepository.existsById(dischargeMethodId)) {
            throw new ResourceNotFoundException("Invalid Discharge Method: discharge method (id = " + dischargeMethodId + ") is not found.");
        }
    }

    public void validateStructureExistence(Long structureId) throws Exception {
        if (!structureRepository.existsById(structureId)) {
            throw new ResourceNotFoundException("Invalid Structure: structure (id = " + structureId + ") is not found.");
        }
    }

    public void validateStrengthExistence(Long strengthId) throws Exception {
        if (!strengthRepository.existsById(strengthId)) {
            throw new ResourceNotFoundException("Invalid Strength: strength (id = " + strengthId + ") is not found.");
        }
    }

    public void validatePumpExistence(Long pumpId) throws Exception {
        if (!pumpRepository.existsById(pumpId)) {
            throw new ResourceNotFoundException("Invalid Pump: pump (id = " + pumpId + ") is not found.");
        }
    }

    public void validateStoneExistence(Long stoneId) throws Exception {
        if (!stoneRepository.existsById(stoneId)) {
            throw new ResourceNotFoundException("Invalid Stone: stone (id = " + stoneId + ") is not found.");
        }
    }

    public void validateMaterialExistence(Long materialId) throws Exception{
        if (!materialRepository.existsById(materialId)) {
            throw new ResourceNotFoundException("Invalid Associate Product: materialId (id = " + materialId + ") is not found.");
        }
    }

    public void validateOrderAssociateProduct(OrderBooking orderBooking) throws Exception {
        List<Long> selectedMaterialIds = new ArrayList<>();
        for (OrderAssociateProductItem associateProduct : orderBooking.getAssociateProducts()) {
            Long materialId = associateProduct.getMaterialId();
            if (orderBooking instanceof SaveBookingRequest) {
                // To cater old version of booking apps not updated yet to get by materialId
                if(materialId == null || materialId == 0) {
                    materialId = associateProduct.getAssociateProductId();
                    associateProduct.setMaterialId(materialId);
                }
            }
            validateMaterialExistence(materialId);
            if (selectedMaterialIds.contains(materialId)) {
                throw new InvalidRequestException(
                        "Invalid Associate Product: Duplicated Associate Products (materialId = "
                                + materialId + ") selected.");
            }
            selectedMaterialIds.add(materialId);
            if (associateProduct.getBundledAssociateProducts() != null) {
                for (OrderAssociateProductItem bundledAssociateProduct : associateProduct.getBundledAssociateProducts()) {
                    validateMaterialExistence(bundledAssociateProduct.getMaterialId());
                    if (selectedMaterialIds.contains(bundledAssociateProduct.getMaterialId())) {
                        throw new InvalidRequestException(
                                "Invalid Associate Product: Duplicated Associate Products (materialId = "
                                        + associateProduct.getMaterialId()+ ") selected.");
                    }
                    selectedMaterialIds.add(bundledAssociateProduct.getMaterialId());
                }
            }
        }
    }

    public void validateOrderQuantityAndProgressiveQuantity(Double progressiveQuantity, Double orderQuantity) throws Exception{
        if (progressiveQuantity != 0.0 && progressiveQuantity > orderQuantity) {
            throw new InvalidRequestException("Invalid Order Quantity: Progressive Quantity has exceeded current Order Quantity. Please adjust the order quantity.");
        }
    }

    public void validateAssignDeliveryOrderQuantityForNormalOrder(Double assignedDeliveryOrderQuantity,
                                                                  Double orderQuantity, Double assignDeliveryOrderQuantity, Long orderId) throws Exception {
        Double availableAssignDeliveryOrderQuantity = orderQuantity - assignedDeliveryOrderQuantity;
        if (availableAssignDeliveryOrderQuantity - assignDeliveryOrderQuantity < 0) {
            throw new InvalidRequestException(
                    "Invalid Assign Delivery Order: Assigned Quantity has exceeded. Please refresh for latest changes.");
        }
    }

    public void validateAssignDeliveryOrderQuantityForMergeOrder(Long mergeOrderId) throws Exception {
        Double availableAssignDeliveryOrderQuantity = deliveryOrderRepository.getUnassignedOrderQuantityForMergeOrder(mergeOrderId);
        if (availableAssignDeliveryOrderQuantity <= 0) {
            throw new InvalidRequestException("Invalid Assign Delivery Order: Merge order had completed assigned.");
        }
    }

    public void validateDeliveryTime(Long plantId, LocalDate deliveryDate, LocalTime deliveryTime) throws InvalidRequestException {
        if (plantId != null) {
            Optional<Plant> plant = plantRepository.findById(plantId);
            if (plant.isPresent()) {
                Plant p = plant.get();
                LocalTime openTime = p.getOpenTime();
                log.info("Plant Open Time: " + openTime);
                LocalTime closeTime = p.getCloseTime();
                log.info("Plant Close Time: " + closeTime);
                if (openTime == null || closeTime == null) {
                    throw new InvalidRequestException("Please Setup Plant opening and close time");
                }

                if (isWeekend(deliveryDate)) {
                    // Between 5am to 12pm (Saturday)
                    if (!DateTimeUtil.isBetweenTimeRange(deliveryTime,
                            LocalTime.parse("05:00:00"), // 5AM
                            LocalTime.parse("12:00:00") // 12PM
                    )) {
                        throw new InvalidRequestException(
                                "The delivery date time selected is after our weekend working hours (05:00 - 12:00). ");
                    }
                } else {
                    List<PlantAdditionalOffTime> offTimes = plantAdditionalOffTimeRepository.getByPlantId(plantId);
                    if (!offTimes.isEmpty()) {
                        LocalDateTime deliveryDateTime = LocalDateTime.of(deliveryDate, deliveryTime);
                        for (PlantAdditionalOffTime item : offTimes) {
                            if (DateTimeUtil.isWithinRange(deliveryDateTime, item.getOpenTime(), item.getCloseTime())) {
                                throw new InvalidRequestException(String.format(
                                        "The delivery date time selected falls within our %s Plant's off-time (%s - %s). ",
                                        p.getPlantName(), DateTimeUtil.convertToLocalDateTimeStringDDMMMYYYHHMM(item.getOpenTime()),
                                        DateTimeUtil.convertToLocalDateTimeStringDDMMMYYYHHMM(item.getCloseTime())));
                            }
                        }
                    }

                    if (!DateTimeUtil.isBetweenTimeRange(deliveryTime, openTime, closeTime)) {
                        throw new InvalidRequestException(String.format(
                                "The delivery date time selected is after our %s Plant working hour (%s - %s). ",
                                p.getPlantName(), openTime, closeTime));
                    }
                }
            }
        }
    }

    public void validateDeliveryTimeWarningOnly(List<String> warningMessages, Long plantId, LocalDate deliveryDate, LocalTime deliveryTime) {
        try {
            validateDeliveryTime(plantId, deliveryDate, deliveryTime);
        } catch (InvalidRequestException e) {
            warningMessages.add(e.getMessage());
        }
    }

    private boolean isWeekend(LocalDate localDate) {
        // get Day of week for the passed LocalDate
        return (localDate.get(ChronoField.DAY_OF_WEEK) == 6) // SATURDAY
//                || (localDate.get(ChronoField.DAY_OF_WEEK) == 7) // SUNDAY
                ;
    }

    public void validateCustomerRegion(Long customerId, Set<OrderPlantItem> assignedPlants) {
        Plant mainPlant = assignedPlants.stream()
                .filter(assignedPlant -> assignedPlant.getPlantType().equals(PlantType.MAIN_PLANT.name()))
                .findFirst()
                .flatMap(assignedPlant -> plantRepository.findById(assignedPlant.getPlantId()))
                .orElse(null);
        List<Long> regionIds = customerRepository.findAllCustomerRegions(customerId);
        if (mainPlant != null && !regionIds.contains(mainPlant.getRegion().getRegionId())) {
            throw new InvalidRequestException("Invalid Customer : Customer region is invalid.");
        }
    }
}
