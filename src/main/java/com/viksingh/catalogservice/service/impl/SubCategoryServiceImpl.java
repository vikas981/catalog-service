package com.viksingh.catalogservice.service.impl;

import com.viksingh.catalogservice.dao.CategoryDAO;
import com.viksingh.catalogservice.dao.SubCategoryDAO;
import com.viksingh.catalogservice.dto.SubCategoryDTO;
import com.viksingh.catalogservice.dto.response.ApiResponse;
import com.viksingh.catalogservice.dto.response.ResponseDTO;
import com.viksingh.catalogservice.entity.Category;
import com.viksingh.catalogservice.entity.SubCategory;
import com.viksingh.catalogservice.exception.CategoryNotFoundException;
import com.viksingh.catalogservice.service.SubCategoryService;
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
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryDAO subCategoryDAO;
    private final CategoryDAO categoryDAO;

    public SubCategoryServiceImpl(SubCategoryDAO subCategoryDAO, CategoryDAO categoryDAO) {
        this.subCategoryDAO = subCategoryDAO;
        this.categoryDAO = categoryDAO;
    }

    @Override
    public ResponseDTO saveSubCategory(SubCategoryDTO subCategoryDTO, HttpServletRequest httpServletRequest) {
        Category category = categoryDAO.findById(subCategoryDTO.getCategory().getId())
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id: %d not found", subCategoryDTO.getCategory().getId())));
            SubCategory subCategory = convertToEntity(subCategoryDTO,category);
            subCategoryDAO.save(subCategory);
        return ApiResponse.response(HttpStatus.OK,"Category fetched successfully.");

    }

    @Override
    public List<SubCategoryDTO> fetchSubcategories(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<SubCategory> pagedResult = subCategoryDAO.findSubCategoryByCategoryId(categoryId,pageable);
        if(pagedResult.hasContent()) {
            return convertToDTO(pagedResult.getContent());
        } else {
            return Collections.emptyList();
        }
    }

    private List<SubCategoryDTO> convertToDTO(List<SubCategory> subCategories) {
        List<SubCategoryDTO> subCategoryList = new ArrayList<>();
        for(SubCategory subCategory : subCategories){
            SubCategoryDTO subCategoryDTO = new SubCategoryDTO();
            subCategoryDTO.setId(subCategory.getId());
            subCategoryDTO.setName(subCategory.getName());
            subCategoryDTO.setImageUrl(subCategory.getImageUrl());
            subCategoryDTO.setStatus(subCategory.getStatus());
            subCategoryList.add(subCategoryDTO);
        }
        return subCategoryList;
    }

    private SubCategory convertToEntity(SubCategoryDTO subCategoryDTO, Category category) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryDTO.getName());
        subCategory.setImageUrl(subCategoryDTO.getImageUrl());
        subCategory.setStatus(subCategoryDTO.getStatus());
        subCategory.setCategory(category);
        subCategory.setCreated("");
        subCategory.setUpdated("");
        subCategory.setCreatedAt(LocalDateTime.now());
        subCategory.setUpdatedAt(LocalDateTime.now());
        return subCategory;
    }
}
