package com.viksingh.catalogservice.dto.response;

import com.viksingh.catalogservice.entity.constant.ProductSize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductVariantResponseDTO {
    private BigDecimal price;
    private ProductSize size;
}
