package com.ntu.edu.group5.ecommerce.service;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.exception.AddressNotFoundException;
import com.ntu.edu.group5.ecommerce.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    public List<Address> getAddressesByPostalCode(String postalCode) {
        return addressRepository.findByPostalCode(postalCode);
    }

    @Override
    public Address updateAddress(Integer id, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        // Update the fields to change in existingAddress
        existingAddress.setStreetName(updatedAddress.getStreetName());
        existingAddress.setBuildingName(updatedAddress.getBuildingName());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());

        return addressRepository.save(existingAddress);
    }

    @Override
    public void deleteAddress(Integer id) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        addressRepository.delete(existingAddress);
    }
}

