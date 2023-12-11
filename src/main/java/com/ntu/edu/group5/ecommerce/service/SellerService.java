package com.ntu.edu.group5.ecommerce.service;

import java.util.List;

import com.ntu.edu.group5.ecommerce.exception.SellerException;
import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.entity.SellerDTO;
import com.ntu.edu.group5.ecommerce.entity.SessionDTO;

public interface SellerService {

	public Seller createSeller(Seller seller) throws SellerException;

	public List<Seller> getAllSellers() throws SellerException;

	public Seller getSeller(Long sellerId) throws SellerException;

	public Seller getSellerByContactNo(String contactNo, String token) throws SellerException;

	public Seller getLoggedInSellerDetails(String token) throws SellerException;

    public List<Seller> searchSellers(String firstName, String lastName) throws SellerException;

	public SessionDTO updateSellerPassword(Long id, SellerDTO sellerDTO, String currentPassword) throws SellerException;

	public Seller updateSeller(Long id, Seller seller) throws SellerException;

	public Seller updateSellerContactNo(Long id, SellerDTO sellerDTO, String token) throws SellerException;

    public void deleteSeller(Long id) throws SellerException;

    public SessionDTO deleteSellerById(Long sellerId, String token) throws SellerException;
}
