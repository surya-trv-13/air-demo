package com.panunited.airdemo.service;

import com.panunited.airdemo.dto.CustomerContactDetailResponse;
import com.panunited.airdemo.mapper.CustomerContactMapper;
import com.panunited.airdemo.models.Customer;
import com.panunited.airdemo.models.CustomerContact;
import com.panunited.airdemo.repositories.CustomerContactRepository;
import com.panunited.airdemo.validation.OrderValidation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerContactService {

    private final CustomerContactRepository customerContactRepository;
    private final EntityManager entityManager;
    private final OrderValidation orderValidation;
    private final CustomerContactMapper customerContactMapper;

    @Transactional
    public Long addCustomerContact(Long customerId, String contactName, String contactNumber, String email) throws Exception {
        // verify if there is any duplicate contact name and contact number
        List<CustomerContact> list = customerContactRepository.findCustomerContact(contactName, contactNumber, email);
        if (list.isEmpty()) {
            log.info("enter creating order new contact");
            orderValidation.validateAndGetCustomer(customerId);
            CustomerContact customerContact = new CustomerContact();
            customerContact.setContactName(contactName);
            customerContact.setContactNumber(contactNumber);
            Customer customer = entityManager.getReference(Customer.class, customerId);
            customerContact.setCustomer(customer);
            customerContact.setEmailAddress(email);
            customerContact.setType("Operation");
            CustomerContact response = customerContactRepository.save(customerContact);
            return response.getCustomerContactId();
        } else {
            log.info("already have this contact");
            return list.get(0).getCustomerContactId();
        }
    }

    public List<CustomerContactDetailResponse> fetchOperationContactByCustomerId(Long customerId) throws Exception {
        orderValidation.validateAndGetCustomer(customerId);
        List<CustomerContact> list = customerContactRepository.filterOperationByCustomerId(customerId);
        return customerContactMapper.toResponse(list);
    }
}
