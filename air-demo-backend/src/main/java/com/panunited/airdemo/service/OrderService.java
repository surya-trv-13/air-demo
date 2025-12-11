package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.*;
import com.panunited.airdemo.enums.AuditOrderAssociateProductStatus;
import com.panunited.airdemo.exception.InvalidRequestException;
import com.panunited.airdemo.exception.ResourceNotFoundException;
import com.panunited.airdemo.mapper.OrderDetailListMapper;
import com.panunited.airdemo.mapper.OrderMapper;
import com.panunited.airdemo.models.*;
import com.panunited.airdemo.repositories.*;
import com.panunited.airdemo.utils.DateTimeUtil;
import com.panunited.airdemo.validation.OrderValidation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@Configuration
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailListMapper orderDetailListMapper;
    private final EntityManager entityManager;
    private final OrderMapper orderMapper;
    private final OrderValidation orderValidation;
    private final OrderPlantRepository orderPlantRepository;
    private final OrderTruckRepository orderTruckRepository;
    private final OrderPlanRepository orderPlanRepository;
    private final OrderCylinderTestRepository orderCylinderTestRepository;
    private final OrderAssociateProductRepository orderAssociateProductRepository;
    private final AuditOrderAssociateProductRepository auditOrderAssociateProductRepository;
    private final TruckTypeRepository truckTypeRepository;
    private final PlantService plantService;
    private final GeneratePlantOrderNumberRepository generatePlantOrderNoRepo;
    private final AuditOrderTruckRepository auditOrderTruckRepository;
    private final AuditOrderRepository auditOrderRepository;
    private final AuditOrderPlantRepository auditOrderPlantRepository;
    private final LocationPlantDistanceService locationPlantDistanceService;
    private final AuditOrderCylinderTestRepository auditOrderCylinderTestRepository;
    private final AuditOrderCylinderTestAgeRepository auditOrderCylinderTestAgeRepository;
    private final CustomerContactService customerContactService;
    private final PurchaseOrderService purchaseOrderService;

    private static final Integer SEQUENCE_OF_FIRST_LAYER_ORDER = 1;


    public List<OrderDetailListResponse> getAllOrders(LocalDate orderDateStart, LocalDate orderDateEnd, Long regionId, Long userId) {
        List<OrderListResponseProjection> orderProjection = orderRepository.findOrderListByOrderDateAndRegionId(orderDateStart, orderDateEnd, regionId);

        // Convert Projection to ListResponse
        List<OrderListResponse> orders = orderProjection.stream()
                .map(orderDetailListMapper::fromProjection)
                .toList();

        List<Long> allNormalOrderId = orders.stream().filter(order -> !Boolean.TRUE.equals(order.getMergingOrder())).map(OrderListResponse::getOrderId).toList();
        List<OrderTruckItem> orderIdWithTruck = new ArrayList<>();
        if (!allNormalOrderId.isEmpty()) {
            orderIdWithTruck = orderRepository.findAllTruck(allNormalOrderId);
        }

        // Filter only User ID and Admin Orders
//        List<OrderListResponse> resultantOrder = orders.stream ().filter (order -> order.getCreatedBy () == 10 || order.getCreatedBy ().equals (userId)).toList ();

        List<OrderDetailListResponse> orderDetails = orderDetailListMapper.fromOrderListResponse(orders);
        for (OrderDetailListResponse order : orderDetails) {
            if (Boolean.TRUE.equals(order.getMergingOrder())) {
                continue;
            }
            long orderId = order.getOrderId();
            order.setFleetGroupTrucks(orderIdWithTruck.stream().filter(item -> item.getOrderId().equals(orderId)).toList());
        }
        return orderDetails;
    }

    @Transactional
    public OrderSaveResponse saveOrderBooking(OrderBooking orderBooking, long userId) throws Exception{
        if(isDemoUsers (userId)) {
            Order order = orderRepository.findByCreatedBy (userId);
            orderBooking.setId (order.getId ());
        }
        System.out.println("Start update order **********************************************");
        List<String> warningMessages = new ArrayList<>();
        if (orderBooking.getAssignedPlants() != null && !orderBooking.getAssignedPlants().isEmpty() && orderBooking instanceof OrderSaveRequest) {
            OrderPlantItem mainPlant = orderBooking.getAssignedPlants().stream().toList().get(0);
        }
        Customer customer = orderValidation.validateAndGetCustomer(orderBooking.getCustomerId());
        orderValidation.validateCustomerRegion(orderBooking.getCustomerId(), orderBooking.getAssignedPlants());
        Project project = orderValidation.validateAndGetProject(orderBooking.getProjectId());

        Product product = null;
        if (orderBooking.getProductId() != null) {
            product = orderValidation.validateAndGetProduct(orderBooking.getProductId());
        }
        if (orderBooking.getLocationId() != null) {
            orderValidation.validateAndGetLocation(orderBooking.getLocationId());
        }
        if (orderBooking.getDischargeMethodId() != null) {
            orderValidation.validateDischargeMethodExistence(orderBooking.getDischargeMethodId());
        }
        if (orderBooking.getStructureId() != null) {
            orderValidation.validateStructureExistence(orderBooking.getStructureId());
        }
        if (orderBooking.getPumpId() != null) {
            orderValidation.validatePumpExistence(orderBooking.getPumpId());
        }
        if (orderBooking.getStoneId() != null) {
            orderValidation.validateStoneExistence(orderBooking.getStoneId());
        }
        orderValidation.validateOrderAssociateProduct(orderBooking);

        // prepare and save data
        Order editedOrder = getOrderUsingRequestObject(orderBooking, false);
        Order orderFromDB = orderValidation.validateAndGetOrder(orderBooking.getId());

        editedOrder.setCustomer(customer);
        editedOrder.setProject(project);
        editedOrder.setStretchProfile(false);
        editedOrder.setProduct(product);
        editedOrder.setAlertEdoID(0);

        CustomerContact customerContact = entityManager.getReference(CustomerContact.class, orderBooking.getContact().getId());
        editedOrder.setCustomerContact(customerContact);

        Location location = entityManager.getReference(Location.class, orderBooking.getLocationId());

        editedOrder.setLatitude(location.getLatitude());
        editedOrder.setLongitude(location.getLongitude());
        editedOrder.setLocation(location);

        Structure structure = entityManager.getReference(Structure.class, orderBooking.getStructureId());
        editedOrder.setStructure(structure);

        if (orderFromDB.getProgressiveQuantity() != null) {
            orderValidation.validateOrderQuantityAndProgressiveQuantity(orderFromDB.getProgressiveQuantity(), editedOrder.getOrderQuantity());
        }
        if (orderBooking.getContact() != null) {
            checkAndSetContact(editedOrder, orderBooking.getContact());
        }
        if (orderBooking.getPurchaseOrder() != null) {
            checkAndSetPurchaseOrder(editedOrder, orderBooking.getPurchaseOrder(), orderBooking.getProjectId());
        }
        System.out.println("update order No " + orderFromDB.getOrderNo() + " ORDER DATE :" + orderFromDB.getOrderDate() );
        if (orderFromDB.getOrderNo() == null || editedOrder.getOrderDate().compareTo(orderFromDB.getOrderDate()) != 0) {
            editedOrder.setOrderNo(generatePlantOrderNumber(editedOrder.getMainPlantId() , editedOrder.getOrderDate()));
        } else {
            editedOrder.setOrderNo(orderFromDB.getOrderNo());
        }
        if (editedOrder.getOrderDateTimeUtc() != null && !editedOrder.getOrderDateTimeUtc().isEqual(orderFromDB.getOrderDateTimeUtc())) {
            orderPlanRepository.deleteAllByOrderId(orderBooking.getId());
        }
        editedOrder.setHourlyRequirement(OrderAutoCalculateService.getHourlyRequirement(orderBooking.getIntervals(), orderBooking.getOrderQuantity()));
        editedOrder.setQuantityPerLoad(calculateQuantityPerLoad(orderBooking.getOrderQuantity()));
        editedOrder.setCreatedBy(orderFromDB.getCreatedBy());
        editedOrder.setCreatedDate(orderFromDB.getCreatedDate());
        editedOrder.setIsBooking(orderFromDB.getIsBooking());
        editedOrder.setUpdatedBy(userId);
        editedOrder.setLayerSequence(orderFromDB.getLayerSequence());
        editedOrder.setLayerGroup(orderFromDB.getLayerGroup());
        // Remains data of SaveBookingRequest
        if (orderBooking instanceof OrderSaveRequest) {
            editedOrder.setDeliveryAtAnyTime(orderFromDB.getDeliveryAtAnyTime());
            editedOrder.setStrengthId(orderFromDB.getStrengthId());
        }
        editedOrder.setAssignedQuantity(orderFromDB.getAssignedQuantity());
        editedOrder.setProgressiveQuantity(orderFromDB.getProgressiveQuantity());
        orderPlantRepository.deleteAllByOrderId(editedOrder.getId());
        orderCylinderTestRepository.deleteCylinderTestAndAgeByOrderId(editedOrder.getId());
        Order savedOrder = orderRepository.save(editedOrder);
        Long orderId = savedOrder.getId();
        List<OrderPlant> orderPlants = orderBooking.getAssignedPlants().stream().map(item -> {
            Plant plant = entityManager.getReference(Plant.class, item.getPlantId());
            return item.changeToOrderPlant(savedOrder, plant);
        }).collect(Collectors.toList());
        OrderCylinderTest orderCylinderTest = Optional.ofNullable(orderBooking.getCylinderTest()).map(item ->  item.changeToOrderCylinderTest(savedOrder)
        ).orElse(null);

        List<OrderAssociateProductItem> requestedItems = orderBooking.getAssociateProducts();
        List<OrderAssociateProduct> existingProducts = orderAssociateProductRepository.findOrderAssociateProductByOrderId(orderId);
        Map<Long, OrderAssociateProduct> existingByMaterialId = existingProducts.stream().collect(Collectors.toMap(OrderAssociateProduct::getMaterialId, Function.identity()));

        List<OrderAssociateProduct> inserts = new ArrayList<>();
        List<OrderAssociateProduct> updates = new ArrayList<>();

        for (OrderAssociateProductItem item : requestedItems) {
            Long materialId = item.getMaterialId();
            OrderAssociateProduct existing = existingByMaterialId.get(materialId);

            // Check Existing or New Record
            if (existing == null) {
                OrderAssociateProduct newProduct = item.changeToOrderAssociateProduct(editedOrder);
                newProduct.setCreatedBy(userId);
                OrderAssociateProduct savedParent = orderAssociateProductRepository.save(newProduct);
                inserts.add(savedParent);

                if (item.getBundledAssociateProducts() != null) {
                    for (OrderAssociateProductItem bundled : item.getBundledAssociateProducts()) {
                        OrderAssociateProduct bundledProduct = bundled.changeToOrderAssociateProduct(editedOrder, savedParent.getId());
                        bundledProduct.setUpdatedBy(userId);
                        orderAssociateProductRepository.save(bundledProduct);
                    }
                }
            } else {
                if (!existing.getQuantity().equals(item.getQuantity())) {
                    existing.setQuantity(item.getQuantity());
                    existing.setUpdatedBy(userId);
                    updates.add(existing);
                }
                existingByMaterialId.remove(materialId);
            }
        }

        // Mark remaining for deletion
        List<OrderAssociateProduct> deletes = new ArrayList<>(existingByMaterialId.values());

        // Batch SaveAll or Delete Removed records and Log Audit records
        if (!inserts.isEmpty())
            saveAuditOrderAssociateProducts(inserts, userId, AuditOrderAssociateProductStatus.CREATED);

        if (!updates.isEmpty()) {
            orderAssociateProductRepository.saveAll(updates);
            saveAuditOrderAssociateProducts(updates, userId, AuditOrderAssociateProductStatus.UPDATED);
        }
        if (!deletes.isEmpty()) {
            orderAssociateProductRepository.deleteAll(deletes);
            saveAuditOrderAssociateProducts(deletes, userId, AuditOrderAssociateProductStatus.DELETED);
        }

        if (orderBooking instanceof OrderSaveRequest) {
            orderTruckRepository.deleteAllByOrderId(orderId);
            List<OrderTruck> orderTrucks = ((OrderSaveRequest) orderBooking).getFleetGroupTrucks().stream().map(item -> changeToOrderTruck(savedOrder, item)).toList();
            orderTruckRepository.saveAll(orderTrucks);
            saveAuditOrderTrucks(orderTrucks, userId);
        }

        orderPlantRepository.saveAll(orderPlants);
        if (orderCylinderTest != null) {
            orderCylinderTestRepository.save(orderCylinderTest);
        }
        saveAuditOrder(savedOrder, userId);
        saveAuditOrderPlants(orderPlants, userId);

        if (savedOrder.getLayerSequence() != null && savedOrder.getLayerSequence() == 1
                && (savedOrder.getCustomerId() != orderFromDB.getCustomerId() || savedOrder.getProjectId() != orderFromDB.getProjectId())) {

            List<Order> listOrder = orderRepository.findByLayerGroup(savedOrder.getLayerGroup());
            for (Order order : listOrder) {
                order.setCustomerId(savedOrder.getCustomerId());
                order.setProjectId(savedOrder.getProjectId());
                orderRepository.save(order);
                saveAuditOrder(order, userId);
            }
        }
        if (orderCylinderTest != null) {
            saveAuditOrderCylinderTest(orderCylinderTest, userId);
        }
        if (!orderFromDB.getLatitude().equals(editedOrder.getLatitude()) || !orderFromDB.getLongitude().equals(editedOrder.getLongitude())) {
            saveLocationPlantDistanceTime(orderBooking, savedOrder.getId(), DateTimeUtil.fromUTCToNewZealandTime(savedOrder.getOrderDateTimeUtc()).getHour());
        }

        OrderSaveResponse orderSaveResponse = orderMapper.fromOrderToOrderSaveResponse(savedOrder);
        orderSaveResponse.setIsSuccess(true);
        orderSaveResponse.setId(savedOrder.getId());
        orderSaveResponse.setCustomer(customer);
        orderSaveResponse.setProject(project);
        orderSaveResponse.setProduct(product);
        orderSaveResponse.setAddress(savedOrder.getAddress());
        orderSaveResponse.setStatus(savedOrder.getStatus());
        orderSaveResponse.setWarningMessages(warningMessages);

        System.out.println("END update order **********************************************");
        return orderSaveResponse;
    }

    private boolean isDemoUsers (long userId){
        return userId == 19 || userId == 20 || userId == 23 || userId == 24;
    }

    private void checkAndSetContact(Order order, OrderContactItem orderContactItem) throws Exception {
        Long contactId = orderContactItem.getId();
        if ((orderContactItem.getId() == null || orderContactItem.getId() < 0)
                && (!orderContactItem.getCustomerName().isEmpty() && !orderContactItem.getContactNumber().isEmpty())) {
            // insert new contact for that customer
            contactId = customerContactService.addCustomerContact(order.getCustomerId(), orderContactItem.getCustomerName(), orderContactItem.getContactNumber(),
                    StringUtils.trimAllWhitespace(orderContactItem.getEmailAddress()));
        }
        if (contactId != null && contactId > 0) {
            // save contact for the order
            order.setContactUserId(contactId);
        }
    }

    private void checkAndSetPurchaseOrder(Order order, OrderPurchaseOrderItem purchaseOrder, Long projectId) throws Exception {
        Long purchaseOrderId = purchaseOrder.getId();
        if ((purchaseOrderId == null || purchaseOrderId < 0) && purchaseOrder.getPurchaseOrderNumber() != null && !purchaseOrder.getPurchaseOrderNumber().isEmpty()) {
            // insert new purchase order for that project
            purchaseOrderId = purchaseOrderService.createOrderPO(purchaseOrder.getPurchaseOrderNumber(), projectId);
        }
        if (purchaseOrderId != null && purchaseOrderId > 0) {
            // save purchase order for the order
            order.setPurchaseOrderId(purchaseOrderId);
        }
    }

    private void saveLocationPlantDistanceTime(OrderBooking orderBooking, Long savedOrderId, Integer hour) throws Exception {

        List<LocationPlantDistanceTime> result = null;

        locationPlantDistanceService.deletePreviousLocationPlantDistanceTime(savedOrderId);
        if (orderBooking.getIsBooking() != null && orderBooking.getIsBooking()) {
            Map<String, Long> plantCodeAndPlantId = plantService.getActivePlants().stream().collect(Collectors.toMap(PlantResponse::getPlantCode, PlantResponse::getPlantId));
            result = locationPlantDistanceService.getAndSaveLocationPlantDistanceTime(savedOrderId, orderBooking.getLatitude(), orderBooking.getLongitude(), plantCodeAndPlantId,
                    hour);
        } else {
            OrderSaveRequest orderSaveRequest = (OrderSaveRequest) orderBooking;
            result = locationPlantDistanceService.saveLocationPlantDistanceTime(orderSaveRequest.getLocationPlantDistanceTime(), savedOrderId);
        }

        if (result == null) {
            throw new InvalidRequestException("Invalid pinpoint location for retrieving distance time");
        }
    }


    @Transactional
    public void saveAuditOrderAssociateProducts(List<OrderAssociateProduct> orderAssociateProducts, Long updatedBy, AuditOrderAssociateProductStatus status) {
        List<AuditOrderAssociateProduct> auditOrderAssociateProducts = orderMapper.toAuditOrderAssociateProduct(orderAssociateProducts);
        auditOrderAssociateProducts.forEach(item -> {
            item.setUpdatedBy(updatedBy);
            item.setStatus(status);
        });
        auditOrderAssociateProductRepository.saveAll(auditOrderAssociateProducts);
    }

    private OrderTruck changeToOrderTruck(Order order, OrderTruckItem orderTruckItem) {
        OrderTruck orderTruck = new OrderTruck();
        orderTruck.setOrder(order);
        if (orderTruckItem.getTruckId() == null) {
            Truck truckGroup = entityManager.getReference(Truck.class, orderTruckItem.getTruckGroupId());
            orderTruck.setTruckGroup(truckGroup);
        } else {
            Truck truck = entityManager.getReference(Truck.class, orderTruckItem.getTruckId());
            orderTruck.setTruck(truck);
        }
        return orderTruck;
    }

    private Order getOrderUsingRequestObject(OrderBooking orderBooking, Boolean isCreate) throws Exception {
        if (orderBooking instanceof OrderSaveRequest) {
            OrderSaveRequest orderSaveRequest = (OrderSaveRequest) orderBooking;
            Order order = orderMapper.fromOrderSaveRequestToOrder(orderSaveRequest);
            checkAndSetLayerDetail(order, orderSaveRequest, isCreate);
            order.setOrderDateTimeUtc(DateTimeUtil.convertCurrentSystemToUtcTimeZone(LocalDateTime.of(orderSaveRequest.getOrderDate(), orderSaveRequest.getStartTime())));
            order.setTrucks(orderSaveRequest.getFleetTruckIds().stream().map(request -> {
                OrderTruck orderTruck = new OrderTruck();
                Truck truck = entityManager.getReference(Truck.class, request);
                orderTruck.setTruck(truck);
                return orderTruck;
            }).collect(Collectors.toSet()));
            return order;
        } else if (orderBooking instanceof SaveBookingRequest saveBookingRequest) {
            Order order = orderMapper.fromSaveBookingRequestToOrder(saveBookingRequest);
            orderValidation.validateStrengthExistence(saveBookingRequest.getStrengthId());
            order.setOrderDateTimeUtc(DateTimeUtil.convertCurrentSystemToUtcTimeZone(
                    LocalDateTime.of(LocalDate.parse(saveBookingRequest.getDeliveryDate()), LocalTime.parse(saveBookingRequest.getDeliveryTime()))));
            return order;
        } else {
            throw new InvalidRequestException("Invalid save order or booking");
        }
    }

    private void checkAndSetLayerDetail(Order order, OrderSaveRequest orderSaveRequest, Boolean isCreate) throws Exception {
        if (isCreate && orderSaveRequest.getSaveLayerOrder()) {
            // create layer order
            Order sameGroupOrder = orderValidation.validateAndGetOrder(orderSaveRequest.getOrderIdWithinLayers());
            Optional.of(orderSaveRequest.getLayerIntervals() == null)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid Order Save Request: layerIntervals is null."));
            order.setLayerIntervals(orderSaveRequest.getLayerIntervals());

            if (sameGroupOrder.getLayerGroup() == null) {
                // set first layer
                orderRepository.updateFirstLayerOrder(SEQUENCE_OF_FIRST_LAYER_ORDER, sameGroupOrder.getId());
                // set second layer
                order.setLayerSequence(SEQUENCE_OF_FIRST_LAYER_ORDER + 1);
                order.setLayerGroup(sameGroupOrder.getId());
            } else {
                // set next layer
                order.setLayerSequence(orderRepository.findLastLayerOrderSequence(sameGroupOrder.getId()) + 1);
                order.setLayerGroup(sameGroupOrder.getLayerGroup());
            }

        } else if (!isCreate && orderSaveRequest.getSaveLayerOrder()) {
            // edit layer order
            Optional.of(orderSaveRequest.getLayerIntervals() == null)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid Order Save Request: layerIntervals is null."));
            order.setLayerIntervals(orderSaveRequest.getLayerIntervals());
        } else {
            // create/edit normal order
            order.setLayerGroup(null);
            order.setLayerSequence(null);
            order.setLayerIntervals(null);
        }
    }

    private void saveAuditOrderCylinderTest(OrderCylinderTest orderCylinderTest, Long updatedBy) {
        AuditOrderCylinderTest auditOrderCylinderTest = orderMapper.toAuditOrderCylinderTest(orderCylinderTest);
        auditOrderCylinderTest.setUpdatedBy(updatedBy);
        auditOrderCylinderTestRepository.save(auditOrderCylinderTest);
        Set<AuditOrderCylinderTestAge> auditOrderCylinderTestAges = orderMapper.toAuditOrderCylinderTestAge(orderCylinderTest.getTestAgeSet());
        auditOrderCylinderTestAges.forEach(item -> {
            item.setOrderCylinderTestId(orderCylinderTest.getId());
            item.setUpdatedBy(updatedBy);
        });
        auditOrderCylinderTestAgeRepository.saveAll(auditOrderCylinderTestAges);
    }


    @Transactional
    public void saveAuditOrderPlants(List<OrderPlant> orderPlants, Long updatedBy) {
        List<AuditOrderPlant> auditOrderPlants = orderMapper.toAuditOrderPlant(orderPlants);
        auditOrderPlants.forEach(item -> item.setUpdatedBy(updatedBy));
        auditOrderPlantRepository.saveAll(auditOrderPlants);
    }

    private void saveAuditOrderTrucks(List<OrderTruck> orderTrucks, Long updatedBy) {
        List<AuditOrderTruck> auditOrderTrucks = orderMapper.toAuditOrderTruck(orderTrucks);
        auditOrderTrucks.forEach(item -> item.setUpdatedBy(updatedBy));
        auditOrderTruckRepository.saveAll(auditOrderTrucks);
    }

    @Transactional
    public void saveAuditOrder(Order order, Long updatedBy) {
        AuditOrder auditOrder = orderMapper.toAuditOrder(order);
        auditOrder.setUpdatedBy(updatedBy);
        auditOrderRepository.save(auditOrder);
    }

    private double calculateQuantityPerLoad(Double orderQuantity) {
        if (orderQuantity != null) {
            double maximumTruckCapacity = Optional.ofNullable(truckTypeRepository.getMaximumCapacity()).orElse(0.0);
            return Math.min(orderQuantity, maximumTruckCapacity);
        }
        return 0;
    }
    @Transactional
    private String generatePlantOrderNumber(Long plantId, LocalDate orderDate) {
        String plantCode = plantService.findById(plantId)
                .map(Plant::getPlantCode)
                .orElseThrow();

        GeneratePlantOrderNumber row = generatePlantOrderNoRepo.findForUpdate(plantId, orderDate)
                .orElseGet (() -> {
                    GeneratePlantOrderNumber g = new GeneratePlantOrderNumber();
                    g.setPlantId(plantId);
                    g.setCreatedDate(orderDate);
                    g.setOrderNo(1); // start at 1 so this call returns 1
                    return generatePlantOrderNoRepo.save(g);
                });

        int current = row.getOrderNo();       // number to use for THIS order
        row.setOrderNo(current + 1);          // increment for next time
        generatePlantOrderNoRepo.save(row);
        log.info("ORDER NO SEQ----- >" + row.getOrderNo());
        return plantCode + String.format("%03d", current);
    }

    @Transactional
    public void updateOrderDate() {
        // 1. Fetch the first 2 orders  on the orders  table
        List<Order> first2Orders = orderRepository.findFirst2ByOrderByIdAsc();
        // 2. Set the time of Order Date to current Date
        first2Orders.forEach (order -> {
            order.setOrderDate (LocalDate.now ());
        });
        // 3. Save the order ion the database
        orderRepository.saveAll (first2Orders);

        // Save Order Batch Time
        // 1.  Fetch Order IDs from the Order
        List<Long> orderIds = first2Orders.stream ().map (Order::getId).toList ();

        // 2. Fetch the OrderPlant from Order Id
        List<OrderPlantItemProjection> orderPlantsProjection = orderPlantRepository.findAllByOrderIds (orderIds);
        List<OrderPlantItem> orderPlants =  orderPlantsProjection.stream()
                .map(orderDetailListMapper::fromOrderPlantProjection)
                .toList();

        // 3. Change the startTime to current date starttime
        orderPlants.forEach (orderPlant -> orderPlant.setStartTime (LocalDateTime.now ().plusMinutes (30)));

        List<OrderPlant> resultantOrders = orderPlants.stream ().map(opi -> {
            Order order = entityManager.getReference(Order.class, opi.getOrderId());
            Plant plant = entityManager.getReference(Plant.class, opi.getPlantId());

            return opi.changeToOrderPlant(order, plant );
        }).toList ();

        // 4. Save the OrderPlants
        orderPlantRepository.saveAll (resultantOrders);
    }

}
