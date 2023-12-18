package com.delivery.restaurant.orders;

import com.delivery.restaurant.users.User;
import com.delivery.restaurant.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + orderDTO.getUserId()));

        Order order = new Order();
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setStatus(orderDTO.getStatus());
        order.setDate(orderDTO.getDate());
        order.setUser(user);

        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTODetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));

        order.setTotalPrice(orderDTODetails.getTotalPrice());
        order.setStatus(orderDTODetails.getStatus());
        order.setDate(orderDTODetails.getDate());

        if (!(order.getUser().getId() == (orderDTODetails.getUserId()))) {
            User user = userRepository.findById(orderDTODetails.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + orderDTODetails.getUserId()));
            order.setUser(user);
        }

        return orderRepository.save(order);
    }

    public boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
        orderRepository.delete(order);
        return true;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

}
