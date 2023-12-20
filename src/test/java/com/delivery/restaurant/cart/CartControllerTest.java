package com.delivery.restaurant.cart;

import com.delivery.restaurant.products.Product;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void getCartItemsByUserId_ShouldReturnAllProductsForUser() throws Exception {
        //given
        Long userId = 1L;
        CartItemDTO testCartItemDTO1 = new CartItemDTO(1L, new Product(), 5);
        CartItemDTO testCartItemDTO2 = new CartItemDTO(2L, new Product(), 5);

        List<CartItemDTO> cartItems = Arrays.asList(testCartItemDTO1, testCartItemDTO2);

        Mockito.when(cartService.getCartItemsByUserId(Mockito.eq(userId))).thenReturn(cartItems);

        //when & then
        mockMvc.perform(get("/api/v1/cart?userId=" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));

        Mockito.verify(cartService).getCartItemsByUserId(Mockito.eq(userId));
    }

    @Test
    void addProductToCart_ShouldReturnNewCartItem() throws Exception {
        //given
        Long cartId = 1L;
        Long productId = 2L;
        Integer quantity = 5;

        CartItem expectedCartItem = new CartItem(1L, new Cart(), new Product(), 5);

        Mockito.when(cartService.addProductToCart(Mockito.eq(cartId), Mockito.eq(productId), Mockito.eq(quantity))).thenReturn(expectedCartItem);

        //when & then
        mockMvc.perform(post("/api/v1/cart/add?cartId=" + cartId + "&productId=" + productId + "&quantity=" + quantity))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").value(1L))
                .andExpect(jsonPath("$.quantity").value(5));

        Mockito.verify(cartService).addProductToCart(Mockito.eq(cartId), Mockito.eq(productId), Mockito.eq(quantity));
    }

    @Test
    void updateCartItemQuantity_ShouldReturnUpdatedCartItem() throws Exception {
        //given
        Long cartItemId = 1L;
        Integer quantity = 5;

        CartItem expectedCartItem = new CartItem(1L, new Cart(), new Product(), 5);

        Mockito.when(cartService.updateCartItemQuantity(Mockito.eq(cartItemId), Mockito.eq(quantity))).thenReturn(expectedCartItem);

        //when & then
        mockMvc.perform(put("/api/v1/cart/update?cartItemId=" + cartItemId + "&quantity=" + quantity))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemId").value(1L))
                .andExpect(jsonPath("$.quantity").value(5));

        Mockito.verify(cartService).updateCartItemQuantity(Mockito.eq(cartItemId), Mockito.eq(quantity));

    }

    @Test
    void removeProductFromCart_ShouldReturnOkStatus() throws Exception {
        //given
        Long cartItemId = 1L;

        Mockito.when(cartService.removeProductFromCart(Mockito.eq(cartItemId))).thenReturn(true);

        //when & then
        mockMvc.perform(delete("/api/v1/cart/remove?cartItemId=" + cartItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));

        Mockito.verify(cartService).removeProductFromCart(Mockito.eq(cartItemId));
    }
}