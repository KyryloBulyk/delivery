package com.delivery.restaurant.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    void getAllUsers() {
        //given
        User user1 = new User(1, "User 1", "user1@example.com", "password123", new HashSet<>());
        User user2 = new User(2, "User 2", "user2@example.com", "password456", new HashSet<>());

        List<User> expectedUsers = Arrays.asList(user1, user2);

        //when
        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getAllUsers();

        //then
        assertEquals(expectedUsers, actualUsers);
    }
}