package com.delivery.restaurant.orders;

import com.delivery.restaurant.users.User;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getAllProducts_ShouldReturnAllOrders() throws Exception {
        //given
        User testUser1 = new User(1L, "Test User 1", "test1@example.com", "password123", null);
        User testUser2 = new User(2L, "Test User 2", "test2@example.com", "password456", null);
        Order testOrder1 = new Order(1L, testUser1, 149.99, "new", new Date());
        Order testOrder2 = new Order(2L, testUser2, 249.99, "new", new Date());

        List<Order> expectedOrders = Arrays.asList(testOrder1, testOrder2);

        Mockito.when(orderService.getAllOrders()).thenReturn(expectedOrders);

        //when & then
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    void getOrdersByUserId_ShouldReturnAllOrdersForUser() throws Exception {
        //given
        Long userId = 1L;
        User testUser1 = new User(userId, "Test User 1", "test1@example.com", "password123", null);
        Order testOrder1 = new Order(1L, testUser1, 149.99, "new", new Date());
        Order testOrder2 = new Order(2L, testUser1, 249.99, "new", new Date());

        List<Order> expectedOrders = Arrays.asList(testOrder1, testOrder2);

        Mockito.when(orderService.getOrdersByUserId(Mockito.eq(userId))).thenReturn(expectedOrders);

        //when & then
        mockMvc.perform(get("/api/v1/orders/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        //given
        String orderJson = "{\"userId\":\"1\",\"totalPrice\":\"149.99\",\"status\":\"new status\",\"date\":\"2023-12-19T00:00:00.000+00:00\"}";
        User testUser1 = new User(1L, "Test User 1", "test1@example.com", "password123", null);
        Order testOrder1 = new Order(1L, testUser1, 149.99, "new status", new Date());

        Mockito.when(orderService.createOrder(Mockito.any(OrderDTO.class))).thenReturn(testOrder1);

        //when & then
        mockMvc.perform(post("/api/v1/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(149.99))
                .andExpect(jsonPath("$.status").value("new status"));

        Mockito.verify(orderService).createOrder(Mockito.any(OrderDTO.class));
    }

    @Test
    void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        //given
        Long orderId = 1L;
        String orderJson = "{\"userId\":\"1\",\"totalPrice\":\"149.99\",\"status\":\"new status\",\"date\":\"2023-12-19T00:00:00.000+00:00\"}";
        User testUser1 = new User(1L, "Test User 1", "test1@example.com", "password123", null);
        Order updatedOrder = new Order(orderId, testUser1, 149.99, "new status", new Date());

        Mockito.when(orderService.updateOrder(Mockito.eq(orderId) ,Mockito.any(OrderDTO.class))).thenReturn(updatedOrder);

        //when & then
        mockMvc.perform(put("/api/v1/orders/changing/" + orderId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(149.99))
                .andExpect(jsonPath("$.status").value("new status"));

        Mockito.verify(orderService).updateOrder(Mockito.eq(orderId) ,Mockito.any(OrderDTO.class));
    }

    @Test
    void deleteOrder_ShouldReturnOkStatus() throws Exception {
        //given
        Long orderId = 1L;

        Mockito.when(orderService.deleteOrder(Mockito.eq(orderId))).thenReturn(true);

        //when & then
        mockMvc.perform(delete("/api/v1/orders/deleting/" + orderId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrder_ShouldReturnNotFoundStatus() throws Exception {
        //given
        Long orderId = 1L;

        Mockito.when(orderService.deleteOrder(Mockito.eq(orderId))).thenReturn(false);

        //when & then
        mockMvc.perform(delete("/api/v1/orders/deleting/" + orderId))
                .andExpect(status().isNotFound());
    }
}