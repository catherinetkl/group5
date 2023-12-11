package com.ntu.edu.group5.ecommerce.service;


import java.util.List;

import com.ntu.edu.group5.ecommerce.entity.CartDTO;
import com.ntu.edu.group5.ecommerce.entity.CartItem;
import com.ntu.edu.group5.ecommerce.exception.CartItemNotFoundException;

public interface CartItemService {

    public List<CartItem> getCartItemsByCustomerId(Long customerId) throws CartItemNotFoundException;

    public CartItem addItemToCart(CartDTO cartdto, Long customerId, Long productId, int quantity) throws CartItemNotFoundException;

    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws CartItemNotFoundException;

    public void removeCartItem(Long cartItemId) throws CartItemNotFoundException;

}
