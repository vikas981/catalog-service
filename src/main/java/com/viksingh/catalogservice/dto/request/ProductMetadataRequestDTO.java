package com.viksingh.catalogservice.dto.request;

import lombok.Data;

@Data
public class ProductMetadataRequestDTO {
    private Integer productId;
    private String attributeName;
    private String attributeValue;
}
