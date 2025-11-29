package com.panunited.airdemo.controllers;

import com.panunited.airdemo.dto.CustomerContactDetailResponse;
import com.panunited.airdemo.models.CustomerContact;
import com.panunited.airdemo.security.models.UserPrincipal;
import com.panunited.airdemo.service.CustomerContactService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerContactController {

    private final CustomerContactService customerContactService;

    @GetMapping("{customerId}/operation-contacts")
    public ResponseEntity<List<CustomerContactDetailResponse>> getOperationalContact(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long customerId) throws Exception {
        List<CustomerContactDetailResponse> customerContacts = customerContactService.fetchOperationContactByCustomerId(customerId);
        return ResponseEntity.ok(customerContacts);
    }
}
