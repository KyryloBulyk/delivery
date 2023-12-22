package com.delivery.restaurant.users;

import com.delivery.restaurant.cart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Test User", "test@example.com", "password","USER", new Cart());
    }

    @Test
    void getAllUsers() {
        //given
        User anotherUser =  new User(2L, "Another User", "another@example.com", "password","USER", new Cart());
        List<User> expectedUsers = Arrays.asList(user, anotherUser);
        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);

        //when
        List<User> actualUsers = userService.getAllUsers();

        //then
        assertEquals(expectedUsers, actualUsers);
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("encodedPassword", createdUser.getPassword());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void createUserWithExistingUser() {
        //given
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        //when
        User resultUser = userService.createUser(user);

        //then
        Mockito.verify(userRepository).findByEmail(user.getEmail());
        assertNull(resultUser);
    }

    @Test
    void updateUser() {
        // given
        User existingUser = new User(1L, "Existing User", "existing@example.com", "password", "USER", new Cart());
        User updateUser = new User(1L, "Another User", "another@example.com", "password123", "USER", new Cart());

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updateUser);

        // when
        User actualUser = userService.updateUser(1L, updateUser);

        // then
        assertNotNull(actualUser);
        assertEquals(updateUser.getName(), actualUser.getName());
        assertEquals(updateUser.getEmail(), actualUser.getEmail());
        assertEquals(updateUser.getPassword(), actualUser.getPassword());

        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).save(Mockito.any(User.class));
    }

    @Test
    void updateUserWhenUserEqualsNull() {
        //given
        User updateUser = new User(1L, "Another User", "another@example.com", "password123", "USER", new Cart());

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        User actualUser = userService.updateUser(1L, updateUser);

        //then
        assertNull(actualUser);
        Mockito.verify(userRepository).findById(1L);
    }

    @Test
    void deleteUser() {
        //given
        User existingUser = new User(1L, "Existing User", "existing@example.com", "password", "USER", new Cart());
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        //when
        boolean result = userService.deleteUser(1L);

        //then
        assertTrue(result);
        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).delete(existingUser);
    }

    @Test
    void deleteUserWhenUserEqualsNull() {
        //given
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        boolean result = userService.deleteUser(1L);

        //then
        assertFalse(result);
        Mockito.verify(userRepository).findById(1L);
    }

    @Test
    void authenticateUser() {
        //given
        User testUser = new User(1L, "Test User", "test@example.com", "password", "USER", new Cart());
        String testEmail = "test@example.com";
        String testPassword = "password";

        Mockito.when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(testUser));

        //when
        User resultUser = userService.authenticateUser(testEmail, testPassword);

        //then
        Mockito.verify(userRepository).findByEmail(testEmail);
        assertEquals(testUser, resultUser);
    }

    @Test
    void authenticateUserWhenUserEqualsNull() {
        //given
        String testEmail = "test@example.com";
        String testPassword = "password";
        Mockito.when(userRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        //when
        User resultUser = userService.authenticateUser(testEmail, testPassword);

        //then
        Mockito.verify(userRepository).findByEmail(testEmail);
        assertNull(resultUser);
    }

}