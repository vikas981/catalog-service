package com.viksingh.catalogservice.dto.request;

import com.viksingh.catalogservice.entity.constant.ProductSize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVariantRequestDTO {
    private Integer productId;
    private BigDecimal price;
    private ProductSize size;
}
