package com.delivery.restaurant.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"name\":\"Test User\",\"email\":\"test@example.com\",\"password\":\"password\",\"roles\":\"USER\"}";

        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        String updatedUserJson = "{\"name\":\"Updated User\",\"email\":\"updated@example.com\",\"password\":\"newpassword\",\"roles\":\"ADMIN\"}";

        mockMvc.perform(put("/api/v1/users/changing/1") // Замініть 1 на існуючий ID користувача
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/deleting/1")) // Замініть 1 на існуючий ID користувача
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticateAndGetToken() throws Exception {
        String authRequestJson = "{\"username\":\"test@example.com\",\"password\":\"password\"}";

        mockMvc.perform(post("/api/v1/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andExpect(header().exists("Set-Cookie")); // Перевіряємо, чи встановлено кукі
    }

}
