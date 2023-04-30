package com.viksingh.catalogservice.dao;

import com.viksingh.catalogservice.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantDAO extends JpaRepository<ProductVariant,Integer> {
}
