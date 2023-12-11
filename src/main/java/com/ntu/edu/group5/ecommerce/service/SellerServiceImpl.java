package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.ntu.edu.group5.ecommerce.exception.LoginException;
import com.ntu.edu.group5.ecommerce.exception.SellerException;
import com.ntu.edu.group5.ecommerce.exception.SellerNotFoundException;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.entity.SellerDTO;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;
import com.ntu.edu.group5.ecommerce.entity.UserSession;
import com.ntu.edu.group5.ecommerce.repository.SellerDao;
import com.ntu.edu.group5.ecommerce.repository.SessionDao;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerDao sellerDao;

    @Autowired
    private LoginLogoutService loginService;

    @Autowired
    private SessionDao sessionDao;

    // Method to create a new seller
    @Override
    public Seller createSeller(Seller seller) {
        // Set creation timestamp for the seller
        seller.setCreatedOn(LocalDateTime.now());

        // Set hashed password using BCrypt
        seller.setHashedPassword("password");

        // Save the seller to the database
        Seller addedSeller = sellerDao.save(seller);

        // Initialize the seller's product list
        addedSeller.setProduct(new ArrayList<>());

        return addedSeller;
    }

    // Method to get all sellers
    @Override
    public List<Seller> getAllSellers() throws SellerException {
        List<Seller> sellers = sellerDao.findAll();

        if (sellers.size() > 0) {
            return sellers;
        } else {
            throw new SellerException("No Seller Found!");
        }
    }

    // Method to get seller by ID
    @Override
    public Seller getSeller(Long sellerId) throws SellerException {
        Optional<Seller> seller = sellerDao.findById(sellerId);

        if (seller.isPresent()) {
            return seller.get();
        } else {
            throw new SellerException("Seller not found for this ID: " + sellerId);
        }
    }

     // Method to get seller by contact number
    @Override
    public Seller getSellerByContactNo(String contactNo, String token) throws SellerException {
        if (!token.contains("seller")) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        Seller existingSeller = sellerDao.findByContactNo(contactNo)
                .orElseThrow(() -> new SellerException("Seller not found with given contact number"));

        return existingSeller;
    }

    // Method to get logged in seller details
    @Override
    public Seller getLoggedInSellerDetails(String token) throws SellerException {
        if (!token.contains("seller")) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();
        Seller existingSeller = sellerDao.findById(user.getUserId())
                .orElseThrow(() -> new SellerException("Seller not found for this ID"));

        return existingSeller;
    }


    @Override
    public List<Seller> searchSellers(String firstName, String lastName) throws SellerNotFoundException {
        // check if first name or last name is empty
        if (firstName.isEmpty() || lastName.isEmpty()) {
            throw new SellerNotFoundException("No record exists");
        }
        // find seller by first name or last name
        List<Seller> foundSellers = sellerDao.findByFirstNameOrLastName(firstName, lastName);
        return foundSellers;
    }


    // Method to update seller details
    @Override
    public Seller updateSeller(Long id, Seller seller) throws SellerException {
        Optional<Seller> opt = sellerDao.findById(id);

        if (opt.isEmpty()) {
            throw new SellerException("Seller does not exist");
        }

        Seller existingSeller = opt.get();

        existingSeller.setFirstName(seller.getFirstName());
        existingSeller.setLastName(seller.getLastName());
        existingSeller.setContactNo(seller.getContactNo());
        existingSeller.setEmail(seller.getEmail());
        existingSeller.setHashedPassword(seller.getHashedPassword());

        return sellerDao.save(existingSeller);
    }

    // Method to update seller contact number
    @Override
    public Seller updateSellerContactNo(Long sellerId, SellerDTO sellerDTO, String token) throws SellerException {
        if (!token.contains("seller")) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();
        Seller existingSeller = sellerDao.findById(sellerId)
                .orElseThrow(() -> new SellerException("Seller not found for this ID: " + user.getUserId()));

        if (existingSeller.getHashedPassword().equals(sellerDTO.getPassword())) {
            existingSeller.setContactNo(sellerDTO.getContactNo());
            return sellerDao.save(existingSeller);
        } else {
            throw new SellerException("Error occurred in updating mobile. Please enter correct password");
        }
    }

    // Method to update seller password
    @Override
    public SessionDTO updateSellerPassword(Long sellerId, SellerDTO sellerDTO, String currentPassword) {

        Optional<Seller> opt = sellerDao.findById(sellerId);

        if (opt.isEmpty()) {
            throw new SellerException("Seller does not exist");
        }

        Seller existingSeller = opt.get();

        if (!sellerDTO.getContactNo().equals(existingSeller.getContactNo())) {
            throw new SellerException("Verification error. Mobile number does not match");
        }

        existingSeller.setHashedPassword(sellerDTO.getPassword());

        sellerDao.save(existingSeller);

        SessionDTO session = new SessionDTO();

        session.setToken("newToken");

        loginService.logoutSeller(session);

        session.setMessage("Updated password and logged out. Login again with new password");

        return session;
    }


    // Method to delete seller by ID
    @Override
    public SessionDTO deleteSellerById(Long sellerId, String token) throws SellerException {
        if (!token.contains("seller")) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        Optional<Seller> opt = sellerDao.findById(sellerId);

        if (opt.isPresent()) {
            UserSession user = sessionDao.findByToken(token).get();
            Seller existingSeller = opt.get();

            if (user.getUserId() == existingSeller.getSellerId()) {
                sellerDao.delete(existingSeller);

                // logic to log out a seller after he deletes his account
                SessionDTO session = new SessionDTO();
                session.setToken(token);
                loginService.logoutSeller(session);

                return session;
            } else {
                throw new SellerException("Verification Error in deleting seller account");
            }
        } else {
            throw new SellerException("Seller not found for this ID: " + sellerId);
        }
    }

    // Method to delete customer
    @Override
    public void deleteSeller(Long sellerId) throws SellerException {

        // Check if the seller exists
        if (!sellerDao.existsById(sellerId)) {
            throw new SellerException("Seller does not exist");
        }

        // If the seller exists, delete the seller
        sellerDao.deleteById(sellerId);

        // Check if the seller still exists
        if (sellerDao.existsById(sellerId)) {
            throw new SellerException("Seller could not be deleted");
        }
    }

}
