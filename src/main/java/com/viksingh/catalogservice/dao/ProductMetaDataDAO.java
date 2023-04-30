package com.viksingh.catalogservice.dao;

import com.viksingh.catalogservice.entity.ProductMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMetaDataDAO extends JpaRepository<ProductMetaData,Integer> {
}
