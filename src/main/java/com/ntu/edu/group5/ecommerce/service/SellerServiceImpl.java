package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.exception.SellerNotFoundException;
import com.ntu.edu.group5.ecommerce.repository.SellerRepository;

@Service
public class SellerServiceImpl implements SellerService {
    private SellerRepository sellerRepository;

    // @Autowired
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
    public Seller getSeller(Long id) {
        // Optional<Seller> optionalSeller = sellerRepository.findById(id);
        // if(optionalSeller.isPresent()) {
        // Seller foundSeller = optionalSeller.get();
        // return foundSeller;
        // }
        // throw new SellerNotFoundException(id);
        return sellerRepository.findById(id).orElseThrow(() -> new SellerNotFoundException(id));
    }

    @Override
    public ArrayList<Seller> getAllSellers() {
        List<Seller> allSellers = sellerRepository.findAll();
        return (ArrayList<Seller>) allSellers;
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {
        // retrieve the seller from the database
        // [Activity 1 - Refactor code]
        Seller sellerToUpdate = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException(id));
        // update the seller retrieved from the database
        sellerToUpdate.setFirstName(seller.getFirstName());
        sellerToUpdate.setLastName(seller.getLastName());
        sellerToUpdate.setEmail(seller.getEmail());
        sellerToUpdate.setContactNo(seller.getContactNo());
        sellerToUpdate.setPassword(seller.getPassword());
        sellerToUpdate.setProducts(seller.getProducts());

        // save the updated seller back to the database
        return sellerRepository.save(sellerToUpdate);
    }

    @Override
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }

}
