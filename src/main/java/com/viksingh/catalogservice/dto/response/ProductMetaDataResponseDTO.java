package com.viksingh.catalogservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductMetaDataResponseDTO {
    private String attributeName;
    private String attributeValue;
}
