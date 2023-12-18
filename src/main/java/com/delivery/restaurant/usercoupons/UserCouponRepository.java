package com.delivery.restaurant.usercoupons;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findAllByUserId(Long userId);
}