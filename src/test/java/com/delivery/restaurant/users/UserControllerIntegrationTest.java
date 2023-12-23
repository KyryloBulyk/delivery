package com.delivery.restaurant.users;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();

        User testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        userRepository.save(testUser);
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


    @Test
    public void testGetAllUsers() throws Exception {
        String token = authenticateAndGetToken("test@example.com", "password");

        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"name\":\"New Test User\",\"email\":\"newtest@example.com\",\"password\":\"password\",\"roles\":\"USER\"}";

        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        String token = authenticateAndGetToken("test@example.com", "password");
        String updatedUserJson = "{\"name\":\"Updated User\",\"email\":\"updated@example.com\",\"password\":\"newpassword\",\"roles\":\"ADMIN\"}";

        User testUser = new User();
        testUser.setName("Old Test User");
        testUser.setEmail("oldtest@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        User savedUser = userRepository.save(testUser);
        long userId = savedUser.getId();

        mockMvc.perform(put("/api/v1/users/changing/" + userId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        String token = authenticateAndGetToken("test@example.com", "password");

        User testUser = new User();
        testUser.setName("Old Test User");
        testUser.setEmail("oldtest@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        User savedUser = userRepository.save(testUser);
        long userId = savedUser.getId();

        mockMvc.perform(delete("/api/v1/users/deleting/" + userId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticateAndGetToken() throws Exception {
        String authRequestJson = "{\"username\":\"test@example.com\",\"password\":\"password\"}";

        MvcResult result = mockMvc.perform(post("/api/v1/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertFalse(responseBody.isEmpty());
    }


}
