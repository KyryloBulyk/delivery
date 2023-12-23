package com.delivery.restaurant.users;

import jakarta.servlet.http.Cookie;
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

    private MvcResult authenticateAndGetToken(String username, String password) throws Exception {
        String authRequestJson = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        return mockMvc.perform(post("/api/v1/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    public void testGetAllUsers() throws Exception {
        MvcResult authResult = authenticateAndGetToken("test@example.com", "password");
        Cookie jwtTokenCookie = new Cookie("jwtToken", authResult.getResponse().getCookie("jwtToken").getValue());

        mockMvc.perform(get("/api/v1/users")
                        .cookie(jwtTokenCookie))
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
        MvcResult authResult = authenticateAndGetToken("test@example.com", "password");
        Cookie jwtTokenCookie = new Cookie("jwtToken", authResult.getResponse().getCookie("jwtToken").getValue());
        String updatedUserJson = "{\"name\":\"Updated User\",\"email\":\"updated@example.com\",\"password\":\"newpassword\",\"roles\":\"ADMIN\"}";

        User testUser = new User();
        testUser.setName("Old Test User");
        testUser.setEmail("oldtest@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        User savedUser = userRepository.save(testUser);
        long userId = savedUser.getId();

        mockMvc.perform(put("/api/v1/users/changing/" + userId)
                        .cookie(jwtTokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        MvcResult authResult = authenticateAndGetToken("test@example.com", "password");
        Cookie jwtTokenCookie = new Cookie("jwtToken", authResult.getResponse().getCookie("jwtToken").getValue());

        User testUser = new User();
        testUser.setName("Old Test User");
        testUser.setEmail("oldtest@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        User savedUser = userRepository.save(testUser);
        long userId = savedUser.getId();

        mockMvc.perform(delete("/api/v1/users/deleting/" + userId)
                        .cookie(jwtTokenCookie))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthenticateAndGetToken() throws Exception {
        String authRequestJson = "{\"username\":\"test@example.com\",\"password\":\"password\"}";

        MvcResult result = mockMvc.perform(post("/api/v1/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authRequestJson))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("jwtToken"))
                .andReturn();

    }



}
