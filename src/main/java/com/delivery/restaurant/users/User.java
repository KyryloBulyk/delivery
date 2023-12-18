package com.delivery.restaurant.users;

import com.delivery.restaurant.usercoupons.UserCoupon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Name
    @Column(name = "name")
    private String name;

    //Email
    @Column(name = "email")
    private String email;

    //Password
    @Column(name = "password")
    private String password;

    //Coupons
    @OneToMany(mappedBy = "user")
    private Set<UserCoupon> userCoupons = new HashSet<>();
}
