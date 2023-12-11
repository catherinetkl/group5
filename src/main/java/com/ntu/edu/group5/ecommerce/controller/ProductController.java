package com.ntu.edu.group5.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.ntu.edu.group5.ecommerce.entity.CategoryEnum;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.ProductDTO;
import com.ntu.edu.group5.ecommerce.entity.ProductStatus;
import com.ntu.edu.group5.ecommerce.exception.ProductNotFoundException;
import com.ntu.edu.group5.ecommerce.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping({"/", ""})
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProductsInCatalog();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductFromCatalogById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping({"", "/"})
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product addedProduct = productService.addProductToCatalog(null, product);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProductInCatalog(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductFromCatalog(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get all products of a specific seller
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductDTO>> getProductsOfSeller(@PathVariable Long sellerId) {
        List<ProductDTO> products = productService.getAllProductsOfSeller(sellerId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get all products in a specific category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsInCategory(@PathVariable String category) {
        CategoryEnum categoryEnum = CategoryEnum.valueOf(category.toUpperCase());
        List<ProductDTO> products = productService.getProductsOfCategory(categoryEnum);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get all products with a specific status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@PathVariable String status) {
        ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
        List<ProductDTO> products = productService.getProductsOfStatus(productStatus);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
