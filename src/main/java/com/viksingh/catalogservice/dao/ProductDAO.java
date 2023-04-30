package com.viksingh.catalogservice.dao;

import com.viksingh.catalogservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDAO extends JpaRepository<Product,Integer> {

    @Query("select p from Product p left join p.subCategory sc where sc.id=:id and p.status='ACTIVE'")
    Page<Product> findProductBySubCategoryId(@Param("id") Integer id, Pageable pageable);

    @Query("select p from Product p left join p.subCategory sc left join sc.category c where c.id=:categoryId and p.status='ACTIVE'")
    Page<Product> findProductByCategoryId(Integer categoryId, Pageable pageable);
}
