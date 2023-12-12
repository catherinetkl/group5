package com.ntu.edu.group5.ecommerce;

import org.springframework.stereotype.Component;

import com.ntu.edu.group5.ecommerce.entity.Cart;
import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.entity.Category;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.entity.Status;
import com.ntu.edu.group5.ecommerce.repository.CartItemRepository;
import com.ntu.edu.group5.ecommerce.repository.CartRepository;
import com.ntu.edu.group5.ecommerce.repository.CustomerRepository;
import com.ntu.edu.group5.ecommerce.repository.ProductRepository;
import com.ntu.edu.group5.ecommerce.repository.SellerRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
        private CustomerRepository customerRepository;
        private ProductRepository productRepository;
        private CartRepository cartRepository;
        private SellerRepository sellerRepository;
        private CartItemRepository cartItemRepository;

        public DataLoader(CustomerRepository customerRepository, ProductRepository productRepository,
                        CartRepository cartRepository, SellerRepository sellerRepository,
                        CartItemRepository cartItemRepository) {
                this.customerRepository = customerRepository;
                this.productRepository = productRepository;
                this.cartRepository = cartRepository;
                this.sellerRepository = sellerRepository;
                this.cartItemRepository = cartItemRepository;

        }

        @PostConstruct
        public void loadData() {
                // clear the database first
                customerRepository.deleteAll();
                productRepository.deleteAll();

                // load data here
                // [Activity 2 - validation]
                customerRepository.save(new Customer("Bruce", "Banner", "12345678", 1972));
                customerRepository.save(new Customer("Peter", "Parker", "12345677", 2005));
                customerRepository.save(new Customer("Stephen", "Strange", "12345678", 1976));
                // customerRepository.save(new Customer("Steve", "Rogers", "12345678", 1940));

                // productRepository
                productRepository.save(
                                new Product("Ipad", 1, "Technological Product from Apple", Category.ELECTRONICS,
                                                Status.ACTIVE, 1899.99, "Apple Inc"));
                productRepository.save(
                                new Product("Apple Pen", 2, "Technological stylus from Apple", Category.ELECTRONICS,
                                                Status.ACTIVE, 98.99, "Apple Inc"));
                productRepository.save(new Product("Sumsung Galaxy watch", 1, "Technological Product from Samsung",
                                Category.ELECTRONICS, Status.ACTIVE, 499.0,
                                "Samsung Electronics"));

                // cartRepository
                cartRepository.save(new Cart(14.55));
                cartRepository.save(new Cart(5.84));

                // cartItemRepository
                // cartItemRepository.save(new CartItem(11));

                // SellerRepository
                // sellerRepository.save(new Seller("Sally", "Wong", "67912380"));
                // sellerRepository.save(new Seller("Martin", "Goh", "67944321"));
                // sellerRepository.save(new Seller("Daniel", "Tan", "67988457"));

        }
}
