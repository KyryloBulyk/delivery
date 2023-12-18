package com.delivery.restaurant.usercoupons;

import com.delivery.restaurant.coupons.Coupon;
import com.delivery.restaurant.coupons.CouponRepository;
import com.delivery.restaurant.users.User;
import com.delivery.restaurant.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    public UserCouponService(UserCouponRepository userCouponRepository, UserRepository userRepository, CouponRepository couponRepository) {
        this.userCouponRepository = userCouponRepository;
        this.userRepository = userRepository;
        this.couponRepository = couponRepository;
    }

    @Transactional
    public UserCoupon assignCouponToUser(Long userId, Long couponId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("Coupon not found"));

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        return userCouponRepository.save(userCoupon);
    }

    public List<UserCoupon> getCouponsByUserId(Long userId) {
        return userCouponRepository.findAllByUserId(userId);
    }

    public boolean removeCouponFromUser(Long userCouponId) {
        if (!userCouponRepository.existsById(userCouponId)) {
            return false;
        }
        userCouponRepository.deleteById(userCouponId);
        return true;
    }
}
