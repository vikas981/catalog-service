package com.viksingh.catalogservice.dto.request;

import com.viksingh.catalogservice.entity.SubCategory;
import com.viksingh.catalogservice.entity.constant.Status;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductRequestDTO {



    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotNull(message = "Status may not be null")
    private Status status;

    @NotNull(message = "Price may not be null")
    private BigDecimal price;

    @NotBlank(message = "Description may not be blank")
    private String description;
    private String sku;

    private Integer quantity;
    private SubCategory subCategory;
    private List<String> additionalImageLinks;
}
