package com.delivery.restaurant.categories;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllCategories() {
        //given
        Category testCategory1 = new Category(1L, "Example 1");
        Category testCategory2 = new Category(2L, "Example 2");

        List<Category> expectedCategories = Arrays.asList(testCategory1, testCategory2);;

        Mockito.when(categoryRepository.findAll()).thenReturn(expectedCategories);

        //when
        List<Category> actualCategories = categoryService.getAllCategories();

        //then
        assertEquals(expectedCategories, actualCategories);
        Mockito.verify(categoryRepository).findAll();
    }

    @Test
    void createCategory() {
        //given
        Category testCategory1 = new Category(1L, "Example 1");

        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(testCategory1);

        //when
        Category actualCategory = categoryService.createCategory(testCategory1);

        //then
        assertEquals(testCategory1, actualCategory);
        Mockito.verify(categoryRepository).save(testCategory1);
    }

    @Test
    void updateCategory() {
        //given
        Category testCategory1 = new Category(1L, "Example 1");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1L, "Example 2")));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(testCategory1);

        //when
        Category updateCategory = categoryService.updateCategory(1L, testCategory1);

        //then
        assertEquals(updateCategory, testCategory1);
        Mockito.verify(categoryRepository).findById(1L);
        Mockito.verify(categoryRepository).save(testCategory1);
    }

    @Test
    void deleteCategory() {
        //given
        Category testCategory1 = new Category(1L, "Example 1");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory1));

        //when
        boolean result = categoryService.deleteCategory(1L);

        //then
        assertTrue(result);
        Mockito.verify(categoryRepository).delete(testCategory1);

    }
}