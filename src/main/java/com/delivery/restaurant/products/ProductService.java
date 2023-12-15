package com.delivery.restaurant.products;

import com.delivery.restaurant.categories.Category;
import com.delivery.restaurant.categories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setImg(productDTO.getImg());

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        return productRepository.save(product);
    }


    public Product updateProduct(Long id, ProductDTO productDTODetails) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        product.setTitle(productDTODetails.getTitle());
        product.setPrice(productDTODetails.getPrice());
        product.setImg(productDTODetails.getImg());

        Category category = categoryRepository.findById(productDTODetails.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if(category != null) {
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return false;
        }

        productRepository.delete(product);
        return true;
    }
}
