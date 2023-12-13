package com.ntu.edu.group5.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

// import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ntu.edu.group5.ecommerce.entity.Seller;
import com.ntu.edu.group5.ecommerce.exception.SellerNotFoundException;
import com.ntu.edu.group5.ecommerce.repository.SellerRepository;
import com.ntu.edu.group5.ecommerce.service.SellerServiceImpl;

@SpringBootTest
public class SellerServiceImplTest {
    
    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    SellerServiceImpl sellerService;

    @Test
    public void createSellerTest() {

        // 1. SETUP
        // Create a new seller
        // Seller seller = new Seller(1L, "Clint", "Barton", "clint@avenver.com", "12345678", "Special Agent", 1975, new ArrayList<Interaction>());
       Seller seller = Seller.builder().firstName("Jason").lastName("Alex").email("alex@yahoo.com").contactNo("12345678").build();


        // mock the save method of the seller repository
        when((sellerRepository.save(seller))).thenReturn(seller);

        // 2. EXECUTE
        Seller savedSeller = sellerService.createSeller(seller);

        // 3. ASSERT
        assertEquals(seller, savedSeller, "The saved seller should be the same as the new seller.");

        // also verify that the save method of the seller repository is called once only.
        verify(sellerRepository, times(1)).save(seller);
    }

    @Test
    public void getSellerTest() {
        // 1. SETUP
        // Create a new seller
        Seller seller = Seller.builder().firstName("Jason").lastName("Alex").email("alex@yahoo.com").contactNo("12345678").build();

        Long sellerId = 1L;

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));

        // 2.EXECUTE
        Seller retrievedSeller = sellerService.getSeller(sellerId);

        // 3. ASSERT
        assertEquals(seller, retrievedSeller);
    }

    @Test
    void testGetSellerNotFound() {
        Long sellerId = 1L;
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());

        assertThrows(SellerNotFoundException.class, () -> sellerService.getSeller(sellerId));
    }
}
