package com.delivery.restaurant.coupons;

import com.delivery.restaurant.usercoupons.UserCoupon;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coupons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "discount")
    private int discount;

    @Column(name = "valid_until")
    private Date validUntil;

    @OneToMany(mappedBy = "coupon")
    private Set<UserCoupon> userCoupons = new HashSet<>();
}
