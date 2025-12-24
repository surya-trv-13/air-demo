package com.panunited.airdemo.controllers;


import com.panunited.airdemo.dto.DeliveryOrderRequestDTO;
import com.panunited.airdemo.dto.JsonRequestResult;
import com.panunited.airdemo.models.DeliveryOrder;
import com.panunited.airdemo.response.ResponseBadRequest;
import com.panunited.airdemo.response.ResponseInternalError;
import com.panunited.airdemo.service.DeliveryOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryOrderService")
@Slf4j
public class DeliveryOrderController {

    private final DeliveryOrderService deliveryOrderService;

    public DeliveryOrderController(DeliveryOrderService deliveryOrderService) {
        this.deliveryOrderService = deliveryOrderService;
    }


    @RequestMapping(
            value = "/submitEdo/demo",
            method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> submitEDOFORDemo(@RequestBody DeliveryOrderRequestDTO reqDTO) {
        try {

            DeliveryOrder deliveryOrder =  deliveryOrderService.getOrderDetailByDONO(reqDTO.getDoNo());
            if (deliveryOrder.getStatus() == null)
                return new ResponseBadRequest("DONO NOT FOUND");
            reqDTO.setOrderDetailId(deliveryOrder.getId());

            deliveryOrderService.submitEdo(reqDTO, 99999L);

            log.debug("submitEdo Submitted Successfully! " + reqDTO.getOrderDetailId() + " - " + reqDTO.getLoginId());
            return new ResponseEntity<>(new JsonRequestResult(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("DeliveryOrderController.submitEdo ", e);
            e.printStackTrace();
            deliveryOrderService.saveErrorEDO(reqDTO, e, "submitEdo");
            return new ResponseInternalError();
        }
    }
}
