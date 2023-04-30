package com.viksingh.catalogservice.dao;

import com.viksingh.catalogservice.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubCategoryDAO extends JpaRepository<SubCategory,Integer> {

    @Query("select sb from SubCategory sb left join sb.category c where c.id=:categoryId and sb.status='ACTIVE'")
    Page<SubCategory> findSubCategoryByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);
}
