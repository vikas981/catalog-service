package com.viksingh.catalogservice.service.impl;

import com.viksingh.catalogservice.dao.ProductDAO;
import com.viksingh.catalogservice.dao.ProductMetaDataDAO;
import com.viksingh.catalogservice.dao.ProductVariantDAO;
import com.viksingh.catalogservice.dao.SubCategoryDAO;
import com.viksingh.catalogservice.dto.request.ProductMetadataRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductUpdateRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductVariantRequestDTO;
import com.viksingh.catalogservice.dto.response.*;
import com.viksingh.catalogservice.entity.Product;
import com.viksingh.catalogservice.entity.ProductMetaData;
import com.viksingh.catalogservice.entity.ProductVariant;
import com.viksingh.catalogservice.entity.SubCategory;
import com.viksingh.catalogservice.exception.ProductNotFoundException;
import com.viksingh.catalogservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final SubCategoryDAO subCategoryDAO;
    private final ProductDAO productDAO;

    private final ProductVariantDAO productVariantDAO;

    private final ProductMetaDataDAO productMetaDataDAO;

    private static final String PRODUCT_NOT_FOUND_MSG = "Product with id: %d not found";


    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(SubCategoryDAO subCategoryDAO, ProductDAO productDAO, ProductVariantDAO productVariantDAO, ProductMetaDataDAO productMetaDataDAO) {
        this.subCategoryDAO = subCategoryDAO;
        this.productDAO = productDAO;
        this.productVariantDAO = productVariantDAO;
        this.productMetaDataDAO = productMetaDataDAO;
    }

    @Override
    public ResponseDTO saveProduct(ProductRequestDTO productDTO, HttpServletRequest httpServletRequest) {
        Optional<SubCategory> subCategory = subCategoryDAO.findById(productDTO.getSubCategory().getId());
        if(subCategory.isPresent()){
            productDAO.save(convertToEntity(productDTO,subCategory.get()));
            return ApiResponse.response(HttpStatus.OK,"Product added successfully.");
        }
        return ApiResponse.response(HttpStatus.BAD_REQUEST,"Product not saved.");
    }

    @Override
    public ResponseDTO fetchProductsBySubcategoryId(Integer subcategoryId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productDAO.findProductBySubCategoryId(subcategoryId,pageable);
        if(pagedResult.hasContent()) {
             List<ProductResponseDTO> products = convertToDTO(pagedResult.getContent());
             return ApiResponse.response(HttpStatus.OK,"Product fetched successfully.",products);
        } else {
            return ApiResponse.response(HttpStatus.BAD_REQUEST,"Unable to fetched products.",Collections.emptyList());
        }
    }

    @Override
    public ResponseDTO saveProductVariants(ProductVariantRequestDTO request, HttpServletRequest httpServletRequest) {
        Product product = productDAO.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, request.getProductId())));
            ProductVariant productVariant = new ProductVariant();
            productVariant.setSize(request.getSize());
            productVariant.setPrice(request.getPrice());
            productVariant.setProduct(product);
            productVariantDAO.save(productVariant);
            return ApiResponse.response(HttpStatus.OK,"Category added successfully.");
    }

    @Override
    public ResponseDTO saveProductMetaData(ProductMetadataRequestDTO request, HttpServletRequest httpServletRequest) {
        Product product = productDAO.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, request.getProductId())));
            ProductMetaData metaData = new ProductMetaData();
            metaData.setAttributeName(request.getAttributeName());
            metaData.setAttributeValue(request.getAttributeValue());
            metaData.setProduct(product);
            productMetaDataDAO.save(metaData);
            return ApiResponse.response(HttpStatus.OK,"Product meta data added successfully.");
    }

    @Override
    public ResponseDTO updateProduct(ProductUpdateRequestDTO request, HttpServletRequest httpServletRequest) {
        Product product = productDAO.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MSG, request.getProductId())));
            product.setAdditionalImageLinks(request.getAdditionalImageLinks());
            product.setPrice(request.getPrice());
            product.setSellingPrice(request.getSellingPrice());
            product.setCurrency(request.getCurrency());
            product.setStatus(request.getStatus());
            product.setImageUrl(request.getImageUrl());
            productDAO.save(product);
            return ApiResponse.response(HttpStatus.OK,"Product data updated successfully.");
    }

    @Override
    public ResponseDTO fetchProductsByCategoryId(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> pagedResult = productDAO.findProductByCategoryId(categoryId,pageable);
        if(pagedResult.hasContent()) {
            List<ProductResponseDTO> products = convertToDTO(pagedResult.getContent());
            return ApiResponse.response(HttpStatus.OK,"Product fetched successfully.",products);
        } else {
            return ApiResponse.response(HttpStatus.BAD_REQUEST,"Unable to fetched products.",Collections.emptyList());
        }
    }

    private List<ProductResponseDTO> convertToDTO(List<Product> products) {
        List<ProductResponseDTO> productDTOList = new ArrayList<>();
        for(Product product : products){
            ProductResponseDTO productDTO = new ProductResponseDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setSku(product.getSku());
            productDTO.setStatus(product.getStatus());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setImageUrl(product.getImageUrl());
            productDTO.setCurrency(product.getCurrency());
            productDTO.setPrice(product.getPrice());
            productDTO.setSellingPrice(product.getSellingPrice());
            productDTO.setVariants(convertToVariantDTO(product.getVariants()));
            productDTO.setMetaData(convertToMetaDataDTO(product.getMetaData()));
            productDTO.setAdditionalImageLinks(product.getAdditionalImageLinks());
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    private List<ProductMetaDataResponseDTO> convertToMetaDataDTO(List<ProductMetaData> metaData) {
        return metaData.stream().map(meta -> ProductMetaDataResponseDTO.builder().attributeName(meta.getAttributeName())
                .attributeValue(meta.getAttributeValue()).build()).collect(Collectors.toList());
    }

    private List<ProductVariantResponseDTO> convertToVariantDTO(List<ProductVariant> variants) {
        return variants.stream().map(variant -> ProductVariantResponseDTO.builder().price(variant.getPrice())
                .size(variant.getSize()).build()).collect(Collectors.toList());
    }


    private Product convertToEntity(ProductRequestDTO productDTO, SubCategory subCategory) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setSku(productDTO.getSku());
        product.setQuantity(productDTO.getQuantity());
        product.setStatus(productDTO.getStatus());
        product.setSubCategory(subCategory);
        return product;
    }
}
