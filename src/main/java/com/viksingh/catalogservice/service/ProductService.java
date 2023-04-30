package com.viksingh.catalogservice.service;

import com.viksingh.catalogservice.dto.request.ProductMetadataRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductUpdateRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductVariantRequestDTO;
import com.viksingh.catalogservice.dto.response.ResponseDTO;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {
    ResponseDTO saveProduct(ProductRequestDTO productDTO, HttpServletRequest httpServletRequest);

    ResponseDTO fetchProductsBySubcategoryId(Integer subcategoryId,Integer pageNo, Integer pageSize, String sortBy);

    ResponseDTO saveProductVariants(ProductVariantRequestDTO request, HttpServletRequest httpServletRequest);

    ResponseDTO saveProductMetaData(ProductMetadataRequestDTO request, HttpServletRequest httpServletRequest);

    ResponseDTO updateProduct(ProductUpdateRequestDTO request, HttpServletRequest httpServletRequest);

    ResponseDTO fetchProductsByCategoryId(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy);
}
