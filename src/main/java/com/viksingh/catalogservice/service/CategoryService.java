package com.viksingh.catalogservice.service;

import com.viksingh.catalogservice.dto.CategoryDTO;
import com.viksingh.catalogservice.dto.response.ResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface CategoryService {

    ResponseDTO saveCategory(CategoryDTO categoryDTO, HttpServletRequest httpServletRequest);

    ResponseDTO fetchAllCategories(Integer pageNo, Integer pageSize, String sortBy);
}
