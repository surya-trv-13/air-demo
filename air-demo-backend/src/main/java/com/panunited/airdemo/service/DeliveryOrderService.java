package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.DeliveryOrderRequestDTO;
import com.panunited.airdemo.enums.EModificationStatus;
import com.panunited.airdemo.enums.EModificationWorkflowAction;
import com.panunited.airdemo.models.*;
import com.panunited.airdemo.repositories.DeliveryOrderRepository;
import com.panunited.airdemo.repositories.EDOSubmissionHistoryRepository;
import com.panunited.airdemo.repositories.EModificationDetailRepository;
import com.panunited.airdemo.repositories.EModificationWorkflowRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class DeliveryOrderService {

    private static final Long DEFAULT_USER_ID = 19L;
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final EntityManager entityManager;
    private final EModificationDetailRepository eModificationDetailRepository;
    private final EModificationWorkflowRepository eModificationWorkflowRepository;
    private final EDOSubmissionHistoryRepository edoSubmissionHistoryRepository;

    public DeliveryOrderService(DeliveryOrderRepository deliveryOrderRepository, EntityManager entityManager, EModificationDetailRepository eModificationDetailRepository, EModificationWorkflowRepository eModificationWorkflowRepository, EDOSubmissionHistoryRepository edoSubmissionHistoryRepository) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.entityManager = entityManager;
        this.eModificationDetailRepository = eModificationDetailRepository;
        this.eModificationWorkflowRepository = eModificationWorkflowRepository;
        this.edoSubmissionHistoryRepository = edoSubmissionHistoryRepository;
    }

    @Transactional
    public void submitEdo(DeliveryOrderRequestDTO reqDTO, Long driverId) throws Exception {

        try {
            Date currentDate = new Date();
            DeliveryOrder deliveryOrder = updateOrderDetail(reqDTO, driverId);
            if (reqDTO.getCubeDetailId() != null && reqDTO.getCubeDetailId() > 0)
                updateCubeOrderDetail(reqDTO, driverId);
            if (reqDTO.getAcceptedQty() < deliveryOrder.getDoQuantity())
                createEModForm(deliveryOrder, reqDTO);

            EDOSubmissionHistory history = new EDOSubmissionHistory();
            history.setDriverId(driverId);
            history.setOrderDetailId(reqDTO.getOrderDetailId());
            history.setSubmissionMethod("SUBMITTED_TIME");
            history.setCreatedDate(currentDate);
            edoSubmissionHistoryRepository.save(history);

        } catch (Exception e) {
            log.error("DeliveryOrderServiceImpl.submitEdo ", e);
            throw e;
        }
    }

    private void createEModForm(DeliveryOrder deliveryOrder, DeliveryOrderRequestDTO reqDTO) {
        try
        {
            EModificationDetail errorDODetail = insertErrorDoDetail(reqDTO, deliveryOrder);
            insertErrorDOWorkflow(errorDODetail);
        } catch (Exception e) {
            log.error("DeliveryOrderServiceImpl.createEModForm ", e);
            throw e;
        }
    }

    private void insertErrorDOWorkflow(EModificationDetail errorDODetail) {
        try {
            String doNoString = errorDODetail.getNewDoNumber()==null ? "" : errorDODetail.getNewDoNumber();
            EModificationWorkflow eModificationWorkflow = new EModificationWorkflow();

//            eModificationWorkflow.setBatchNo()
            eModificationWorkflow.setSubmissionFrom(DEFAULT_USER_ID);
            eModificationWorkflow.setSubmissionFromStatus(EModificationStatus.PENDING_PH);
            eModificationWorkflow.setSubmissionToStatus(EModificationStatus.PENDING_PH);
            eModificationWorkflow.setAction(EModificationWorkflowAction.SUBMITTED_FOR_APPROVAL);
            eModificationWorkflow.setRemarks("Submitted from EDO");
            eModificationWorkflow.setCreatedDate(LocalDateTime.now());

            eModificationWorkflowRepository.save(eModificationWorkflow);
        } catch (Exception e) {
            log.error("DeliveryOrderServiceImpl.insertErrorDOWorkflow ", e);
            throw e;
        }


    }

    private EModificationDetail insertErrorDoDetail(DeliveryOrderRequestDTO reqDTO, DeliveryOrder deliveryOrder) {
        try {
            boolean isCancelledDO = reqDTO.getAcceptedQty()==0.0;

            // Insert ErrorDODetail
            EModificationDetail errorDODetail = new EModificationDetail();
            errorDODetail.setNewQuantity(reqDTO.getAcceptedQty());
//            errorDODetail.(isCancelledDO);
            // Pending confirmation by PH
            EModificationCause eModificationCause = entityManager.getReference(EModificationCause.class, 86);
            errorDODetail.setEModificationCause(eModificationCause); // Pending confirmation by PH
//            errorDODetail.setOtherCause("Admin issues");
            errorDODetail.setNewDoNumber("0");
            errorDODetail.setDivertedQuantity((double) 0);
            errorDODetail.setNewDoNumber(deliveryOrder.getDoNumber());
            errorDODetail.setDeliveryOrderId(deliveryOrder.getId());

            errorDODetail.setPlantCode(deliveryOrder.getAssignedPlantId());
            errorDODetail.setDriverClaim(true);
//            errorDODetail.setSubmitFlag(true);
            errorDODetail.setStatus(EModificationStatus.PENDING_CCM);
            errorDODetail.setCreatedDate(LocalDateTime.now());
            errorDODetail = eModificationDetailRepository.save(errorDODetail);


            return errorDODetail;
        } catch (Exception e) {
            log.error("DeliveryOrderServiceImpl.insertErrorDoDetail ", e);
            throw e;
        }
    }

    private DeliveryOrder updateOrderDetail(DeliveryOrderRequestDTO reqDTO, Long driverId) {
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(reqDTO.getOrderDetailId()).orElse(new DeliveryOrder());
        deliveryOrder.setEdoSubmittedDate(LocalDateTime.now());
        deliveryOrder.setAcceptedQuantity(reqDTO.getAcceptedQty());
        deliveryOrder.setDischargeStartTime(LocalDateTime.parse(reqDTO.getDischargeStartTime()));
        deliveryOrder.setDischargeEndTime(LocalDateTime.parse(reqDTO.getDischargeEndTime()));
        deliveryOrder.setEdoComments(reqDTO.getEdoComments());

        Driver driver = entityManager.getReference(Driver.class, driverId);
        deliveryOrder.setDriver(driver);

        return deliveryOrderRepository.save(deliveryOrder);
    }

    private void updateCubeOrderDetail(DeliveryOrderRequestDTO reqDTO, Long driverId) throws Exception {
        try {

            DeliveryOrder cubeDo = deliveryOrderRepository.findById(reqDTO.getOrderDetailId()).orElse( null);

            if(cubeDo == null) {
                throw new Exception("CubeDO not found");
            }

            cubeDo.setEdoSubmittedDate(LocalDateTime.now());
            cubeDo.setAcceptedQuantity(cubeDo.getDoQuantity());
//            cubeDo.setReceivedBySignature(reqDTO.getCustomerSignature() == null ? null : Base64.decodeBase64(reqDTO.getCustomerSignature().getBytes()));
//            cubeDo.setCustomerSignOff(reqDTO.getCustomerSignOff());
//            cubeDo.setCustomerSignOffName(reqDTO.getCustomerSignOffName());
//            cubeDo.setCustomerSignLaterName(reqDTO.getCustomerSignLaterName());
//            cubeDo.setConsultantSignature(reqDTO.getConsultantSignature() == null ? null : Base64.decodeBase64(reqDTO.getConsultantSignature().getBytes()));
//            cubeDo.setConsultantSignOff(reqDTO.getConsultantSignOff());
//            cubeDo.setConsultantSignOffName(reqDTO.getConsultantSignOffName());
//            cubeDo.setConsultantSignLaterName(reqDTO.getConsultantSignLaterName());
            Driver driver = entityManager.getReference(Driver.class, driverId);
            cubeDo.setDriver(driver);

            deliveryOrderRepository.save(cubeDo);
        } catch (Exception e) {
            log.error("DeliveryOrderServiceImpl.updateCubeOrderDetail ", e);
            throw e;
        }
    }

    public DeliveryOrder getOrderDetailByDONO(Integer doNo) {
        return deliveryOrderRepository.findByDoNumber(doNo.toString());
    }

    public void saveErrorEDO(DeliveryOrderRequestDTO reqDTO, Exception e, String submitEdo) {
    }
}
