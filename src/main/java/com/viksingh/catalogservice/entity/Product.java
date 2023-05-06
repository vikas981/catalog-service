package com.viksingh.catalogservice.entity;

import com.viksingh.catalogservice.entity.constant.Currency;
import com.viksingh.catalogservice.entity.constant.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "products")
@Entity
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private Status status;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column (name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(unique = true)
    private String sku;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> variants;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductMetaData> metaData;

    @ElementCollection
    @Column(name = "additional_image_links")
    private List<String> additionalImageLinks;

}
