package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.repository.SellerRepository;

@Service
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;

    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

     @Override
    public ArrayList<Seller> searchSellers(String firstName) {
        List<Seller> foundSellers = sellerRepository.findByFirstName(firstName);
        return (ArrayList<Seller>) foundSellers;
    }

    @Override
    public Seller createSeller(Seller seller) {
        Seller newSeller = sellerRepository.save(seller);
        return newSeller;
    }
    
    @Override
    public ArrayList<Seller> getAllSellers() {
        List<Seller> allSellers = sellerRepository.findAll();
        return (ArrayList<Seller>) allSellers;
    }

    @Override
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {
        if (sellerRepository.existsById(id)) {
            seller.setId(id);
            return sellerRepository.save(seller);
        }
        return null; // or throw an exception indicating that the customer with the given id doesn't exist
    }

    @Override
    public Seller getSeller(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }


    
}
