package com.viksingh.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.viksingh.catalogservice.entity.Product;
import com.viksingh.catalogservice.entity.SubCategory;
import com.viksingh.catalogservice.entity.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryDTO {
    private Integer id;
    private String name;
    private String imageUrl;
    private Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubCategory> subCategories;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Product> products;
}
