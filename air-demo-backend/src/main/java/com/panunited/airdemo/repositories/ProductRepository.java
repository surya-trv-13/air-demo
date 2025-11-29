package com.panunited.airdemo.repositories;

import com.panunited.airdemo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
