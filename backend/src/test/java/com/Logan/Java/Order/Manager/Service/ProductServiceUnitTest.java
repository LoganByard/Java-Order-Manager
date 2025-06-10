package com.Logan.Java.Order.Manager.Service;

import com.Logan.Java.Order.Manager.model.Product;
import com.Logan.Java.Order.Manager.repository.ProductRepository;
import com.Logan.Java.Order.Manager.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

/**
 * Potential message about this highlighting my testing skills/capabilities/reason for
 * not developing more tests for other classes
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProductSavesProductIfNameDoesNotExist() {

        String name = "Chair";
        String description = "An ergonomic spruce chair with a comfortable memory foam seat and back rest";
        Double price = 159.99;
        Integer stockQuantity = 132;

        when(productRepository.existsByNameIgnoreCase(name)).thenReturn(false);

        boolean result = productService.createProduct(name, description, price, stockQuantity);

        assertTrue(result);
        verify(productRepository).save(any(Product.class));

    }

    @Test
    void testCreateProductThrowsExceptionIfProductExists() {

        String name = "Chair";
        String description = "An ergonomic spruce chair with a comfortable memory foam seat and back rest";
        Double price = 159.99;
        Integer stockQuantity = 132;

        when(productRepository.existsByNameIgnoreCase(name)).thenReturn(true);

        try {
            boolean result = productService.createProduct(name, description, price, stockQuantity);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals("Product with name Chair already exists", e.getMessage());
        }

        verify(productRepository, never()).save(any(Product.class));

    }

    @Test
    void testFindByIdReturnsTrueIfProductExists() {

        String name = "Chair";
        String description = "An ergonomic spruce chair with a comfortable memory foam seat and back rest";
        Double price = 159.99;
        Integer stockQuantity = 132;
        Long Id = 9L;
        Product product = new Product(Id, name, description, price, stockQuantity);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product result = productService.findById(product.getId());

        assertEquals(product, result);

    }

    @Test
    void testFindByIdReturnsNullIfProductDoesNotExist() {

        String name = "Chair";
        String description = "An ergonomic spruce chair with a comfortable memory foam seat and back rest";
        Double price = 159.99;
        Integer stockQuantity = 132;
        Long Id = 9L;
        Product product = new Product(Id, name, description, price, stockQuantity);

        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        Product result = productService.findById(product.getId());

        assertNull(result);

    }

    @Test
    void testDeleteProductSuccessfully() {

        String name = "Chair";
        String description = "An ergonomic spruce chair with a comfortable memory foam seat and back rest";
        Double price = 159.99;
        Integer stockQuantity = 132;
        Long Id = 9L;
        Product product = new Product(Id, name, description, price, stockQuantity);

        productService.delete(product);

        verify(productRepository, times(1)).delete(product);

    }

    @Test
    void testDeleteByNameSuccessfully() {

        String name = "Chair";
        String description = "An ergonomic spruce chair with a comfortable memory foam seat and back rest";
        Double price = 159.99;
        Integer stockQuantity = 132;
        Long Id = 9L;
        Product product = new Product(Id, name, description, price, stockQuantity);

        when(productRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(product));

        productService.deleteByName(name);

        verify(productRepository, times(1)).deleteByNameIgnoreCase(name);

    }

    @Test
    void testDeleteProductByNameThrowsExceptionIfProductDoesNotExist() {

        String name = "Nonexistent";

        when(productRepository.findByNameIgnoreCase(name)).thenReturn(Optional.empty());

        try {
            productService.deleteByName(name);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Product with name 'Nonexistent' does not exist", e.getMessage());
        }

    }

    @Test
    void testUpdateProductByNameSuccessfullyUpdatesFields() {

        String name = "Chair";
        String originalDescription = "Old Desc";
        Double originalPrice = 50.0;
        Integer originalStock = 5;
        Long Id = 9L;
        Product existingProduct = new Product(Id, name, originalDescription, originalPrice, originalStock);

        when(productRepository.findByNameIgnoreCase(name)).thenReturn(Optional.of(existingProduct));

        String newDescription = "New ergonomic chair";
        Double newPrice = 75.0;

        productService.updateProductByName(name, newDescription, newPrice, null);

        assertEquals(newDescription, existingProduct.getDescription());
        assertEquals(newPrice, existingProduct.getPrice());
        assertEquals(originalStock, existingProduct.getStockQuantity()); // Unchanged

        verify(productRepository, times(1)).save(existingProduct);

    }

    @Test
    void testUpdateProductByNameThrowsExceptionIfNameIsNull() {

        String name = null;
        String description = "Updated Description";
        Double price = 99.99;
        Integer stockQuantity = 10;

        try {
            productService.updateProductByName(name, description, price, stockQuantity);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Product name must not be null.", e.getMessage());
        }

    }

    @Test
    void testUpdateProductByNameThrowsExceptionIfProductDoesNotExist() {

        String name = "Nonexistent Product";
        String description = "Doesn't matter";
        Double price = 10.0;
        Integer stockQuantity = 5;

        when(productRepository.findByNameIgnoreCase(name)).thenReturn(Optional.empty());

        try {
            productService.updateProductByName(name, description, price, stockQuantity);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Product with name 'Nonexistent Product' not found.", e.getMessage());
        }

    }

    @Test
    void testUpdateProductByIdSuccessfullyUpdatesProduct() {

        Long Id = 9L;
        String name = "Updated Name";
        String description = "Updated description";
        Double price = 49.99;
        Integer stockQuantity = 75;


        Product existingProduct = new Product(Id, "Old Name", "Old description", 39.99, 100);

        when(productRepository.findById(Id)).thenReturn(Optional.of(existingProduct));

        productService.updateProductById(Id, name, description, price, stockQuantity);

        assertEquals(name, existingProduct.getName());
        assertEquals(description, existingProduct.getDescription());
        assertEquals(price, existingProduct.getPrice());
        assertEquals(stockQuantity, existingProduct.getStockQuantity());

        verify(productRepository, times(1)).save(existingProduct);

    }

    @Test
    void testUpdateProductByIdThrowsExceptionIfIdIsNull() {

        String name = "Chair";
        String description = "Updated description";
        Double price = 59.99;
        Integer stockQuantity = 10;

        try {
            productService.updateProductById(null, name, description, price, stockQuantity);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Id cannot be null.", e.getMessage());
        }

    }

    @Test
    void testUpdateProductByIdThrowsExceptionIfProductDoesNotExist() {

        Long Id = 9L;
        String name = "Chair";
        String description = "Updated description";
        Double price = 59.99;
        Integer stockQuantity = 10;

        when(productRepository.findById(Id)).thenReturn(Optional.empty());

        try {
            productService.updateProductById(Id, name, description, price, stockQuantity);
            fail();
        } catch (EntityNotFoundException e) {
            assertEquals("Product with name Chair not found.", e.getMessage());
        }

    }
}
