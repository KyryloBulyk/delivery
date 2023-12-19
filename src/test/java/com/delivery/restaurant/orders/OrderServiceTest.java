package com.delivery.restaurant.orders;

import com.delivery.restaurant.cart.Cart;
import com.delivery.restaurant.users.User;
import com.delivery.restaurant.users.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void getAllOrders() {
        //given
        Order testOrder1 = new Order(1L, new User(), 12.99, "example status 1", new Date());
        Order testOrder2 = new Order(2L, new User(), 13.99, "example status 2", new Date());

        List<Order> expectedOrders = Arrays.asList(testOrder1, testOrder2);

        Mockito.when(orderRepository.findAll()).thenReturn(expectedOrders);

        //when
        List<Order> actualOrders = orderService.getAllOrders();

        //then
        assertEquals(actualOrders, expectedOrders);
        Mockito.verify(orderRepository).findAll();

    }

    @Test
    void createOrder() {
        //given
        OrderDTO testOrderDTO = new OrderDTO(1L, 1L, 12.99, "test status", new Date());
        User testUser = new User(1L, "Test Name", "test@emaple.com", "password", new Cart());

        Order testOrder = new Order();
        testOrder.setTotalPrice(testOrderDTO.getTotalPrice());
        testOrder.setStatus(testOrderDTO.getStatus());
        testOrder.setDate(testOrderDTO.getDate());
        testOrder.setUser(testUser);

        Mockito.when(userRepository.findById(testOrderDTO.getUserId())).thenReturn(Optional.of(testUser));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(testOrder);

        //when
        Order actualOrder = orderService.createOrder(testOrderDTO);

        //then
        assertEquals(actualOrder, testOrder);
        Mockito.verify(userRepository).findById(testOrderDTO.getUserId());
        Mockito.verify(orderRepository).save(testOrder);


    }

    @Test
    void updateOrder() {
        // given
        Long differentUserId = 2L;
        OrderDTO testOrderDTO = new OrderDTO(1L, differentUserId, 12.99, "test status", new Date());
        User testUser = new User(1L, "Test Name", "test@example.com", "password", new Cart());
        User newUser = new User(differentUserId, "New User", "new@example.com", "newpassword", new Cart());

        Order existingOrder = new Order();
        existingOrder.setId(1L);
        existingOrder.setUser(testUser);

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        Mockito.when(userRepository.findById(differentUserId)).thenReturn(Optional.of(newUser));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(existingOrder);

        // when
        Order actualOrder = orderService.updateOrder(1L, testOrderDTO);

        // then
        assertNotNull(actualOrder);
        assertEquals(differentUserId, actualOrder.getUser().getId());
        assertEquals(testOrderDTO.getTotalPrice(), actualOrder.getTotalPrice());
        assertEquals(testOrderDTO.getStatus(), actualOrder.getStatus());
        assertEquals(testOrderDTO.getDate(), actualOrder.getDate());
        Mockito.verify(orderRepository).findById(1L);
        Mockito.verify(userRepository).findById(differentUserId);
        Mockito.verify(orderRepository).save(Mockito.any(Order.class));
    }

    @Test
    void deleteOrder() {
        //given
        Order testOrder = new Order(1L, new User(), 12.99, "test status", new Date());

        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        //when
        boolean result = orderService.deleteOrder(1L);

        //then
        assertTrue(result);
        Mockito.verify(orderRepository).findById(1L);
        Mockito.verify(orderRepository).delete(testOrder);

    }

    @Test
    void getOrdersByUserId() {
        //given
        Order testOrder1 = new Order(1L, new User(), 12.99, "test status 1", new Date());
        Order testOrder2 = new Order(2L, new User(), 15.99, "test status 2", new Date());

        List<Order> expectedOrders = Arrays.asList(testOrder1, testOrder2);

        Mockito.when(orderRepository.findAllByUserId(1L)).thenReturn(expectedOrders);

        //when
        List<Order> actualOrders = orderService.getOrdersByUserId(1L);

        //then
        assertEquals(expectedOrders, actualOrders);
        Mockito.verify(orderRepository).findAllByUserId(1L);
    }
}