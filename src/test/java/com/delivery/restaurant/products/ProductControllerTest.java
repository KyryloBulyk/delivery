package com.delivery.restaurant.products;

import com.delivery.restaurant.categories.Category;
import com.delivery.restaurant.users.User;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception {
        //given
        Category category1 = new Category(1L, "category1");
        Category category2 = new Category(2L, "category2");
        Product product1 = new Product(1L, "name1", 11.99, "image1", category1);
        Product product2 = new Product(2L, "name2", 15.99, "image2", category2);

        List<Product> products = Arrays.asList(product1, product2);

        Mockito.when(productService.getAllProducts()).thenReturn(products);

        //when & then
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        //given
        String productJson = "{\"title\":\"Test Product\",\"price\":\"11.99\",\"img\":\"image\",\"categoryId\":\"1\"}";
        Category testCategory = new Category(1L, "Test Category");
        Product product = new Product(1L, "Test Product", 11.99, "image", testCategory);

        Mockito.when(productService.createProduct(Mockito.any(ProductDTO.class))).thenReturn(product);

        //when & then
        mockMvc.perform(post("/api/v1/products/create")
                .contentType(APPLICATION_JSON)
                .content(productJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Test Product"))
            .andExpect(jsonPath("$.price").value("11.99"));

        Mockito.verify(productService).createProduct(Mockito.any(ProductDTO.class));
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnProduct() throws Exception {
        // given
        Long productId = 1L;
        String productUpdateJson = "{\"title\":\"Updated Product\",\"price\":\"12.99\",\"img\":\"image\",\"categoryId\":\"1\"}";
        Category testCategory = new Category(1L, "Test Category");
        Product updatedProduct = new Product(productId, "Updated Product", 12.99, "image", testCategory);

        Mockito.when(productService.updateProduct(Mockito.eq(productId), Mockito.any(ProductDTO.class))).thenReturn(updatedProduct);

        // when & then
        mockMvc.perform(put("/api/v1/products/changing/" + productId)
                        .contentType(APPLICATION_JSON)
                        .content(productUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(12.99));

        Mockito.verify(productService).updateProduct(Mockito.eq(productId), Mockito.any(ProductDTO.class));
    }


    @Test
    void deleteProduct_ShouldReturnOkStatus() throws Exception {
        // given
        Long productId = 1L;
        Mockito.when(productService.deleteProduct(productId)).thenReturn(true);

        // when & then
        mockMvc.perform(delete("/api/v1/products/deleting/" + productId))
                .andExpect(status().isOk());

        Mockito.verify(productService).deleteProduct(productId);
    }


}