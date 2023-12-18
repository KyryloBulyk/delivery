package com.delivery.restaurant.users;

import com.delivery.restaurant.usercoupons.UserCoupon;
import com.delivery.restaurant.usercoupons.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserCouponService userCouponService;

    @Autowired
    public UserController(UserService userService, UserCouponService userCouponService) {
        this.userService = userService;
        this.userCouponService = userCouponService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PutMapping("/changing/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/deleting/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/coupons/{couponId}")
    public ResponseEntity<UserCoupon> assignCouponToUser(@PathVariable Long userId, @PathVariable Long couponId) {
        UserCoupon userCoupon = userCouponService.assignCouponToUser(userId, couponId);
        return ResponseEntity.ok(userCoupon);
    }

    @GetMapping("/{userId}/coupons")
    public ResponseEntity<List<UserCoupon>> getUserCoupons(@PathVariable Long userId) {
        List<UserCoupon> coupons = userCouponService.getCouponsByUserId(userId);
        return ResponseEntity.ok(coupons);
    }

    @DeleteMapping("/coupons/{userCouponId}")
    public ResponseEntity<?> removeCouponFromUser(@PathVariable Long userCouponId) {
        boolean isDeleted = userCouponService.removeCouponFromUser(userCouponId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
