package com.viksingh.catalogservice.dto.request;

import com.viksingh.catalogservice.entity.constant.Currency;
import com.viksingh.catalogservice.entity.constant.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductUpdateRequestDTO {
    private Integer productId;
    private Status status;
    private BigDecimal price;
    private String imageUrl;
    private BigDecimal sellingPrice;
    private Currency currency;
    private List<String> additionalImageLinks;
}
