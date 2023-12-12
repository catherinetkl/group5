package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;

import com.ntu.edu.group5.ecommerce.entity.Seller;

public interface SellerService {
    Seller createSeller(Seller seller);

    Seller getSeller(Long id);

    ArrayList<Seller> getAllSellers();

    Seller updateSeller(Long id, Seller seller);

    void deleteSeller(Long id);

    ArrayList<Seller> searchSellers(String firstName);

}
