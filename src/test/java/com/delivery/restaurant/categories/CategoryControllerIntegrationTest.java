package com.delivery.restaurant.categories;

import com.delivery.restaurant.users.User;
import com.delivery.restaurant.users.UserRepository;
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

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    private String token;

    @BeforeEach
    public void setup() throws Exception {
        User testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        userRepository.save(testUser);

        this.token = authenticateAndGetToken("test@example.com", "password");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
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
    public void getAllCategories() throws Exception {
        mockMvc.perform(get("/api/v1/categories")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createCategory() throws Exception {
        String categoryJson = "{\"name\":\"New Category\"}";

        mockMvc.perform(post("/api/v1/categories/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCategory() throws Exception {
        Category category = new Category();
        category.setName("Old Category");
        Category savedCategory = categoryRepository.save(category);

        String updatedCategoryJson = "{\"name\":\"Updated Category\"}";
        mockMvc.perform(put("/api/v1/categories/changing/{id}", savedCategory.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCategoryJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCategory() throws Exception {
        Category category = new Category();
        category.setName("Category to Delete");
        Category savedCategory = categoryRepository.save(category);

        mockMvc.perform(delete("/api/v1/categories/deleting/{id}", savedCategory.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
