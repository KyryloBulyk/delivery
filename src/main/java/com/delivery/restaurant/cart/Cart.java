//package com.delivery.restaurant.cart;
//
//import com.delivery.restaurant.users.User;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Table(name = "cart")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class Cart {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer cartId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//
//    @Column(name = "total_price")
//    private Float totalPrice;
//
//    @OneToMany(mappedBy = "cart")
//    private List<CartItem> cartItems;
//}
