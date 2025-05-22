package com.Logan.Java.Order.Manager.service;

import com.Logan.Java.Order.Manager.model.Product;
import com.Logan.Java.Order.Manager.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){

        this.productRepository = productRepository;

    }

    /**
     * Creates a product
     *
     * @param name of product
     * @param description of product
     * @param price or product
     * @param stockQuantity quantity in stock
     */
    //TODO: security and role authentification
    public boolean createProduct(String name, String description, Double price, Integer stockQuantity){

        if (productRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Product with name " + name + " already exists");
        } else {
            Product product = new Product(null, name, description, price, stockQuantity);
            productRepository.save(product);

            return true;
        }

    }

    public Product findById(Long Id) {

        return productRepository.findById(Id).orElse(null);

    }
    public void deleteProductById(Long Id){

        if (this.findById(Id) == null) {
            throw new EntityNotFoundException("Product with Id " + Id + " does not exist.");
        }

        productRepository.deleteById(Id);

    }

    public void delete(Product product) {

        productRepository.delete(product);

    }

    public void deleteByName(String name) {

        if (this.findByName(name) == null) {
            throw new EntityNotFoundException("Product with name '" + name + "' does not exist");
        }

        productRepository.deleteByNameIgnoreCase(name);

    }

    public List<Product> searchByName(String name) {

        return productRepository.findByNameContainingIgnoreCase(name);

    }

    public Product findByName(String name) {

        return productRepository.findByNameIgnoreCase(name);

    }

    public void updateProductByName(String name, String description, Double price, Integer stockQuantity) {

        if (name == null) {
            throw new IllegalArgumentException("Product name must not be null.");
        }

        Product product = this.findByName(name);
        if (product == null) {
            throw new EntityNotFoundException("Product with name '" + name + "' not found.");
        }

        if (description != null) {
            product.setDescription(description);
        }

        if (price != null) {
            product.setPrice(price);
        }

        if (stockQuantity != null) {
            product.setStockQuantity(stockQuantity);
        }

        productRepository.save(product);

    }

    public void updateProductById(Long Id, String name, String description, Double price, Integer stockQuantity) {

        if (Id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }

        Product product = this.findById(Id);
        if (product == null) {
            System.out.println("Product does not exist");
            throw new EntityNotFoundException("Product with name " + name + " not found.");
        }

        if (name != null) {
            product.setName(name);
        }

        if (description != null) {
            product.setDescription(description);
        }

        if (price != null) {
            product.setPrice(price);
        }

        if (stockQuantity != null) {
            product.setStockQuantity(stockQuantity);
        }

        productRepository.save(product);
        
    }
}
