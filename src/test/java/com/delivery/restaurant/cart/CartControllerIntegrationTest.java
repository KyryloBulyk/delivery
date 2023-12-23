package com.delivery.restaurant.cart;

import com.delivery.restaurant.cart.Cart;
import com.delivery.restaurant.cart.CartItem;
import com.delivery.restaurant.cart.CartItemRepository;
import com.delivery.restaurant.cart.CartRepository;
import com.delivery.restaurant.products.Product;
import com.delivery.restaurant.products.ProductRepository;
import com.delivery.restaurant.users.User;
import com.delivery.restaurant.users.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String token;
    private Long userId;
    private Long productId;
    private Long cartId;

    @BeforeEach
    public void setup() throws Exception {
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        Cart cart = new Cart(1L, testUser, 0.0f, new ArrayList<>());
        testUser.setCart(cart);
        testUser = userRepository.save(testUser);

        this.userId = testUser.getId();
        this.cartId = testUser.getCart().getCartId();

        Product product = new Product();
        product.setTitle("Test Product");
        product.setPrice(10.0);
        product.setImg("url");
        product = productRepository.save(product);

        this.productId = product.getId();
        this.token = authenticateAndGetToken("test@example.com", "password");
    }

    private String authenticateAndGetToken(String username, String password) throws Exception {
        String authRequestJson = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        MvcResult result = mockMvc.perform(post("/api/v1/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    @AfterEach
    void tearDown() {
        cartItemRepository.deleteAll();
        cartRepository.deleteAll();

        userRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void getCartItemsByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/cart")
                        .param("userId", userId.toString())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void addProductToCart() throws Exception {
        mockMvc.perform(post("/api/v1/cart/add")
                        .param("cartId", cartId.toString())
                        .param("productId", productId.toString())
                        .param("quantity", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }


    @Test
    public void updateCartItemQuantity() throws Exception {
        CartItem cartItem = new CartItem(null, new Cart(cartId, null, 0.0f, null), new Product(productId, "Test Product", 10.0, "url", null), 1);
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        mockMvc.perform(put("/api/v1/cart/update")
                        .param("cartItemId", savedCartItem.getCartItemId().toString())
                        .param("quantity", "2")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void removeProductFromCart() throws Exception {
        CartItem cartItem = new CartItem(null, new Cart(cartId, null, 0.0f, null), new Product(productId, "Test Product", 10.0, "url", null), 1);
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        mockMvc.perform(delete("/api/v1/cart/remove")
                        .param("cartItemId", savedCartItem.getCartItemId().toString())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
