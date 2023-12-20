package com.delivery.restaurant.categories;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getAllCategories_ShouldReturnAllCategories() throws Exception {
        //given
        Category testCategory1 = new Category(1L, "Test Category 1");
        Category testCategory2 = new Category(2L, "Test Category 2");
        List<Category> expectedCategories = Arrays.asList(testCategory1, testCategory2);

        Mockito.when(categoryService.getAllCategories()).thenReturn(expectedCategories);

        //when & then
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    void createCategory_ShouldReturnCreatedCategory() throws Exception {
        //given
        String categoryJson = "{\"name\":\"Test Category\"}";
        Category testCategory1 = new Category(1L, "Test Category");

        Mockito.when(categoryService.createCategory(Mockito.any(Category.class))).thenReturn(testCategory1);

        //when & then
        mockMvc.perform(post("/api/v1/categories/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(categoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"));

        Mockito.verify(categoryService).createCategory(Mockito.any(Category.class));
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory() throws Exception {
        //given
        Long categoryId = 1L;
        String categoryJson = "{\"name\":\"Test Category\"}";
        Category testCategory1 = new Category(categoryId, "Test Category");

        Mockito.when(categoryService.updateCategory(Mockito.eq(categoryId), Mockito.any(Category.class))).thenReturn(testCategory1);

        //when & then
        mockMvc.perform(put("/api/v1/categories/changing/" + categoryId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(categoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"));

        Mockito.verify(categoryService).updateCategory(Mockito.eq(categoryId), Mockito.any(Category.class));
    }

    @Test
    void deleteCategory_ShouldReturnOkStatus() throws Exception {
        //given
        Long categoryId = 1L;

        Mockito.when(categoryService.deleteCategory(Mockito.eq(categoryId))).thenReturn(true);

        //when & then
        mockMvc.perform(delete("/api/v1/categories/deleting/" + categoryId))
                .andExpect(status().isOk());
    }
}