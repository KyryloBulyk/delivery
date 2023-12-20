package com.delivery.restaurant.cart;

import com.delivery.restaurant.products.Product;
import com.delivery.restaurant.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItemDTO> getCartItemsByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            return Collections.emptyList();
        }

        return cart.getCartItems().stream()
                .map(this::convertToCartItemDTO)
                .collect(Collectors.toList());
    }

    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        return new CartItemDTO(cartItem.getCartItemId(), cartItem.getProduct(), cartItem.getQuantity());
    }

    public CartItem addProductToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (cart != null && product != null) {
            Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            CartItem cartItem;
            if (existingCartItem.isPresent()) {
                cartItem = existingCartItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
            }

            cartItem = cartItemRepository.save(cartItem);
            updateCartTotalPrice(cart);
            return cartItem;
        }
        return null;
    }

    public boolean removeProductFromCart(Long cartItemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isPresent()) {
            cartItemRepository.delete(cartItem.get());
            updateCartTotalPrice(cartItem.get().getCart());
            return true;
        }
        return false;
    }

    public CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isPresent() && quantity > 0) {
            cartItem.get().setQuantity(quantity);
            CartItem updatedCartItem = cartItemRepository.save(cartItem.get());
            updateCartTotalPrice(updatedCartItem.getCart());
            return updatedCartItem;
        }
        return null;
    }

    private void updateCartTotalPrice(Cart cart) {
        float total = 0;
        List<CartItem> cartItems = cart.getCartItems();
        for (CartItem item : cartItems) {
            total += (float) (item.getProduct().getPrice() * item.getQuantity());
        }
        cart.setTotalPrice(total);
        cartRepository.save(cart);
    }
}
