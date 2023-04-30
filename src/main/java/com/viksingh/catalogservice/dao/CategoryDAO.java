package com.viksingh.catalogservice.dao;

import com.viksingh.catalogservice.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryDAO extends JpaRepository<Category,Integer> {

    @Query("select c from Category c where c.status='ACTIVE'")
    Page<Category> findAllActiveCategory(Pageable pageable);
}
