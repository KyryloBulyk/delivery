package com.delivery.restaurant.coupons;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Long id, Coupon couponDetails) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        if (coupon == null) {
            return null;
        }
        coupon.setCode(couponDetails.getCode());
        coupon.setDiscount(couponDetails.getDiscount());
        coupon.setValidUntil(couponDetails.getValidUntil());
        return couponRepository.save(coupon);
    }

    public boolean deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id).orElse(null);
        if (coupon == null) {
            return false;
        }
        couponRepository.delete(coupon);
        return true;
    }
}
