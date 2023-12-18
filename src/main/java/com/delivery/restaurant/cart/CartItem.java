//package com.delivery.restaurant.cart;
//
//import com.delivery.restaurant.products.Product;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "cart_item")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class CartItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer cartItemId;
//
//    @Column(name = "cart_id")
//    private Integer cartId;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id", insertable = false, updatable = false)
//    private Product product;
//
//    @Column(name = "quantity")
//    private Integer quantity;
//}
