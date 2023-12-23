package com.delivery.restaurant.products;

import com.delivery.restaurant.categories.Category;
import com.delivery.restaurant.categories.CategoryRepository;
import com.delivery.restaurant.users.User;
import com.delivery.restaurant.users.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private String token;

    private Long productId;

    @BeforeEach
    public void setup() throws Exception {
        userRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        User testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword(passwordEncoder.encode("password"));
        testUser.setRolesSet(Set.of("ROLE_USER"));
        userRepository.save(testUser);

        this.token = authenticateAndGetToken("test@example.com", "password");

        Category category = new Category();
        category.setName("Test Category");
        Category savedCategory = categoryRepository.save(category);

        Product product = new Product();
        product.setTitle("Test Product");
        product.setPrice(10.0);
        product.setImg("url");
        product.setCategory(savedCategory);
        Product savedProduct = productRepository.save(product);

        this.productId = savedProduct.getId();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        productRepository.deleteAll();
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
    public void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/products")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createProduct() throws Exception {
        long categoryId = categoryRepository.findAll().get(0).getId();
        String productJson = "{\"title\":\"New Product\",\"price\":10.0,\"img\":\"url\",\"categoryId\":" + categoryId + "}";

        mockMvc.perform(post("/api/v1/products/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProduct() throws Exception {
        String updatedProductJson = "{\"title\":\"Updated Product\",\"price\":15.0,\"img\":\"updatedUrl\",\"categoryId\":1}";
        mockMvc.perform(put("/api/v1/products/changing/{id}", productId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/products/deleting/{id}", productId) // Використовуйте збережений ID
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
