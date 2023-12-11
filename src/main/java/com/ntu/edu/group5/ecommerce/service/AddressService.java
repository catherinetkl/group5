package com.ntu.edu.group5.ecommerce.service;

import com.ntu.edu.group5.ecommerce.entity.Address;

import java.util.List;

public interface AddressService {

    Address createAddress(Address address);

    List<Address> getAllAddresses();

    Address getAddressById(Integer id);

    List<Address> getAddressesByPostalCode(String postalCode);

    Address updateAddress(Integer id, Address updatedAddress);

    void deleteAddress(Integer id);
}
