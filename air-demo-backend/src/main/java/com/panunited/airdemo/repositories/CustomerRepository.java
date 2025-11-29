package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT REGION_ID FROM CUSTOMER_REGION WHERE CUSTOMER_ID = :customerId", nativeQuery = true)
    List<Long> findAllCustomerRegions(Long customerId);
}
