package com.viksingh.catalogservice.controller;

import com.viksingh.catalogservice.dto.CategoryDTO;
import com.viksingh.catalogservice.dto.response.ResponseDTO;
import com.viksingh.catalogservice.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/categories")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/addCategory")
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody @NotNull(message = "Input must not be NULL")
                                                    @Valid final CategoryDTO categoryDto, HttpServletRequest httpServletRequest){
        log.info("*** Request received for Add Category ***");
        ResponseDTO response = categoryService.saveCategory(categoryDto,httpServletRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> fetchCategories(@RequestParam(defaultValue = "0") Integer pageNo,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       @RequestParam(defaultValue = "id") String sortBy){
        ResponseDTO response = categoryService.fetchAllCategories(pageNo,pageSize,sortBy);
        return ResponseEntity.ok(response);
    }
}
