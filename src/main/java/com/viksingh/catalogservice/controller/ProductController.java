package com.viksingh.catalogservice.controller;

import com.viksingh.catalogservice.dto.request.ProductMetadataRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductUpdateRequestDTO;
import com.viksingh.catalogservice.dto.request.ProductVariantRequestDTO;
import com.viksingh.catalogservice.dto.response.ResponseDTO;
import com.viksingh.catalogservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/addProduct")
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody
                                                    @NotNull(message = "Input must not be NULL")
                                                    @Valid final ProductRequestDTO request, HttpServletRequest httpServletRequest){
        log.info("*** Request received for Add Category ***");
        ResponseDTO response = productService.saveProduct(request,httpServletRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> fetchProducts(
            @RequestParam(value = "subcategoryId", required = false) Integer subcategoryId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        ResponseDTO response = productService.fetchProductsBySubcategoryId(subcategoryId, pageNo, pageSize, sortBy);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<ResponseDTO> fetchProductsByCategoryId(
            @PathVariable(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        ResponseDTO response = productService.fetchProductsByCategoryId(categoryId, pageNo, pageSize, sortBy);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/addVariants")
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody
                                                    @NotNull(message = "Input must not be NULL")
                                                    @Valid final ProductVariantRequestDTO request , HttpServletRequest httpServletRequest){
        log.info("*** Request received for Add Product Variants ***");
        ResponseDTO response = productService.saveProductVariants(request,httpServletRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addMetadata")
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody
                                                    @NotNull(message = "Input must not be NULL")
                                                    @Valid final ProductMetadataRequestDTO request , HttpServletRequest httpServletRequest){
        log.info("*** Request received for Add Product Variants ***");
        ResponseDTO response = productService.saveProductMetaData(request,httpServletRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateProduct")
    public ResponseEntity<ResponseDTO> updateProduct(@RequestBody
                                                    @NotNull(message = "Input must not be NULL")
                                                    @Valid final ProductUpdateRequestDTO request, HttpServletRequest httpServletRequest){
        log.info("*** Request received for Update Product ***");
        ResponseDTO response = productService.updateProduct(request,httpServletRequest);
        return ResponseEntity.ok(response);
    }

}
