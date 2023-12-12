package com.ntu.edu.group5.ecommerce.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.service.CartItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cartItems")
public class CartItemController {
    private CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // READ (GET ALL)
    @GetMapping({ "/", "" })
    public ResponseEntity<ArrayList<CartItem>> getAllCartItems() {
        ArrayList<CartItem> allCartItems = cartItemService.getAllCartItems();
        return new ResponseEntity<>(allCartItems, HttpStatus.OK);
    }

    // READ (GET ONE)
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable Long id) {
        CartItem foundCartItem = cartItemService.getCartItem(id);
        return new ResponseEntity<>(foundCartItem, HttpStatus.OK);
    }

    // CREATE
    @PostMapping({ "", "/" })
    public ResponseEntity<CartItem> createCartItem(@Valid @RequestBody CartItem cartItem) {
        CartItem newCartItem = cartItemService.createCartItem(cartItem);
        return new ResponseEntity<>(newCartItem, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        CartItem updatedCartItem = cartItemService.updateCartItem(id, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);

    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<CartItem> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
