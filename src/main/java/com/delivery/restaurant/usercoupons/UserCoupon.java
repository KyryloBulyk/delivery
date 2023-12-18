package com.delivery.restaurant.usercoupons;

import com.delivery.restaurant.coupons.Coupon;
import com.delivery.restaurant.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

}
