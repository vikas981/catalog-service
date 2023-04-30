package com.viksingh.catalogservice.service.impl;

import com.viksingh.catalogservice.dao.CategoryDAO;
import com.viksingh.catalogservice.dto.CategoryDTO;
import com.viksingh.catalogservice.dto.response.ApiResponse;
import com.viksingh.catalogservice.dto.response.ResponseDTO;
import com.viksingh.catalogservice.entity.Category;
import com.viksingh.catalogservice.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public ResponseDTO saveCategory(CategoryDTO categoryDTO, HttpServletRequest httpServletRequest) {
        Category category = convertToEntity(categoryDTO);
        categoryDAO.save(category);
        return ApiResponse.response(HttpStatus.OK,"Category added successfully.");
    }

    @Override
    public ResponseDTO fetchAllCategories(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Category> pagedResult = categoryDAO.findAllActiveCategory(pageable);
        if(pagedResult.hasContent()) {
            return ApiResponse.response(HttpStatus.OK,"Category fetched successfully.",convertToDTO(pagedResult.getContent()));
        } else {
            return ApiResponse.response(HttpStatus.BAD_REQUEST,"Unable to fetch categories.", Collections.emptyList());
        }
    }

    private List<CategoryDTO> convertToDTO(List<Category> categories) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(Category category : categories){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setImageUrl(category.getImageUrl());
            categoryDTO.setStatus(category.getStatus());
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setImageUrl(categoryDTO.getImageUrl());
        category.setStatus(categoryDTO.getStatus());
        category.setSubCategories(categoryDTO.getSubCategories());

        //need to set admin id security not implemented yet.
        category.setCreated("");
        category.setUpdated("");
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return category;
    }
}
