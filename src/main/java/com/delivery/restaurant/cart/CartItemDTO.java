package com.delivery.restaurant.cart;

import com.delivery.restaurant.products.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Product product;
    private Integer quantity;
}
