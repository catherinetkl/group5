package com.ntu.edu.group5.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.exception.CartItemNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.CustomerNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.ProductNotFoundException;
import com.ntu.edu.group5.ecommerce.entity.Cart;
import com.ntu.edu.group5.ecommerce.entity.CartDTO;
import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.Product;
import com.ntu.edu.group5.ecommerce.entity.ProductStatus;
import com.ntu.edu.group5.ecommerce.repository.CustomerDao;
import com.ntu.edu.group5.ecommerce.repository.CartItemDao;
import com.ntu.edu.group5.ecommerce.repository.ProductDao;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    ProductDao productDao;

    private CartItemDao cartItemDao;

    @Override
    public CartItem addItemToCart
            (CartDTO cartdto,
            Long customerId,
            Long productId,
            int quantity)
            throws CartItemNotFoundException {
        Optional<Product> opt = productDao.findById(cartdto.getProductId());

        if (opt.isEmpty())
            throw new ProductNotFoundException("Product not found");

        Product existingProduct = opt.get();

        CartItem newItem = new CartItem();

        newItem.setCartProduct(existingProduct);
        newItem.setCartItemQuantity(1);
        return newItem;
    }

    @Override
    public List<CartItem> getCartItemsByCustomerId(Long customerId) throws CartItemNotFoundException {
        // Implementation of getting cart items by customer ID
        List<CartItem> cartItems = cartItemDao.findCartByCustomerId(customerId);

        // Check if the cart items exist
        if (cartItems.isEmpty()) {
            throw new CartItemNotFoundException("Cart items not found for customer ID: " + customerId);
        }

        // Return the list of cart items
        return cartItems;
    }


    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws CartItemNotFoundException {
        // Implementation of updating cart item quantity

        throw new CartItemNotFoundException("Error updating cart item quantity");
    }

    @Override
    public void removeCartItem(Long cartItemId) throws CartItemNotFoundException {
        // Implementation of removing cart item

        throw new CartItemNotFoundException("Error removing cart item");
    }
}
