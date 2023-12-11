package com.ntu.edu.group5.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.exception.CustomerNotFoundException;
import com.ntu.edu.group5.ecommerce.exception.LoginException;
import com.ntu.edu.group5.ecommerce.exception.SellerNotFoundException;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.entity.CustomerDTO;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.entity.SellerDTO;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;
import com.ntu.edu.group5.ecommerce.entity.UserSession;
import com.ntu.edu.group5.ecommerce.repository.CustomerDao;
import com.ntu.edu.group5.ecommerce.repository.SellerDao;
import com.ntu.edu.group5.ecommerce.repository.SessionDao;

@Service
public class LoginLogoutServiceImpl implements LoginLogoutService {

    private static final Logger logger = LoggerFactory.getLogger(LoginLogoutServiceImpl.class);

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private SellerDao sellerDao;

    // Method to login a customer and generate a customer token
    @Override
    public UserSession loginCustomer(CustomerDTO loginCustomer) {

        logger.info("Attempting to login customer with contact number: {}", loginCustomer.getContactNo());

        Optional<Customer> res = customerDao.findByContactNo(loginCustomer.getContactNo());

        if (res.isEmpty())
            throw new CustomerNotFoundException("Customer record does not exist with given mobile number");

        Customer existingCustomer = res.get();

        Optional<UserSession> opt = sessionDao.findByUserId(existingCustomer.getCustomerId());

        if (opt.isPresent()) {
            UserSession user = opt.get();

            if (user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                sessionDao.delete(user);
            } else
                throw new LoginException("User already logged in");

        }

        if (existingCustomer.getHashedPassword().equals(loginCustomer.getPassword())) {
            UserSession newSession = new UserSession();

            newSession.setUserId(existingCustomer.getCustomerId());
            newSession.setUserType("customer");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));

            UUID uuid = UUID.randomUUID();
            String token = "customer_" + uuid.toString().split("-")[0];

            newSession.setToken(token);

            return sessionDao.save(newSession);
        } else {
            throw new LoginException("Password Incorrect. Try again.");
        }
    }

    // Method to logout a customer
    @Override
    public SessionDTO logoutCustomer(SessionDTO sessionToken) {
        String token = sessionToken.getToken();
        logger.info("Attempting to logout user with token: {}", token);

        checkTokenStatus(token);

        Optional<UserSession> opt = sessionDao.findByToken(token);

        if (!opt.isPresent())
            throw new LoginException("User not logged in. Invalid session token. Login Again.");

        UserSession session = opt.get();

        sessionDao.delete(session);

        sessionToken.setMessage("Logged out successfully.");

        return sessionToken;
    }

    // Method to check status of session token
    @Override
    public void checkTokenStatus(String token) {
        Optional<UserSession> opt = sessionDao.findByToken(token);

        if (opt.isPresent()) {
            UserSession session = opt.get();
            LocalDateTime endTime = session.getSessionEndTime();
            boolean flag = false;
            if (endTime.isBefore(LocalDateTime.now())) {
                sessionDao.delete(session);
                flag = true;
            }

            deleteExpiredTokens();
            if (flag)
                throw new LoginException("Session expired. Login Again");
        } else {
            throw new LoginException("User not logged in. Invalid session token. Please login first.");
        }
    }

    // Method to login a valid seller and generate a seller token
    @Override
    public UserSession loginSeller(SellerDTO seller) {
        Optional<Seller> res = sellerDao.findByContactNo(seller.getContactNo());

        if (res.isEmpty())
            throw new SellerNotFoundException("Seller record does not exist with given mobile number");

        Seller existingSeller = res.get();

        Optional<UserSession> opt = sessionDao.findByUserId(existingSeller.getSellerId());

        if (opt.isPresent()) {
            UserSession user = opt.get();

            if (user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                sessionDao.delete(user);
            } else
                throw new LoginException("User already logged in");
        }

        if (existingSeller.getHashedPassword().equals(seller.getPassword())) {
            UserSession newSession = new UserSession();

            newSession.setUserId(existingSeller.getSellerId());
            newSession.setUserType("seller");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));

            UUID uuid = UUID.randomUUID();
            String token = "seller_" + uuid.toString().split("-")[0];

            newSession.setToken(token);

            return sessionDao.save(newSession);
        } else {
            throw new LoginException("Password Incorrect. Try again.");
        }
    }

    // Method to logout a seller and delete his session token
    @Override
    public SessionDTO logoutSeller(SessionDTO session) {
        String token = session.getToken();
        logger.info("Attempting to logout seller with token: {}", token);

        checkTokenStatus(token);

        Optional<UserSession> opt = sessionDao.findByToken(token);

        if (!opt.isPresent())
            throw new LoginException("User not logged in. Invalid session token. Login Again.");

        UserSession user = opt.get();

        sessionDao.delete(user);

        session.setMessage("Logged out successfully.");

        return session;
    }

    // Method to delete expired tokens
    @Override
    public void deleteExpiredTokens() {
        logger.info("Inside delete tokens");

        List<UserSession> users = sessionDao.findAll();

        logger.info("Users found: {}", users);

        if (users.size() > 0) {
            for (UserSession user : users) {
                logger.info("User ID: {}", user.getUserId());
                LocalDateTime endTime = user.getSessionEndTime();
                if (endTime.isBefore(LocalDateTime.now())) {
                    logger.info("Deleting expired session for User ID: {}", user.getUserId());
                    sessionDao.delete(user);
                }
            }
        }
    }
}
