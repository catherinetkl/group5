package com.ntu.edu.group5.ecommerce.controller;

import java.util.ArrayList;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/search")
    public ResponseEntity<ArrayList<Address>> searchAddresses(@RequestParam String city) {
        ArrayList<Address> foundAddresses = addressService.searchAddressesByCity(city);
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
    public ResponseEntity<ArrayList<Address>> getAllAddresses() {
        ArrayList<Address> allAddresses = addressService.getAllAddresses();
        return new ResponseEntity<>(allAddresses, HttpStatus.OK);
    }

    // READ (GET ONE)
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Address foundAddress = addressService.getAddress(id);
        return new ResponseEntity<>(foundAddress, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(id, address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
