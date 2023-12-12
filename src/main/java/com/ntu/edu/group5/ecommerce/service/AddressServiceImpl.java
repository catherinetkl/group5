package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public ArrayList<Address> searchAddressesByCity(String city) {
        List<Address> foundAddresses = addressRepository.findByCity(city);
        return (ArrayList<Address>) foundAddresses;
    }

    @Override
    public Address createAddress(Address address) {
        Address newAddress = addressRepository.save(address);
        return newAddress;
    }

    @Override
    public ArrayList<Address> getAllAddresses() {
        List<Address> allAddresses = addressRepository.findAll();
        return (ArrayList<Address>) allAddresses;
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address updateAddress(Long id, Address address) {
        if (addressRepository.existsById(id)) {
            address.setId(id);
            return addressRepository.save(address);
        }
        return null; // or throw an exception indicating that the address with the given id doesn't exist
    }

    @Override
    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElse(null);
    }
}
