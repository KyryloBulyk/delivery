package com.delivery.restaurant.products;

import com.delivery.restaurant.categories.Category;
import com.delivery.restaurant.categories.CategoryRepository;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    void getAllProducts() {
        //given
        Product testProduct1 = new Product(1L, "Example 1", 11.99, "image1", new Category());
        Product testProduct2 = new Product(2L, "Example 2", 12.99, "image2", new Category());
        List<Product> expectedProducts = Arrays.asList(testProduct1, testProduct2);

        Mockito.when(productRepository.findAll()).thenReturn(expectedProducts);

        //when
        List<Product> actualProducts = productService.getAllProducts();

        //then
        Mockito.verify(productRepository).findAll();
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void createProduct() {
        //given
        ProductDTO testProductDTO = new ProductDTO("Test", 11.99, "image", 1L);
        Category testCategory = new Category(1L, "Test Category");

        Product expectedProduct = new Product();
        expectedProduct.setTitle(testProductDTO.getTitle());
        expectedProduct.setPrice(testProductDTO.getPrice());
        expectedProduct.setImg(testProductDTO.getImg());
        expectedProduct.setCategory(testCategory);

        Mockito.when(categoryRepository.findById(testProductDTO.getCategoryId())).thenReturn(Optional.of(testCategory));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(expectedProduct);

        //when
        Product createdProduct = productService.createProduct(testProductDTO);

        //then
        assertEquals(expectedProduct, createdProduct);
        Mockito.verify(categoryRepository).findById(testProductDTO.getCategoryId());
        Mockito.verify(productRepository).save(Mockito.any(Product.class));
    }


    @Test
    void updateProduct() {
        //given
        ProductDTO updateProductDTO = new ProductDTO("Test", 11.99, "image", 1L);
        Category testCategory = new Category(1L, "Test Category");

        Product previousProduct = new Product();

        Product expectedProduct = new Product();
        expectedProduct.setTitle(updateProductDTO.getTitle());
        expectedProduct.setPrice(updateProductDTO.getPrice());
        expectedProduct.setImg(updateProductDTO.getImg());
        expectedProduct.setCategory(testCategory);

        Mockito.when(categoryRepository.findById(updateProductDTO.getCategoryId())).thenReturn(Optional.of(testCategory));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(expectedProduct);
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(previousProduct));

        //when
        Product actualProduct = productService.updateProduct(1L, updateProductDTO);

        //then
        assertEquals(expectedProduct, actualProduct);
        Mockito.verify(categoryRepository).findById(updateProductDTO.getCategoryId());
        Mockito.verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    void deleteProduct() {
        //given
        Product testProduct = new Product(1L, "Test", 11.99, "image", new Category());

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        //when
        boolean result = productService.deleteProduct(1L);

        //then
        assertTrue(result);
        Mockito.verify(productRepository).delete(testProduct);
    }
}