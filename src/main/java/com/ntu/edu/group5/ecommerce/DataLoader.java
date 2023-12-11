package com.ntu.edu.group5.ecommerce;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.ntu.edu.group5.ecommerce.entity.Address;
import com.ntu.edu.group5.ecommerce.entity.Customer;
import com.ntu.edu.group5.ecommerce.repository.CustomerDao;
import com.ntu.edu.group5.ecommerce.repository.AddressDao;

@Component
public class DataLoader {
    private CustomerDao customerDao;
    private AddressDao addressDao;

    // @Autowired
    public DataLoader(CustomerDao customerDao, AddressDao addressDao) {
        this.customerDao = customerDao;
        this.addressDao = addressDao;
    }

    @PostConstruct
    public void loadData() {
        // clear the database first
        customerDao.deleteAll();

        // load data here
        // Create sample customers and save them to the database
        Customer customer1 = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .hashedPassword("hashedPassword1") // You should use hashed passwords in a real application
                .contactNo("98765432")
                .build();

        Customer customer2 = Customer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .hashedPassword("hashedPassword2") // You should use hashed passwords in a real application
                .contactNo("62345678")
                .build();

        customerDao.save(customer1);
        customerDao.save(customer2);

        // Reload the customers from the database
        // customer1 = customerDao.findById(customer1.getCustomerId()).orElse(null);
        // customer2 = customerDao.findById(customer2.getCustomerId()).orElse(null);

        // Create sample addresses and save them to the database
        // Address address1 = Address.builder()
        //         .blockNumber("668A")
        //         .streetName("Jurong West Street 64")
        //         .city("Singapore")
        //         .state("Singapore")
        //         .postalCode("641668")
        //         .customer(customer1)
        //         .build();

        // Create sample addresses and save them to the database
        // Address address2 = Address.builder()
        //         .blockNumber("39A")
        //         .streetName("Margaret Drive")
        //         .city("Singapore")
        //         .state("Singapore")
        //         .postalCode("141039")
        //         .customer(customer2)
        //         .build();

        // addressDao.save(address1);
        // addressDao.save(address2);

    }
}
