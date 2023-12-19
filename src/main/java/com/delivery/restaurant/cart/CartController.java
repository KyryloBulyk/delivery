package com.delivery.restaurant.cart;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItemDTO> getCartItemsByUserId(@RequestParam Integer userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @PostMapping("/add")
    public CartItem addProductToCart(@RequestParam Integer cartId, @RequestParam Long productId, @RequestParam Integer quantity) {
        return cartService.addProductToCart(cartId, productId, quantity);
    }

    @PutMapping("/update")
    public CartItem updateCartItemQuantity(@RequestParam Integer cartItemId, @RequestParam Integer quantity) {
        return cartService.updateCartItemQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/remove")
    public boolean removeProductFromCart(@RequestParam Integer cartItemId) {
        return cartService.removeProductFromCart(cartItemId);
    }
}
