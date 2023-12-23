package com.delivery.restaurant.orders;

import com.delivery.restaurant.users.User;
import com.delivery.restaurant.users.UserRepository;
import jakarta.servlet.http.Cookie;
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

import java.util.Date;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Cookie jwtTokenCookie;
    private Long userId;
    private Long orderId;

    @BeforeEach
    public void setup() throws Exception {
        User testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        User savedUser = userRepository.save(testUser);

        Order order = new Order();
        order.setTotalPrice(100.0);
        order.setStatus("NEW");
        order.setUser(savedUser);
        order.setDate(new Date(System.currentTimeMillis()));
        Order savedOrder = orderRepository.save(order);

        this.orderId = savedOrder.getId();
        this.userId = savedUser.getId();

        MvcResult authResult = authenticateAndGetToken("test@example.com", "password");
        this.jwtTokenCookie = new Cookie("jwtToken", authResult.getResponse().getCookie("jwtToken").getValue());
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }

    private MvcResult authenticateAndGetToken(String username, String password) throws Exception {
        String authRequestJson = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        return mockMvc.perform(post("/api/v1/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAllOrders() throws Exception {
        mockMvc.perform(get("/api/v1/orders")
                        .cookie(jwtTokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getOrdersByUserId() throws Exception {
        mockMvc.perform(get("/api/v1/orders/user/{userId}", userId)
                        .cookie(jwtTokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createOrder() throws Exception {
        String orderJson = "{\"userId\":" + userId + ", \"totalPrice\":100.0, \"status\":\"NEW\", \"date\":\"2023-01-01\"}";
        mockMvc.perform(post("/api/v1/orders/create")
                        .cookie(jwtTokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrder() throws Exception {
        Order order = new Order();
        order.setTotalPrice(100.0);
        order.setStatus("NEW");
        order.setUser(userRepository.findById(userId).get());
        Order savedOrder = orderRepository.save(order);

        String updatedOrderJson = "{\"userId\":" + userId + ", \"totalPrice\":200.0, \"status\":\"UPDATED\", \"date\":\"2023-01-02\"}";
        mockMvc.perform(put("/api/v1/orders/changing/{id}", savedOrder.getId())
                        .cookie(jwtTokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedOrderJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteOrder() throws Exception {
        Order order = new Order();
        order.setTotalPrice(100.0);
        order.setStatus("NEW");
        order.setUser(userRepository.findById(userId).get());
        Order savedOrder = orderRepository.save(order);

        mockMvc.perform(delete("/api/v1/orders/deleting/{id}", savedOrder.getId())
                        .cookie(jwtTokenCookie))
                .andExpect(status().isOk());
    }
}
