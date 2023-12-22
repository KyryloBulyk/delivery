package com.delivery.restaurant.cart;

import com.delivery.restaurant.products.Product;
import com.delivery.restaurant.products.ProductRepository;
import com.delivery.restaurant.users.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    void getCartItemsByUserId() {
        // given
        Long userId = 1L;
        User user = new User(userId, "Test User", "test@example.com", "password", "USER", null);
        Cart cart = new Cart(1L, user, 0.0f, null);

        Product product1 = new Product(1L, "Product 1", 10.0, "Image 1", null);
        Product product2 = new Product(2L, "Product 2", 20.0, "Image 2", null);
        CartItem cartItem1 = new CartItem(1L, cart, product1, 5);
        CartItem cartItem2 = new CartItem(2L, cart, product2, 4);

        cart.setCartItems(Arrays.asList(cartItem1, cartItem2));

        Mockito.when(cartRepository.findByUserId(userId)).thenReturn(Optional.of(cart));

        // when
        List<CartItemDTO> cartItemDTOs = cartService.getCartItemsByUserId(userId);

        // then
        assertNotNull(cartItemDTOs);
        assertEquals(2, cartItemDTOs.size());
        assertEquals(product1, cartItemDTOs.get(0).getProduct());
        assertEquals(Integer.valueOf(5), cartItemDTOs.get(0).getQuantity());
        assertEquals(product2, cartItemDTOs.get(1).getProduct());
        assertEquals(Integer.valueOf(4), cartItemDTOs.get(1).getQuantity());

        Mockito.verify(cartRepository).findByUserId(userId);
    }


    @Test
    void addProductToCart() {
        // given
        Long cartId = 1L;
        Long productId = 1L;
        Integer quantity = 5;

        User user = new User(1L, "Test User", "test@example.com", "password", "USER", null);
        Cart cart = new Cart(cartId, user, 0.0f, new ArrayList<>());
        Product product = new Product(productId, "Product 1", 10.0, "Image 1", null);

        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        CartItem savedCartItem = new CartItem(null, cart, product, quantity);
        Mockito.when(cartItemRepository.save(Mockito.any(CartItem.class))).thenReturn(savedCartItem);

        // when
        CartItem resultCartItem = cartService.addProductToCart(cartId, productId, quantity);

        // then
        assertNotNull(resultCartItem);
        assertEquals(productId, resultCartItem.getProduct().getId());
        assertEquals(quantity, resultCartItem.getQuantity());

        Mockito.verify(cartRepository).findById(cartId);
        Mockito.verify(productRepository).findById(productId);
        Mockito.verify(cartItemRepository).save(Mockito.any(CartItem.class));
    }


    @Test
    void removeProductFromCart() {
        //given
        Long cartId = 1L;
        User user = new User(1L, "Test User", "test@example.com", "password", "USER", null);
        Cart cart = new Cart(cartId, user, 0.0f, new ArrayList<>());
        Product product1 = new Product(1L, "Product 1", 10.0, "Image 1", null);
        CartItem cartItem1 = new CartItem(1L, cart, product1, 5);

        Mockito.when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem1));

        //when
        boolean result = cartService.removeProductFromCart(1L);

        //then
        assertTrue(result);

        Mockito.verify(cartItemRepository).findById(1L);
        Mockito.verify(cartItemRepository).delete(cartItem1);
    }

    @Test
    void updateCartItemQuantity() {
        //given
        Long cartId = 1L;
        User user = new User(1L, "Test User", "test@example.com", "password", "USER", null);
        Cart cart = new Cart(cartId, user, 0.0f, new ArrayList<>());

        Product product1 = new Product(1L, "Product 1", 10.0, "Image 1", null);
        CartItem cartItem1 = new CartItem(1L, cart, product1, 5);
        CartItem cartItem2 = new CartItem(2L, cart, product1, 4);

        Mockito.when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem1));
        Mockito.when(cartItemRepository.save(Mockito.any(CartItem.class))).thenReturn(cartItem2);

        //when
        CartItem result = cartService.updateCartItemQuantity(1L, 4);

        //then
        assertEquals(cartItem2, result);
        Mockito.verify(cartItemRepository).findById(1L);
        Mockito.verify(cartItemRepository).save(Mockito.any(CartItem.class));
    }
}