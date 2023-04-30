package com.viksingh.catalogservice.controller;

import com.viksingh.catalogservice.dto.SubCategoryDTO;
import com.viksingh.catalogservice.dto.response.ResponseDTO;
import com.viksingh.catalogservice.service.SubCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/sub_categories")
@Slf4j
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody
                                                    @NotNull(message = "Input must not be NULL")
                                                    @Valid final SubCategoryDTO subCategoryDTO, HttpServletRequest httpServletRequest){
        log.info("*** Request received for add subcategory ***");
        ResponseDTO response = subCategoryService.saveSubCategory(subCategoryDTO,httpServletRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryDTO>> fetchSubcategories(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        List<SubCategoryDTO> subcategories = subCategoryService.fetchSubcategories(categoryId,pageNo,pageSize,sortBy);
        return ResponseEntity.ok(subcategories);
    }
}
