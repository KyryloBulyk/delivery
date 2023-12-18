//package com.delivery.restaurant.cart;
//
//simport com.delivery.restaurant.users.User;
//import com.delivery.restaurant.products.Product;
//import com.delivery.restaurant.products.ProductRepository;
//import com.delivery.restaurant.users.User;
//import com.delivery.restaurant.users.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//public class CartService {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // Додавання товару в кошик
//    @Transactional
//    public void addItemToCart(Long userId, Long productId, Integer quantity) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        Cart cart = user.getCart();
//        if (cart == null) {
//            cart = new Cart();
//            cart.setUser(user);
//            cartRepository.save(cart);
//        }
//
//        Optional<CartItem> existingItem = cart.getCartItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst();
//
//        if (existingItem.isPresent()) {
//            CartItem cartItem = existingItem.get();
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//            cartItemRepository.save(cartItem);
//        } else {
//            CartItem newItem = new CartItem();
//            newItem.setCart(cart);
//            newItem.setProduct(product);
//            newItem.setQuantity(quantity);
//            cartItemRepository.save(newItem);
//        }
//
//        updateCartTotalPrice(cart);
//    }
//
//    // Оновлення загальної ціни кошика
//    private void updateCartTotalPrice(Cart cart) {
//        float totalPrice = cart.getCartItems().stream()
//                .mapToFloat(item -> item.getProduct().getPrice() * item.getQuantity())
//                .sum();
//        cart.setTotalPrice(totalPrice);
//        cartRepository.save(cart);
//    }
//
//    // Додайте тут інші методи (видалення, оновлення кількості, отримання кошика тощо)
//}
