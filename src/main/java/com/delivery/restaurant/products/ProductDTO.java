package com.delivery.restaurant.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String title;
    private double price;
    private String img;
    private Long categoryId;

}