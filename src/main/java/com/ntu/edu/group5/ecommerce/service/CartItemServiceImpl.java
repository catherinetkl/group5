package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.exception.CartItemNotFoundException;
import com.ntu.edu.group5.ecommerce.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        CartItem newCartItem = cartItemRepository.save(cartItem);
        return newCartItem;
    }

    @Override
    public ArrayList<CartItem> getAllCartItems() {
        List<CartItem> allCartItems = cartItemRepository.findAll();
        return (ArrayList<CartItem>) allCartItems;
    }

    @Override
    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItem updateCartItem(Long id, CartItem cartItem) {
        CartItem cartItemToUpdate = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException(id));
        // update the cartItem retrieved fromn the database
        cartItemToUpdate.setCart(cartItem.getCart());
        cartItemToUpdate.setCartItemQuantity(cartItem.getCartItemQuantity());
        cartItemToUpdate.setProduct(cartItem.getProduct());
        // save the updated cartItem back to the database
        return cartItemRepository.save(cartItemToUpdate);
    }

    @Override
    public CartItem getCartItem(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new CartItemNotFoundException(id));
    }

}
