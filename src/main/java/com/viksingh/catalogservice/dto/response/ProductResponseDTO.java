package com.viksingh.catalogservice.dto.response;

import com.viksingh.catalogservice.entity.constant.Currency;
import com.viksingh.catalogservice.entity.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponseDTO {
    private Integer id;
    private String name;
    private Status status;
    private BigDecimal price;
    private String description;
    private String sku;
    private Integer quantity;
    private BigDecimal sellingPrice;
    private String imageUrl;
    private Currency currency;
    private List<String> additionalImageLinks;
    private List<ProductVariantResponseDTO> variants;
    private List<ProductMetaDataResponseDTO> metaData;
}
