package com.viksingh.catalogservice.service;

import com.viksingh.catalogservice.dto.SubCategoryDTO;
import com.viksingh.catalogservice.dto.response.ResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SubCategoryService {

    ResponseDTO saveSubCategory(SubCategoryDTO subCategoryDTO, HttpServletRequest httpServletRequest);

    List<SubCategoryDTO> fetchSubcategories(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy);
}
