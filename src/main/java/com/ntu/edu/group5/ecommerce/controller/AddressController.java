package com.ntu.edu.group5.ecommerce.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Address>> searchAddresses(@RequestParam String postalCode) {
        List<Address> foundAddresses = addressService.getAddressesByPostalCode(postalCode);
        return new ResponseEntity<>(foundAddresses, HttpStatus.OK);
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<Address> createAddress(@Valid @RequestBody Address address) {
        Address newAddress = addressService.createAddress(address);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    // READ (GET ALL)
    @GetMapping("")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> allAddresses = addressService.getAllAddresses();
        return new ResponseEntity<>(allAddresses, HttpStatus.OK);
    }

    // READ (GET ONE)
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Integer id) {
        Address foundAddress = addressService.getAddressById(id);
        return new ResponseEntity<>(foundAddress, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(id, address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
