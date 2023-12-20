package com.delivery.restaurant.users;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        //given
        User user1 = new User(1L, "User1", "user1@example.com", "password", null);
        User user2 = new User(2L, "User2", "user2@example.com", "password", null);
        List<User> users = Arrays.asList(user1, user2);
        Mockito.when(userService.getAllUsers()).thenReturn(users);

        //when & then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void createUser_ShouldReturnCreatedUser() throws Exception {
        // given
        String userJson = "{\"name\":\"Test User\",\"email\":\"test@example.com\",\"password\":\"password\"}";
        User createdUser = new User(1L, "Test User", "test@example.com", "password", null);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(createdUser);

        //when & then
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));

        Mockito.verify(userService).createUser(Mockito.any(User.class));
    }

    @Test
    public void loginUser_ShouldReturnUser_WhenValidCredentials() throws Exception {
        //given
        String loginJson = "{\"email\":\"user1@example.com\",\"password\":\"password\"}";
        User authenticatedUser = new User(1L, "User1", "user1@example.com", "password", null);

        Mockito.when(userService.authenticateUser("user1@example.com", "password")).thenReturn(authenticatedUser);

        //when & then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user1@example.com"))
                .andExpect(jsonPath("$.name").value("User1"));

        Mockito.verify(userService).authenticateUser("user1@example.com", "password");
    }

    @Test
    public void updateUser_ShouldUpdateAndReturnUser() throws Exception {
        // given
        Long userId = 1L;
        String userUpdateJson = "{\"name\":\"Updated User\",\"email\":\"update@example.com\",\"password\":\"newpassword\"}";
        User updatedUser = new User(userId, "Updated User", "update@example.com", "newpassword", null);

        Mockito.when(userService.updateUser(Mockito.eq(userId), Mockito.any(User.class))).thenReturn(updatedUser);

        // when & then
        mockMvc.perform(put("/api/v1/users/changing/" + userId)
                        .contentType(APPLICATION_JSON)
                        .content(userUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andExpect(jsonPath("$.email").value("update@example.com"));

        Mockito.verify(userService).updateUser(Mockito.eq(userId), Mockito.any(User.class));
    }

    @Test
    public void deleteUser_ShouldReturnOkStatus() throws Exception {
        // given
        Long userId = 1L;
        Mockito.when(userService.deleteUser(userId)).thenReturn(true);

        // when & then
        mockMvc.perform(delete("/api/v1/users/deleting/" + userId))
                .andExpect(status().isOk());

        Mockito.verify(userService).deleteUser(userId);
    }
}