package com.ntu.edu.group5.ecommerce.service;

import com.ntu.edu.group5.ecommerce.entity.CustomerDTO;
import com.ntu.edu.group5.ecommerce.entity.SellerDTO;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;
import com.ntu.edu.group5.ecommerce.entity.UserSession;


public interface LoginLogoutService {

	public UserSession loginCustomer(CustomerDTO customer);

	public SessionDTO logoutCustomer(SessionDTO session);

	public void checkTokenStatus(String token);

	public void deleteExpiredTokens();


	public UserSession loginSeller(SellerDTO seller);

	public SessionDTO logoutSeller(SessionDTO session);


}
