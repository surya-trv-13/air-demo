package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.CustomerContact;
import com.panunited.airdemo.service.CustomerContactService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerContactRepository extends JpaRepository<CustomerContact, Long> {

    @Query(value = """
			SELECT CC.* FROM CUSTOMER_CONTACT CC WHERE TRIM(CONTACT_NAME) = TRIM(:contactName)
			AND  (CONTACT_NUMBER IS NULL OR CONTACT_NUMBER = :contactNumber)
			AND (:email IS NULL OR EMAIL_ADDRESS = :email)
			""", nativeQuery = true)
    List<CustomerContact> findCustomerContact(String contactName, String contactNumber, String email);

	@Query(value = "SELECT * FROM CUSTOMER_CONTACT WHERE TYPE LIKE '%Operation%'  AND  (:customerId is null  OR CUSTOMER_ID = :customerId)", nativeQuery = true)
    List<CustomerContact> filterOperationByCustomerId(Long customerId);
}
