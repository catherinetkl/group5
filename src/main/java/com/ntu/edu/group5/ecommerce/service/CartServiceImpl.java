package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.Cart;
import com.ntu.edu.group5.ecommerce.exception.CartNotFoundException;
import com.ntu.edu.group5.ecommerce.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;


    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart createCart(Cart cart) {
        Cart newCart = cartRepository.save(cart);
        return newCart;
    }

    @Override
    public ArrayList<Cart> getAllCarts() {
        List<Cart> allCarts = cartRepository.findAll();
        return (ArrayList<Cart>) allCarts;
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {
        Cart cartToUpdate = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        // update the cart retrieved fromn the database

        cartToUpdate.setCartTotal(cart.getCartTotal());
        cartToUpdate.setCartItems(cart.getCartItems());
        cartToUpdate.setCustomer(cart.getCustomer());
        // save the updated cart back to the database
        return cartRepository.save(cartToUpdate);
    }

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }
}
